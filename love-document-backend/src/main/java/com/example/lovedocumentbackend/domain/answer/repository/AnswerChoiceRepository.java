package com.example.lovedocumentbackend.domain.answer.repository;

import com.example.lovedocumentbackend.domain.answer.entity.Answer;
import com.example.lovedocumentbackend.domain.answer.entity.AnswerChoice;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerChoiceRepository extends JpaRepository<AnswerChoice, Long> {
    Optional<AnswerChoice> findByAnswerAndCategoryItem(Answer answer, CategoryItem categoryItem);
}
