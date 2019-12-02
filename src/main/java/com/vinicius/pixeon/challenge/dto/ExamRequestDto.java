package com.vinicius.pixeon.challenge.dto;

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
public class ExamRequestDto {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private Long healthcareId;

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
