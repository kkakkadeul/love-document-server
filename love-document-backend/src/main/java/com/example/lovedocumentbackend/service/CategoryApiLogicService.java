package com.example.lovedocumentbackend.service;

import com.example.lovedocumentbackend.dto.response.CategoryApiResponse;
import com.example.lovedocumentbackend.dto.response.CategoryItemApiResponse;
import com.example.lovedocumentbackend.entity.Category;
import com.example.lovedocumentbackend.entity.CategoryItem;
import com.example.lovedocumentbackend.repository.CategoryItemRepository;
import com.example.lovedocumentbackend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryApiLogicService {

    private final CategoryRepository categoryRepository;

    private final CategoryItemRepository categoryItemRepository;

    public List<CategoryApiResponse> allCategory() {
        List<CategoryApiResponse> CategoryApiResponseList = new ArrayList<>();
        List<Category> categoryList = categoryRepository.findAll();

        categoryList.forEach(category -> {
            CategoryApiResponseList.add(response(category));
        });

        return CategoryApiResponseList;
    }


    private CategoryApiResponse response(Category category){

        List<CategoryItemApiResponse> categoryItemResponseList = new ArrayList<>();
        List<CategoryItem> categoryItemList = categoryItemRepository.findAllByCategoryId(category.getId());

        categoryItemList.forEach(categoryItem -> {
            CategoryItemApiResponse categoryItemApiResponse = CategoryItemApiResponse.builder()
                    .id(categoryItem.getId())
                    .title(categoryItem.getTitle())
                    .build();
            categoryItemResponseList.add(categoryItemApiResponse);
        });


        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .categoryItemList(categoryItemResponseList)
                .build();

        return categoryApiResponse;
    }
}
