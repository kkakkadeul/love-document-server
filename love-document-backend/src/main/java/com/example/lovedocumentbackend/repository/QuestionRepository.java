package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByQuestionGroupId(Long id);
}
