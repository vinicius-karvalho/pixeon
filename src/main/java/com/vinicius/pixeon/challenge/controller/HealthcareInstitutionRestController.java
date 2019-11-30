package com.vinicius.pixeon.challenge.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        Optional<HealthcareInstitution> healthcareInstitution = service.findById(id);
        if (healthcareInstitution.isPresent()) {
            HealthcareInstitutionResponseDto dto = new HealthcareInstitutionResponseDto();
            dto.setId(healthcareInstitution.get().getId());
            dto.setName(healthcareInstitution.get().getName());
            dto.setCnpj(healthcareInstitution.get().getCnpj());
            dto.setCoins(healthcareInstitution.get().getCoins());
            return dto;
        } else {
            HealthcareInstitutionResponseDto dto = new HealthcareInstitutionResponseDto();
            dto.setMessage("HealthcareInstitution not found");
            return dto;
        }
    }

    @PostMapping(produces = "application/json; charset=utf-8", consumes = "application/json")
    public HealthcareInstitutionResponseDto createHealthcareInstitution(@RequestBody HealthcareInstitutionRequestDto reqDto) {
        HealthcareInstitution newInstitution = service.save(reqDto);
        HealthcareInstitutionResponseDto dto = new HealthcareInstitutionResponseDto(newInstitution.getId(), //
                                                                                    newInstitution.getName(), //
                                                                                    newInstitution.getCnpj(), //
                                                                                    null, //
                                                                                    newInstitution.getCoins());
        return dto;
    }

    @PutMapping(value = "/{id}")
    public Integer chargeOneCoin(@PathVariable(name = "id", required = true) Long id) {
        return service.chargeOneCoin(id);
    }

}
