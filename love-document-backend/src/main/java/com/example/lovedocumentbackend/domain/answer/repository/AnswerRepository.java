package com.example.lovedocumentbackend.domain.answer.repository;

import com.example.lovedocumentbackend.domain.answer.entity.Answer;
import com.example.lovedocumentbackend.domain.question.entity.QuestionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestionGroup(QuestionGroup questionGroup);
}
