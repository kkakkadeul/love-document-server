package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.ideal.entity.ChoiceResultExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoiceResultExampleRepository extends JpaRepository<ChoiceResultExample, Long> {
    Optional<ChoiceResultExample> findByCategoryItemExample(CategoryItemExample categoryItemExample);
}
