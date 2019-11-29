package com.vinicius.pixeon.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author vinícius.carvalho
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class HealthcareInstitutionRequestDto {

    @Getter @Setter
    private String name;
    @Getter @Setter
    private String cnpj;

}
