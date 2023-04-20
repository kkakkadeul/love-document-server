package com.example.lovedocumentbackend.domain.user.controller;

import com.example.lovedocumentbackend.SuccessResponse;
import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.user.dto.request.NicknameCheckRequest;
import com.example.lovedocumentbackend.domain.user.dto.response.UserCategoryResponse;
import com.example.lovedocumentbackend.domain.user.dto.response.UserInfoResponse;
import com.example.lovedocumentbackend.domain.user.dto.response.UserResponse;
import com.example.lovedocumentbackend.domain.user.service.UserService;
import com.example.lovedocumentbackend.domain.user.dto.request.UserRequest;
import com.example.lovedocumentbackend.exception.ErrorResponse;
import com.example.lovedocumentbackend.enumclass.CommonErrorCode;
import com.example.lovedocumentbackend.exception.RestApiException;
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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User api")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userApiLogicService;

    // 로그인 API
    @ApiDocumentResponse
    @Operation(summary = "로그인", description = "가입한 유저 로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "입력 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponse> signIn(@RequestBody @Valid UserRequest request, BindingResult result) {
        validBindingResult(result);

        return new ResponseEntity<>(userApiLogicService.login(request), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "회원가입", description = "새로운 유저 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "입력 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "닉네임 사용중", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid UserRequest request, BindingResult result) {
        validBindingResult(result);

        return new ResponseEntity<>(userApiLogicService.register(request), HttpStatus.OK);
    }

    // 유저 정보 조회
    @ApiDocumentResponse
    @Operation(summary = "닉네임 중복 확인", description = "닉네임 중복 체크")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "400", description = "입력 오류", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "닉네임 사용중", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/nickname")
    public ResponseEntity<SuccessResponse> nicknameCheck(@RequestBody @Valid NicknameCheckRequest request, BindingResult result){
        validBindingResult(result);

        return new ResponseEntity<>(userApiLogicService.nicknameCheck(request), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "유저 정보 조회", description = "닉네임, 선택 카테고리 개수, 링크 아이디")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserInfoResponse.class))),
    })
    @GetMapping("")
    public ResponseEntity<UserInfoResponse> userInfo(Authentication authentication){
        return new ResponseEntity<>(userApiLogicService.getUserInfo(authentication.getName()), HttpStatus.OK);
    }

    @ApiDocumentResponse
    @Operation(summary = "유저 카테고리 조회", description = "유저 선택 카테고리 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UserCategoryResponse.class))),
    })
    @GetMapping("/categories")
    public ResponseEntity<List<UserCategoryResponse>> userCategories(Authentication authentication){
        return new ResponseEntity<>(userApiLogicService.getUserCategories(authentication.getName()), HttpStatus.OK);
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
