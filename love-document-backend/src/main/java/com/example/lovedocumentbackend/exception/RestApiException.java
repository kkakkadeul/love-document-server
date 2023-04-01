package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.itf.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;
}
