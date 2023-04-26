package com.example.lovedocumentbackend.enumclass;

import com.example.lovedocumentbackend.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    //User
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "인증된 유저가 아니예요.", "U000"),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없어요.", "U001"),
    PASSWORD_ERR(HttpStatus.NOT_FOUND, "비밀번호가 틀렸어요.", "U002"),
    INVALID_USER_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임 형식이 틀렸어요.", "U003"),
    INVALID_USER_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 형식이 틀렸어요.", "U004"),
    AlREADY_USER(HttpStatus.CONFLICT, "중복된 유저예요.", "U005"),

    //Question
    NOT_FOUND_QUESTION(HttpStatus.NOT_FOUND, "질문을 찾을 수 없어요.", "Q001"),

    //Ideal
    NOT_FOUND_IDEAL(HttpStatus.NOT_FOUND, "답안을 찾을 수 없어요.", "I001"),
    INVALID_PARAMETER_IDEAL(HttpStatus.BAD_REQUEST, "타입별 데이터 입력이 잘 못 되었어요.", "I002"),
    AlREADY_IDEAL_REQUEST(HttpStatus.CONFLICT, "중복된 요청이예요.", "I003"),

    //Answer
    NOT_FOUND_ANSWER(HttpStatus.NOT_FOUND, "답변을 찾을 수 없어요.", "A001"),
    INVALID_PARAMETER_ANSWER(HttpStatus.BAD_REQUEST, "타입별 데이터 입력이 잘 못 되었어요.", "A002"),
    AlREADY_ANSWER_REQUEST(HttpStatus.CONFLICT, "중복된 요청이예요.", "A003"),

    //Category
    NOT_FOUND_CATEGORY_ITEM(HttpStatus.NOT_FOUND, "카테고리 아이템을 찾을 수 없어요.", "C001"),
    NOT_FOUND_CATEGORY_ITEM_EXAMPLE(HttpStatus.NOT_FOUND, "카테고리 아이템 예시를 찾을 수 없어요.", "C002"),

    // Token
    TOKEN_GENERATION_ERROR(HttpStatus.NOT_FOUND, "토큰이 비어있습니다.", "T001"),
    //All
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러예요.", "A000"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "데이터가 존재하지 않아요.", "A001"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "입력이 잘 못 되었어요.", "A002"),
    AlREADY_REQUEST(HttpStatus.CONFLICT, "중복된 요청이예요.", "A003"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;
}
