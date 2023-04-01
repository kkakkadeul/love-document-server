package com.example.lovedocumentbackend.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.dto.request.UserApiRequest;
import com.example.lovedocumentbackend.dto.response.ErrorResponse;
import com.example.lovedocumentbackend.dto.response.UserApiResponse;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
import com.example.lovedocumentbackend.service.UserApiLogicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "유저 관련 api")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController {
    private final UserApiLogicService userApiLogicService;

    // 로그인 API
    @ApiDocumentResponse
    @Operation(summary = "로그인", description = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "입력 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<UserApiResponse> signIn(@RequestBody @Valid UserApiRequest request, BindingResult result) {
        validBindingResult(result);

        return new ResponseEntity<>(userApiLogicService.login(request), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserApiResponse.class))),
            @ApiResponse(responseCode = "400", description = "입력 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "닉네임 사용중", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<UserApiResponse> signUp(@RequestBody @Valid UserApiRequest request, BindingResult result) {
        validBindingResult(result);

        return new ResponseEntity<>(userApiLogicService.register(request), HttpStatus.OK);
    }

    private void validBindingResult(BindingResult result) {
        if (result.hasErrors()){
            if(result.getFieldError().getField().equals("nickname")){
                throw new RestApiException(CommonErrorCode.INVALID_USER_NICKNAME);
            }else{
                throw new RestApiException(CommonErrorCode.INVALID_USER_PASSWORD);
            }
        }
    }
}
