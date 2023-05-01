package com.example.lovedocumentbackend.domain.category.service;

import com.example.lovedocumentbackend.domain.category.dto.response.CategoryResponse;
import com.example.lovedocumentbackend.domain.category.dto.response.CategoryItemResponse;
import com.example.lovedocumentbackend.domain.category.entity.Category;
import com.example.lovedocumentbackend.domain.category.entity.CategoryItem;
import com.example.lovedocumentbackend.domain.category.repository.CategoryItemRepository;
import com.example.lovedocumentbackend.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryItemRepository categoryItemRepository;

    public List<CategoryResponse> allCategory() {
        List<CategoryResponse> CategoryApiResponseList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();

        categoryList.forEach(category -> {
            CategoryApiResponseList.add(response(category));
        });

        return CategoryApiResponseList;
    }


    private CategoryResponse response(Category category){

        List<CategoryItemResponse> categoryItemResponseList = new ArrayList<>();
        List<CategoryItem> categoryItemList = categoryItemRepository.findAllByCategoryId(category.getId());

        categoryItemList.forEach(categoryItem -> {
            CategoryItemResponse categoryItemApiResponse = CategoryItemResponse.builder()
                    .id(categoryItem.getId())
                    .title(categoryItem.getTitle())
                    .build();
            categoryItemResponseList.add(categoryItemApiResponse);
        });


        CategoryResponse categoryApiResponse = CategoryResponse.builder()
                .id(category.getId())
                .emoji(category.getEmoji())
                .title(category.getTitle())
                .categoryItemList(categoryItemResponseList)
                .build();

        return categoryApiResponse;
    }
}
