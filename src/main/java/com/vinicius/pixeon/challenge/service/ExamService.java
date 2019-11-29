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
import com.vinicius.pixeon.challenge.repository.ExamRepository;

@Service
public class ExamService {

    @Autowired
    private ExamRepository repo;

    @Autowired
    private HealthcareInstitutionService healthcareService;

    public ExamResponseDto findById(Long id) {
        Optional<Exam> exam = repo.findById(id);
        if (exam.isPresent()) {
            return new ExamResponseDto(exam.get(), null);
        } else {
            return new ExamResponseDto(null, "Exam not found");
        }
    }

    @Transactional
    public ExamResponseDto save(ExamRequestDto dto) {
        // Tenta carregar a instituição
        Optional<HealthcareInstitution> healthcare = healthcareService.findById(dto.getHealthcareId());

        validateSaveOperation(dto, healthcare);

        Exam exam = new Exam();
        exam.setHealthcareInstitution(healthcare.get());
        exam.setPatientAge(dto.getPatientAge());
        exam.setPatientGender(dto.getPatientGender());
        exam.setPatientName(dto.getPatientName());
        exam.setPhysicianCRM(dto.getPhysicianCRM());
        exam.setPhysicianName(dto.getPhysicianName());
        exam.setProcedureName(dto.getProcedureName());

        Exam newExam = repo.save(exam);

        return new ExamResponseDto(newExam, null);
    }

    private void validateSaveOperation(ExamRequestDto dto, Optional<HealthcareInstitution> healthcare) {
        if (dto == null) {
            throw new PixeonServiceException("dto is null");
        }

        if (dto.getHealthcareId() == null) {
            throw new PixeonServiceException("healthcareId is null");
        }

        if (!healthcare.isPresent()) {
            throw new PixeonServiceException("HealthcareInstitution not found");
        }
    }
}
