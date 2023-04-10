package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.entity.CategoryItemExample;
import com.example.lovedocumentbackend.ideal.entity.Ideal;
import com.example.lovedocumentbackend.ideal.entity.IdealChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealChoiceRepository extends JpaRepository<IdealChoice, Long> {
    Optional<IdealChoice> findByIdealAndCategoryItemExample(Ideal ideal, CategoryItemExample categoryItemExample);
}
