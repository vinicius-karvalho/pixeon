package com.vinicius.pixeon.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.vinicius.pixeon.challenge.domain.Exam;

/**
 * @author vinícius.carvalho
 */
public interface ExamRepository extends JpaRepository<Exam, Long> {

    @Modifying
    @Query("update Exam e set e.firstRead = false where e.id = ?1")
    int updatefirstReadToFalse(Long id);
}
