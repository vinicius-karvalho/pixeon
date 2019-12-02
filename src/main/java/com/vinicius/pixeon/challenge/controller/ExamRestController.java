package com.vinicius.pixeon.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.pixeon.challenge.dto.ExamRequestDto;
import com.vinicius.pixeon.challenge.dto.ExamResponseDto;
import com.vinicius.pixeon.challenge.service.ExamService;

/**
 * @author vinícius.carvalho
 */
@RestController
@RequestMapping("v1/exam")
public class ExamRestController {

    @Autowired
    private ExamService service;

    @GetMapping(value = "/{idExam}/{idHealthcare}", produces = "application/json; charset=utf-8")
    public ExamResponseDto retrieveExam(@PathVariable(name = "idExam", required = true) Long idExam,
                                        @PathVariable(name = "idHealthcare", required = false) Long idHealthcare) {
        return service.findById(idExam, idHealthcare);
    }

    @PostMapping(produces = "application/json; charset=utf-8", consumes = "application/json")
    public ExamResponseDto createExam(@RequestBody ExamRequestDto reqDto) {
        return service.save(reqDto);
    }

    @PutMapping(produces = "application/json; charset=utf-8", consumes = "application/json")
    public ExamResponseDto updateExam(@RequestBody ExamRequestDto reqDto) {
        return service.save(reqDto);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteExam(@PathVariable(name = "id", required = true) Long id) {
        service.delete(id);
    }

}
