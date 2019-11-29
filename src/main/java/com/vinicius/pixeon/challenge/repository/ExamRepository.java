package com.vinicius.pixeon.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.pixeon.challenge.domain.Exam;

/**
 * @author vinícius.carvalho
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {

}
