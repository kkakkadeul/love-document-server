package com.example.lovedocumentbackend.domain.category.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemExampleRepository extends JpaRepository<CategoryItemExample, Long> {
    List<CategoryItemExample> findAllByCategoryItemId(Long id);
}
