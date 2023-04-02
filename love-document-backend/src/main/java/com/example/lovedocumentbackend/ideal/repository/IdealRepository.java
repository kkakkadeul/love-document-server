package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.Ideal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealRepository extends JpaRepository<Ideal, Long> {
}
