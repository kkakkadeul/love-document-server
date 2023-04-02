package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.IdealRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealRangeRepository extends JpaRepository<IdealRange, Long> {
}
