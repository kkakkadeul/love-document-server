package com.example.lovedocumentbackend.exception;

import com.example.lovedocumentbackend.dto.response.ErrorResponse;
import com.example.lovedocumentbackend.enumclass.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("handleHttpRequestMethodNotSupportedException", e);

        final ErrorResponse response
                = ErrorResponse
                .create()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .message(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidParameterException(InvalidParameterException e) {
        logger.error("handleInvalidParameterException", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response
                = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .message(e.toString())
                .errors(e.getErrors());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        logger.error("handleAllException", e);

        ErrorCode errorCode = e.getErrorCode();

        ErrorResponse response
                = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        logger.error("handleException", e);

        ErrorResponse response
                = ErrorResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
