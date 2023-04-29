package com.acceleron.spendly.core.service.impl;

import com.acceleron.spendly.core.dto.CategoryDto;
import com.acceleron.spendly.core.mapper.CategoryMapper;
import com.acceleron.spendly.core.service.CategoryService;
import com.acceleron.spendly.persistence.dao.CategoryDao;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto findById(UUID id) {
        return categoryDao.findById(id)
                .map(categoryMapper::toCategoryDto)
                .orElse(null);
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        return categoryMapper.toCategoryDto(
                categoryDao.save(categoryMapper.toCategoryEntity(categoryDto)));
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto.getId() == null) {
            throw new IllegalIdentifierException("Category ID is required.");
        }
        return categoryMapper.toCategoryDto(categoryDao.save(categoryMapper.toCategoryEntity(categoryDto)));
    }

    @Override
    public CategoryDto delete(UUID id) {
        return categoryMapper.toCategoryDto(categoryDao.deleteCategory(id));
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll()
                .stream()
                .filter(category -> category.getParentCategoryId() == null)
                .map(categoryMapper::toCategoryDto).toList();
    }
}
