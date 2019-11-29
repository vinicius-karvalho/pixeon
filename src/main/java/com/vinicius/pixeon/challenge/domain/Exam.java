package com.vinicius.pixeon.challenge.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.vinicius.pixeon.challenge.type.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author vinícius.carvalho
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Exam extends BaseEntity {

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "healthcare_id", nullable = false)
    private HealthcareInstitution healthcareInstitution;

    @Getter @Setter
    private String patientName;

    @Getter @Setter
    private Integer patientAge;

    @Getter @Setter
    private Gender patientGender;

    @Getter @Setter
    private String physicianName;

    @Getter @Setter
    private String physicianCRM;

    @Getter @Setter
    private String procedureName;
}
