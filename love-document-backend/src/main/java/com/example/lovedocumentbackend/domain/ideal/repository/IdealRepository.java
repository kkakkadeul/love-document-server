package com.example.lovedocumentbackend.domain.ideal.repository;

import com.example.lovedocumentbackend.domain.ideal.entity.Ideal;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdealRepository extends JpaRepository<Ideal, Long> {
//    Optional<Ideal> findByQuestionGroupId(Long id);
    Optional<Ideal> findByQuestionGroupIdAndStatus(Long id, BooleanType status);
    Optional<Ideal> findTopByQuestionGroupIdOrderByCreatedAtDesc(Long id);
}
