package com.example.lovedocumentbackend.enumclass;

import com.example.lovedocumentbackend.itf.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    //User
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.", "U001"),
    PASSWORD_ERR(HttpStatus.NOT_FOUND, "비밀번호가 틀렸습니다.", "U002"),
    INVALID_USER_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 형식이 틀렸습니다.", "U003"),
    INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 형식이 틀렸습니다.", "U004"),
    AlREADY_USER(HttpStatus.CONFLICT, "중복된 유저입니다.", "U005"),

    //Question
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "질문을 찾을 수 없습니다.", "Q001"),

    //All
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러", "A000"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "데이터가 존재하지 않습니다.", "A001"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력이 잘 못 되었습니다", "A002"),
    AlREADY_REQUEST(HttpStatus.CONFLICT, "중복된 요청입니다.", "A003"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
