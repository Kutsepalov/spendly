package com.acceleron.spendly.core.service;

import com.acceleron.spendly.core.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

public interface CategoryService extends CrudService<CategoryDto, UUID> {

    List<CategoryDto> findAll();
}
