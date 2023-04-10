package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.Ideal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealRepository extends JpaRepository<Ideal, Long> {
    Optional<Ideal> findByQuestionGroupId(Long id);
}
