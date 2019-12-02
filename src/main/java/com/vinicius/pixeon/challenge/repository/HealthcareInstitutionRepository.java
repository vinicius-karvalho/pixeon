package com.vinicius.pixeon.challenge.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;

/**
 * @author vinícius.carvalho
 */
public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Long> {

    HealthcareInstitution findByName(String name);

    HealthcareInstitution findByCnpj(String cnpj);

    @Modifying
    @Query("update HealthcareInstitution hc set hc.coins = (hc.coins-1) where hc.coins > 0 and hc.id = ?1")
    int chargeOneCoin(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select hc.coins from HealthcareInstitution hc where hc.id = ?1")
    Integer retrieveNumberOfCoins(Long id);
}
