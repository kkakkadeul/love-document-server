package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import com.example.lovedocumentbackend.domain.ideal.entity.IdealYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealYnRepository extends JpaRepository<IdealYn, Long> {
    Optional<IdealYn> findByIdealAndCategoryItem(Ideal ideal, CategoryItem categoryItem);
}
