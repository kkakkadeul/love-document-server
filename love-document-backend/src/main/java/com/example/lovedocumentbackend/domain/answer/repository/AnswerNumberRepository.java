package com.example.lovedocumentbackend.domain.answer.repository;

import com.example.lovedocumentbackend.domain.answer.entity.AnswerNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerNumberRepository extends JpaRepository<AnswerNumber, Long> {
}
