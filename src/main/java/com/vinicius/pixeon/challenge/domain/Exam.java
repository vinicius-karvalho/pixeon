package com.vinicius.pixeon.challenge.domain;

import javax.persistence.Column;
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
    @Column(nullable = false)
    private String patientName;

    @Getter @Setter
    @Column(nullable = false)
    private Integer patientAge;

    @Getter @Setter
    @Column(nullable = false)
    private Gender patientGender;

    @Getter @Setter
    @Column(nullable = false)
    private String physicianName;

    @Getter @Setter
    @Column(nullable = false)
    private String physicianCRM;

    @Getter @Setter
    @Column(nullable = false)
    private String procedureName;

    @Getter @Setter
    private Boolean firstRead = Boolean.TRUE;
}
