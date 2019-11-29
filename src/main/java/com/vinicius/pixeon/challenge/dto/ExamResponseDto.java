package com.vinicius.pixeon.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vinicius.pixeon.challenge.domain.Exam;
import com.vinicius.pixeon.challenge.type.Gender;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(value = Include.NON_NULL)
public class ExamResponseDto {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String healthcareName;

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

    @Getter @Setter
    private String message;

    public ExamResponseDto(Exam exam, String message) {
        this.id = exam.getId();
        this.healthcareName = exam.getHealthcareInstitution().getName();
        this.patientAge = exam.getPatientAge();
        this.patientGender = exam.getPatientGender();
        this.patientName = exam.getPatientName();
        this.physicianCRM = exam.getPhysicianCRM();
        this.physicianName = exam.getPhysicianName();
        this.procedureName = exam.getProcedureName();
        this.message = message;
    }
}
