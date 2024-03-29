package com.example.lovedocumentbackend.domain.ideal.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.ideal.dto.request.IdealRequest;
import com.example.lovedocumentbackend.domain.ideal.dto.response.IdealResponse;
import com.example.lovedocumentbackend.domain.ideal.service.IdealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ideal", description = "Ideal api")
@RequiredArgsConstructor
@RequestMapping("/ideals")
@RestController
public class IdealController {

    private final IdealService idealService;

    // GET : 답안 정보 요청.
    @ApiDocumentResponse
    @Operation(summary = "내 기준 답안 조회", description = "유저 답안 조회하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = IdealResponse.class)))}),
    })
    @GetMapping("")
    public ResponseEntity<List<IdealResponse>> getIdeal(Authentication authentication){

        return new ResponseEntity<>(idealService.userIdeal(authentication.getName()), HttpStatus.OK);
    }

    // POST : 답안 정보 입력
    @ApiDocumentResponse
    @Operation(summary = "유저 답안 생성", description = "유저 답변 저장하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
    })
    @PostMapping("")
    public ResponseEntity<Void> postIdeal(Authentication authentication, @RequestBody IdealRequest request){
        idealService.saveIdeal(authentication.getName(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // PATCH : 답안 정보 수정
//    @PatchMapping("")
//    public ResponseEntity<Void> patchIdeal(Authentication authentication, @RequestBody IdealRequest request){
//        idealService.saveIdeal(authentication.getName(), request);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
