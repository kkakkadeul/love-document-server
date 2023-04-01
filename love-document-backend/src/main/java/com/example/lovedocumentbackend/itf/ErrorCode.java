package com.example.lovedocumentbackend.itf;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();

    String getCode();
}
