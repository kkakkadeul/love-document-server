package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<List<Question>> findAllByQuestionGroupId(Long id);
}
