package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepository extends JpaRepository<CategoryItem, Long> {
    List<CategoryItem> findAllByCategoryId(Long id);
}
