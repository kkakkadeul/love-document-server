package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import com.example.lovedocumentbackend.domain.ideal.entity.IdealChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdealChoiceRepository extends JpaRepository<IdealChoice, Long> {
    Optional<IdealChoice> findByIdealAndCategoryItemExample(Ideal ideal, CategoryItemExample categoryItemExample);
    List<IdealChoice> findByIdealAndCategoryItem(Ideal ideal, CategoryItem categoryItem);

}
