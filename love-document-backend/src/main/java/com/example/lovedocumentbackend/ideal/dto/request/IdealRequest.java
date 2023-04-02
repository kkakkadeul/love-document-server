package com.example.lovedocumentbackend.ideal.dto.request;


import com.example.lovedocumentbackend.enumclass.BooleanType;
import com.example.lovedocumentbackend.enumclass.QuestionType;
import lombok.Getter;

import java.util.List;

@Getter
public class IdealRequest {

    private List<ideal> idealList;

    @Getter
    static public class ideal {

        private Long categoryItemId;

        private QuestionType questionType;

        private List<Long> choiceIdList;

        private List<Integer> rangeNumList;

        private List<Integer> scoreNumList;

        private List<BooleanType> ynBoolList;
    }
}
