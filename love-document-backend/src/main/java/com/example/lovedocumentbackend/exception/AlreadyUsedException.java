package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.enumclass.ErrorCode;

public class AlreadyUsedException extends CustomException{
    public AlreadyUsedException() {
        super(ErrorCode.ALREADY_USED);
    }
}
