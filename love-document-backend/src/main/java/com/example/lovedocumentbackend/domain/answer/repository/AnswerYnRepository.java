package com.example.lovedocumentbackend.domain.answer.repository;

import com.example.lovedocumentbackend.domain.answer.entity.Answer;
import com.example.lovedocumentbackend.domain.answer.entity.AnswerYn;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerYnRepository extends JpaRepository<AnswerYn, Long> {
    Optional<AnswerYn> findByAnswerAndCategoryItem(Answer answer, CategoryItem categoryItem);
}
