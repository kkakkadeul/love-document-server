package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.entity.CategoryItem;
import com.example.lovedocumentbackend.ideal.entity.Ideal;
import com.example.lovedocumentbackend.ideal.entity.IdealScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealScoreRepository extends JpaRepository<IdealScore, Long> {
    Optional<IdealScore> findByIdealAndCategoryItem(Ideal ideal, CategoryItem categoryItem);
}
