package com.example.lovedocumentbackend.ideal.entity;


import com.example.lovedocumentbackend.entity.BaseEntity;
import com.example.lovedocumentbackend.entity.CategoryItem;
import com.example.lovedocumentbackend.enumclass.BooleanType;
import jakarta.persistence.*;
import lombok.*;


@ToString(exclude ={"ideal", "categoryItem"})
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Entity
public class IdealYn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BooleanType content;

    @ManyToOne
    private Ideal ideal;

    @ManyToOne
    private CategoryItem categoryItem;
}
