package com.acceleron.spendly.controller.category;

import com.acceleron.spendly.core.dto.CategoryDto;
import com.acceleron.spendly.core.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable UUID id) {
        return categoryService.findById(id);
    }

    @GetMapping
    public List<CategoryDto> getCategories() {
        return categoryService.findAll();
    }
}
