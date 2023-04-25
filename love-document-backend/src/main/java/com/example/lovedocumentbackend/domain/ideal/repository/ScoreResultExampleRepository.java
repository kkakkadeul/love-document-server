package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.ScoreResultExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreResultExampleRepository extends JpaRepository<ScoreResultExample, Long> {
    Optional<ScoreResultExample> findByAnswerAndCategoryItem(Integer answer, CategoryItem categoryItem);
}
