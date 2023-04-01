package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.Category;
import com.example.lovedocumentbackend.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
