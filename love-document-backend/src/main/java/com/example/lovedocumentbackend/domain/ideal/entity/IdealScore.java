package com.example.lovedocumentbackend.domain.ideal.entity;

import com.example.lovedocumentbackend.BaseEntity;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
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
public class IdealScore extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne
    private Ideal ideal;

    @ManyToOne
    private CategoryItem categoryItem;
}
