package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.CategoryItemIdealResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryItemIdealResultRepository extends JpaRepository<CategoryItemIdealResult, Long> {
    Optional<CategoryItemIdealResult> findByCategoryItem(CategoryItem categoryItem);
}
