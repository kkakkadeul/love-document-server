package com.example.lovedocumentbackend.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    INVALID_PARAMETER(400,"INVALID_PARAMETER","입력 오류입니다."),
    NOT_FOUND(404,"NOT_FOUND","유저가 존재하지 않습니다."),
    ALREADY_USED(409,"ALREADY_USED","중복 요청입니다."),
    TOKEN_EXPIRED(410,"TOKEN_EXPIRED","만료된 토큰입니다."),
    INTER_SERVER_ERROR(500,"COMMON","서버 오류입니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
