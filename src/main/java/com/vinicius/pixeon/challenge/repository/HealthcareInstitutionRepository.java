package com.vinicius.pixeon.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;

/**
 * @author vinícius.carvalho
 */
public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Long> {

    HealthcareInstitution findByName(String name);
    HealthcareInstitution findByCnpj(String cnpj);
}
