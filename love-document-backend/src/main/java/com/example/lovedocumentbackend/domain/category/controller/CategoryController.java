package com.example.lovedocumentbackend.domain.category.controller;

import com.example.lovedocumentbackend.config.ApiDocumentResponse;
import com.example.lovedocumentbackend.domain.category.dto.response.CategoryResponse;
import com.example.lovedocumentbackend.domain.category.service.CategoryService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "카테고리 관련 api")
@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryApiLogicService;

    @ApiDocumentResponse
    @Operation(summary = "카테고리 목록", description = "카테고리 목록 불러오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))}),
    })
    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> getCategory() {
        return new ResponseEntity<>(categoryApiLogicService.allCategory(), HttpStatus.OK);
    }
}
