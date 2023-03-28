package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.CategoryItemExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemExampleRepository extends JpaRepository<CategoryItemExample, Long> {
    List<CategoryItemExample> findAllByCategoryItemId(Long id);
}
