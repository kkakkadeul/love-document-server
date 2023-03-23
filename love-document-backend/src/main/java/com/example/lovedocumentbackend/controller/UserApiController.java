package com.example.lovedocumentbackend.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.dto.request.UserApiRequest;
import com.example.lovedocumentbackend.dto.response.ErrorResponse;
import com.example.lovedocumentbackend.dto.response.UserApiResponse;
import com.example.lovedocumentbackend.exception.InvalidParameterException;
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
    public ResponseEntity<UserApiResponse> signIn(@RequestBody @Valid UserApiRequest request, BindingResult result) throws Exception {

        if (result.hasErrors()){
            throw new InvalidParameterException(result);
        }

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
    public ResponseEntity<UserApiResponse> signUp(@RequestBody @Valid UserApiRequest request, BindingResult result) throws Exception {

        if (result.hasErrors()){
            throw new InvalidParameterException(result);
        }

        return new ResponseEntity<>(userApiLogicService.register(request), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

}
