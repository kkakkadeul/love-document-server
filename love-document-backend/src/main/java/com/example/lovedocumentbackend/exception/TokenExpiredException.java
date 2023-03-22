package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.enumclass.ErrorCode;

public class TokenExpiredException extends CustomException{
    public TokenExpiredException() {
        super(ErrorCode.TOKEN_EXPIRED);
    }
}
