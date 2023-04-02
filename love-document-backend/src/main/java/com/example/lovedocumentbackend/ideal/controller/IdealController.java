package com.example.lovedocumentbackend.ideal.controller;

import com.example.lovedocumentbackend.ideal.dto.request.IdealRequest;
import com.example.lovedocumentbackend.ideal.service.IdealService;
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
    @PostMapping("")
    public ResponseEntity<Void> postIdeal(Authentication authentication, @RequestBody IdealRequest request){
        idealService.saveIdeal(authentication.getName(), request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // PATCH : 답안 정보 수정. 현재 없음

    // DELETE : 답안 정보 삭제. 현재 없음
}
