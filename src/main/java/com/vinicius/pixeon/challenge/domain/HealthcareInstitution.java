package com.vinicius.pixeon.challenge.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vinícius.carvalho
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class HealthcareInstitution extends BaseEntity {

    @Getter @Setter
    @Column(nullable = false)
    private String name;

    @Getter @Setter
    @Column(nullable = false)
    private String cnpj;

    @Getter @Setter
    @Column(nullable = false)
    private Integer coins;

    public HealthcareInstitution(Long id) {
        this.setId(id);
    }
 }
