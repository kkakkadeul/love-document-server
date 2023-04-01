package com.example.lovedocumentbackend.repository;

import com.example.lovedocumentbackend.entity.QuestionGroup;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionGroupRepository extends JpaRepository<QuestionGroup, Long> {
    List<QuestionGroup> findAllByUserId(Long id);

    @Enumerated(EnumType.STRING)
    Optional<QuestionGroup> findByUserIdAndStatus(Long id, BooleanType status);
}
