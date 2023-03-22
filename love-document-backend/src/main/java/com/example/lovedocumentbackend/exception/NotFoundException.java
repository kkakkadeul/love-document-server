package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.enumclass.ErrorCode;

public class NotFoundException extends CustomException{
    private static final long serialVersionUID = -2116671122895194101L;

    public NotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}
