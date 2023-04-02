package com.example.lovedocumentbackend.ideal.repository;

import com.example.lovedocumentbackend.ideal.entity.IdealChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdealChoiceRepository extends JpaRepository<IdealChoice, Long> {
}
