package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItemExample;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class IdealChoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ideal ideal;

    @ManyToOne
    private CategoryItemExample categoryItemExample;

    @ManyToOne
    private CategoryItem categoryItem;
}
