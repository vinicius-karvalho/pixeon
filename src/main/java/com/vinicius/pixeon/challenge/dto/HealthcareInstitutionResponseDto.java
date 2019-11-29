package com.vinicius.pixeon.challenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class HealthcareInstitutionResponseDto {

    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String cnpj;
    @Getter @Setter
    private String message;
    @Getter @Setter
    private Integer coins;

    public HealthcareInstitutionResponseDto(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }
}
