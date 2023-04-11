package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.IdealScore;
import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealScoreRepository extends JpaRepository<IdealScore, Long> {
    Optional<IdealScore> findByIdealAndCategoryItem(Ideal ideal, CategoryItem categoryItem);
}
