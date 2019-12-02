package com.vinicius.pixeon.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.pixeon.challenge.domain.Exam;
import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;
import com.vinicius.pixeon.challenge.dto.ExamRequestDto;
import com.vinicius.pixeon.challenge.dto.ExamResponseDto;
import com.vinicius.pixeon.challenge.exception.PixeonServiceException;
import com.vinicius.pixeon.challenge.exception.PixeonUnauthorizedException;
import com.vinicius.pixeon.challenge.repository.ExamRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository repo;

    @Autowired
    private HealthcareInstitutionService healthcareService;

    public ExamResponseDto findById(Long id, Long idHealthcare) {
        Optional<Exam> examOptional = repo.findById(id);

        if (!examOptional.isPresent()) {
            return new ExamResponseDto(null, "Exame não encontrado");
        }

        Exam exam = examOptional.get();

        // Verifica se a instituição possui acesso a este exame
        if (idHealthcare != null && !exam.getHealthcareInstitution().getId().equals(idHealthcare)) {
            throw new PixeonUnauthorizedException("Esta instituição não está autorizada a acessar este exame");
        }

        // Se for o primeiro acesso ao exame debitar uma moeda da instituição
        // e atualizar a flag do exame
        if (exam.getFirstRead()) {
            int recordsChanged = healthcareService.chargeOneCoin(exam.getHealthcareInstitution().getId());
            if (recordsChanged == 0) {
                throw new PixeonServiceException("Não foi possível acessar o exame, pois a instituição não possui crédito");
            }
            repo.updatefirstReadToFalse(id);
        }

        if (healthcareService.hasCoins(exam.getHealthcareInstitution().getId())) {
            return new ExamResponseDto(exam, null);
        }
        throw new PixeonUnauthorizedException("Não é possível acessar o exame, pois a instituição está sem créditos");
    }

    @Transactional
    public ExamResponseDto save(ExamRequestDto dto) {
        // Tenta carregar a instituição
        Optional<HealthcareInstitution> healthcare = healthcareService.findById(dto.getHealthcareId());

        validateSaveOperation(dto, healthcare);

        Exam exam = null;
        if (dto.getId() == null) {
            exam = new Exam();
        } else {
            Optional<Exam> examOpt = repo.findById(dto.getId());
            if (examOpt.isPresent()) {
                exam = examOpt.get();
            } else {
                throw new PixeonServiceException("Exame não encontrado");
            }
        }
        exam.setHealthcareInstitution(healthcare.get());
        exam.setPatientAge(dto.getPatientAge());
        exam.setPatientGender(dto.getPatientGender());
        exam.setPatientName(dto.getPatientName());
        exam.setPhysicianCRM(dto.getPhysicianCRM());
        exam.setPhysicianName(dto.getPhysicianName());
        exam.setProcedureName(dto.getProcedureName());

        Exam examPersisted = repo.save(exam);

        // Charge one coin for create a new exam
        if (dto.getId() == null) {
            int recordsChanged = healthcareService.chargeOneCoin(healthcare.get().getId());
            if (recordsChanged == 0) {
                throw new PixeonUnauthorizedException("Não foi possível criar o novo exame, pois a instituição está sem crédito");
            }
        }

        return new ExamResponseDto(examPersisted, null);
    }

    @Transactional
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // TODO: Melhorar a validação dos dados. Estender para todos os campos obrigatórios
    private void validateSaveOperation(ExamRequestDto dto, Optional<HealthcareInstitution> healthcare) {
        if (dto == null) {
            throw new PixeonServiceException("É necessário preencher os campos para criar um novo exame");
        }

        if (dto.getHealthcareId() == null) {
            throw new PixeonServiceException("Não foi informado o id da instituição");
        }

        if (!healthcare.isPresent()) {
            throw new PixeonServiceException("Instituição não encontrada");
        }
    }
}
