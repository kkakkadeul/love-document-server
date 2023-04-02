package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.IdealYn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealYnRepository extends JpaRepository<IdealYn, Long> {
}
