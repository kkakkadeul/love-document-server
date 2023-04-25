package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.YnResultExample;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YnResultExampleRepository extends JpaRepository<YnResultExample, Long> {
    Optional<YnResultExample> findByAnswerAndCategoryItem(BooleanType answer, CategoryItem categoryItem);
}
