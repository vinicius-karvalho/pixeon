package com.vinicius.pixeon.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.vinicius.pixeon.challenge.domain.Exam;
import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;
import com.vinicius.pixeon.challenge.dto.ExamRequestDto;
import com.vinicius.pixeon.challenge.dto.ExamResponseDto;
import com.vinicius.pixeon.challenge.exception.PixeonNotFoundException;
import com.vinicius.pixeon.challenge.exception.PixeonServiceException;
import com.vinicius.pixeon.challenge.exception.PixeonUnauthorizedException;
import com.vinicius.pixeon.challenge.repository.ExamRepository;
import com.vinicius.pixeon.challenge.type.OperationType;

/**
 * @author vinícius.carvalho
 */
@Service
public class ExamService {

    @Autowired
    private ExamRepository repo;

    @Autowired
    private HealthcareInstitutionService healthcareService;

    @Transactional
    public ExamResponseDto findById(Long id, Long idHealthcare) {
        Exam exam = repo.findById(id).orElseThrow(() -> new PixeonNotFoundException("Exame não encontrado"));

        // Verifica se a instituição possui acesso a este exame
        if (idHealthcare != null && !exam.getHealthcareInstitution().getId().equals(idHealthcare)) {
            throw new PixeonUnauthorizedException("Esta instituição não está autorizada a acessar este exame");
        }

        // Se for o primeiro acesso ao exame debitar uma moeda da instituição
        // e atualizar a flag do exame
        if (exam.getFirstRead()) {
            int recordsChanged = healthcareService.chargeOneCoin(exam.getHealthcareInstitution().getId());
            if (recordsChanged == 0) {
                throw new PixeonUnauthorizedException("Não foi possível acessar o exame, pois a instituição não possui crédito");
            }
            repo.updatefirstReadToFalse(id);
        }

        // Verifica se a instituição ainda possui créditos para acesso a exames
        if (healthcareService.hasCoins(exam.getHealthcareInstitution().getId())) {
            return new ExamResponseDto(exam, null);
        }

        throw new PixeonUnauthorizedException("Não é possível acessar o exame, pois a instituição está sem crédito");
    }

    @Transactional
    public ExamResponseDto save(ExamRequestDto dto) {
        validateSaveOrUpdateOperation(dto, OperationType.SAVE);

        // Tenta carregar a instituição pelo id
        HealthcareInstitution healthcare = healthcareService.findById(dto.getHealthcareId());

        Exam exam = new Exam();
        exam.setHealthcareInstitution(healthcare);
        exam.setPatientAge(dto.getPatientAge());
        exam.setPatientGender(dto.getPatientGender());
        exam.setPatientName(dto.getPatientName());
        exam.setPhysicianCRM(dto.getPhysicianCRM());
        exam.setPhysicianName(dto.getPhysicianName());
        exam.setProcedureName(dto.getProcedureName());

        Exam examPersisted = repo.save(exam);

        // Cobra uma moeda da instituição pela criação de um novo exame
        int recordsChanged = healthcareService.chargeOneCoin(healthcare.getId());
        if (recordsChanged == 0) {
            throw new PixeonUnauthorizedException("Não foi possível criar o novo exame, pois a instituição está sem crédito");
        }

        return new ExamResponseDto(examPersisted, "Exame criado com sucesso");
    }

    @Transactional
    public ExamResponseDto update(ExamRequestDto dto) {
        validateSaveOrUpdateOperation(dto, OperationType.UPDATE);

        // Tenta carregar o exame para atualização
        Exam exam = repo.findById(dto.getId()).orElseThrow(() -> new PixeonNotFoundException("Exame não encontrado"));

        /*
         * O método de atualização ignora se o id da instituição foi informado. Em
         * outras palavras, não é permitida a troca de instituição na atualização. Caso
         * esta troca fosse permitida, seria necessário um serviço de estorno. Por
         * questões de simplicidade, vamos apenas impedir que a instituição de um exame
         * seja modificada.
         */
        exam.setPatientAge(dto.getPatientAge());
        exam.setPatientGender(dto.getPatientGender());
        exam.setPatientName(dto.getPatientName());
        exam.setPhysicianCRM(dto.getPhysicianCRM());
        exam.setPhysicianName(dto.getPhysicianName());
        exam.setProcedureName(dto.getProcedureName());

        Exam examPersisted = repo.save(exam);

        return new ExamResponseDto(examPersisted, "Exame atualizado com sucesso");
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    private void validateSaveOrUpdateOperation(ExamRequestDto dto, OperationType operationType) {
        if (dto == null) {
            throw new PixeonServiceException("É necessário preencher os campos para criar um novo exame.");
        }

        List<String> errorMessages = new ArrayList<>();

        if (operationType == OperationType.SAVE && dto.getHealthcareId() == null) {
            errorMessages.add("É necessário informar o id da instituição.");
        }

        if (operationType == OperationType.UPDATE && dto.getId() == null) {
            errorMessages.add("É necessário informar o id para atualizar o exame.");
        }

        if (dto.getPatientAge() == null || dto.getPatientAge().intValue() < 0 || dto.getPatientAge() > 130) {
            errorMessages.add("A idade informada do paciente é inválida.");
        }

        if (dto.getPatientGender() == null) {
            errorMessages.add("É necessário informar o gênero do paciente. Valores válidos: MALE ou FEMALE.");
        }

        if (!StringUtils.hasText(dto.getPatientName())) {
            errorMessages.add("É necessário informar o nome do paciente.");
        }

        if (!StringUtils.hasText(dto.getPhysicianCRM())) {
            errorMessages.add("É necessário informar o CRM.");
        }

        if (!StringUtils.hasText(dto.getPatientName())) {
            errorMessages.add("É necessário informar o nome do médico.");
        }

        if (!StringUtils.hasText(dto.getProcedureName())) {
            errorMessages.add("É necessário informar o nome do procedimento.");
        }

        if (!errorMessages.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            errorMessages.forEach(m -> {
                sb.append(m).append(" | ");
            });
            sb.delete(sb.lastIndexOf(" | "), sb.length());
            throw new PixeonServiceException(sb.toString());
        }
    }

}
