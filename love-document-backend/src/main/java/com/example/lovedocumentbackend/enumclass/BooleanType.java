package com.example.lovedocumentbackend.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BooleanType {

    N(0, "NO" , "거짓"),
    Y(1, "YES", "참");

    private Integer id;
    private String title;
    private String description;
}
