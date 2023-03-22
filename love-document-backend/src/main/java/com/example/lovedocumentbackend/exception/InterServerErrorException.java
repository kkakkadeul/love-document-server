package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.enumclass.ErrorCode;

public class InterServerErrorException extends CustomException{
    private static final long serialVersionUID = -2116671122895194101L;

    public InterServerErrorException() {
        super(ErrorCode.INTER_SERVER_ERROR);
    }
}
