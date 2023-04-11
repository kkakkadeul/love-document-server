package com.example.lovedocumentbackend.domain.ideal.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.ideal.dto.request.IdealRequest;
import com.example.lovedocumentbackend.domain.ideal.service.IdealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/ideals")
@RestController
public class IdealController {

    private final IdealService idealService;

    // GET : 답안 정보 요청.
    @GetMapping("")
    public void getIdeal(){
        idealService.userIdeal();
    }

    // POST : 답안 정보 입력
    @ApiDocumentResponse
    @Operation(summary = "유저 답변 저장", description = "유저 답변 저장하기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))}),
    })
    @PostMapping("")
    public ResponseEntity<Void> postIdeal(Authentication authentication, @RequestBody IdealRequest request){
        idealService.saveIdeal(authentication.getName(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // PATCH : 답안 정보 수정. 현재 없음

    // DELETE : 답안 정보 삭제. 현재 없음
}
