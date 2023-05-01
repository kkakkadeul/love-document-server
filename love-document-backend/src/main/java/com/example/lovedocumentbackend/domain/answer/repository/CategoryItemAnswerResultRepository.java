package com.example.lovedocumentbackend.domain.answer.repository;

import com.example.lovedocumentbackend.domain.answer.entity.CategoryItemAnswerResult;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryItemAnswerResultRepository extends JpaRepository<CategoryItemAnswerResult, Long> {
    Optional<CategoryItemAnswerResult> findByCategoryItem(CategoryItem categoryItem);
}
