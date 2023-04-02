package com.example.lovedocumentbackend.entity;


import com.example.lovedocumentbackend.ideal.entity.IdealChoice;
import com.example.lovedocumentbackend.ideal.entity.IdealScore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@ToString(exclude = {"categoryItem"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="category_item_example")
@Entity
public class CategoryItemExample extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private CategoryItem categoryItem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private List<IdealChoice> idealChoiceList;
}
