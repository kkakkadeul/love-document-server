package com.example.lovedocumentbackend.ideal.entity;

import com.example.lovedocumentbackend.entity.CategoryItemExample;
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
public class IdealChoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Ideal ideal;

    @ManyToOne
    private CategoryItemExample categoryItemExample;
}
