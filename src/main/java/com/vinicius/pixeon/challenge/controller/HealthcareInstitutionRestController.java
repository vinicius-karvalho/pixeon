package com.vinicius.pixeon.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.pixeon.challenge.domain.HealthcareInstitution;
import com.vinicius.pixeon.challenge.dto.HealthcareInstitutionRequestDto;
import com.vinicius.pixeon.challenge.dto.HealthcareInstitutionResponseDto;
import com.vinicius.pixeon.challenge.service.HealthcareInstitutionService;

/**
 * @author vinícius.carvalho
 */
@RestController
@RequestMapping("v1/helthcare/institution")
public class HealthcareInstitutionRestController {

    @Autowired
    private HealthcareInstitutionService service;

    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    public HealthcareInstitutionResponseDto retrieveHealthcareInstitution(@PathVariable(name = "id", required = true) Long id) {
        HealthcareInstitution healthcareInstitution = service.findById(id);
        HealthcareInstitutionResponseDto dto = new HealthcareInstitutionResponseDto();
        dto.setId(healthcareInstitution.getId());
        dto.setName(healthcareInstitution.getName());
        dto.setCnpj(healthcareInstitution.getCnpj());
        dto.setCoins(healthcareInstitution.getCoins());
        return dto;
    }

    @PostMapping(produces = "application/json; charset=utf-8", consumes = "application/json")
    public HealthcareInstitutionResponseDto createHealthcareInstitution(@RequestBody HealthcareInstitutionRequestDto reqDto) {
        HealthcareInstitution newInstitution = service.save(reqDto);
        HealthcareInstitutionResponseDto dto = new HealthcareInstitutionResponseDto(newInstitution.getId(), //
                                                                                    newInstitution.getName(), //
                                                                                    newInstitution.getCnpj(), //
                                                                                    "Instituição de saúde criada com sucesso", //
                                                                                    newInstitution.getCoins());
        return dto;
    }

}
