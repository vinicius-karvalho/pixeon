package com.vinicius.pixeon.challenge.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;
import com.vinicius.pixeon.challenge.dto.HealthcareInstitutionRequestDto;
import com.vinicius.pixeon.challenge.exception.PixeonServiceException;
import com.vinicius.pixeon.challenge.repository.HealthcareInstitutionRepository;

@Service
public class HealthcareInstitutionService {

    @Autowired
    private HealthcareInstitutionRepository repo;

    public Optional<HealthcareInstitution> findById(Long id) {
        return repo.findById(id);
    }

    public HealthcareInstitution findByName(String name) {
        return repo.findByName(name);
    }

    public HealthcareInstitution findByCnpj(String cnpj) {
        return repo.findByCnpj(cnpj);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public int chargeOneCoin(Long id) {
        return repo.chargeOneCoin(id);
    }

    @Transactional
    public HealthcareInstitution save(HealthcareInstitutionRequestDto dto) {
        validateSaveOperation(dto);
        return repo.save(new HealthcareInstitution(dto.getName(), dto.getCnpj(), Integer.valueOf(20)));
    }

    private void validateSaveOperation(HealthcareInstitutionRequestDto dto) {
        if (dto == null) {
            throw new PixeonServiceException("dto is null");
        }

        // Valida se existe instituição com mesmo nome
        HealthcareInstitution byName = findByName(dto.getName());
        if (byName != null) {
            throw new PixeonServiceException("já existe intituição de saúde com este nome cadastrado");
        }

        // Valida se existe instituição com mesmo cnpj
        HealthcareInstitution byCnpj = findByCnpj(dto.getCnpj());
        if (byCnpj != null) {
            throw new PixeonServiceException("já existe intituição de saúde com este cnpj cadastrado");
        }
    }
}
