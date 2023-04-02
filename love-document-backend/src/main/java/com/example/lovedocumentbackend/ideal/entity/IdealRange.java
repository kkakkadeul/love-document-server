package com.example.lovedocumentbackend.ideal.entity;

import com.example.lovedocumentbackend.entity.BaseEntity;
import com.example.lovedocumentbackend.entity.CategoryItem;
import jakarta.persistence.*;
import lombok.*;


@ToString(exclude ={"ideal", "categoryItem"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class IdealRange extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer more;

    private Integer less;

    @ManyToOne
    private Ideal ideal;

    @ManyToOne
    private CategoryItem categoryItem;

}
