package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.IdealScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealScoreRepository extends JpaRepository<IdealScore, Long> {
}
