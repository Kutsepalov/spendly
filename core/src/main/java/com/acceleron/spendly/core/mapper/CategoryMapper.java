package com.acceleron.spendly.core.mapper;

import com.acceleron.spendly.core.dto.CategoryDto;
import com.acceleron.spendly.persistence.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "childCategories", target = "childCategories", qualifiedByName = "childCategoriesToCategoryDto")
    CategoryDto toCategoryDto(Category model);
    @Mapping(source = "childCategories", target = "childCategories", qualifiedByName = "childCategoriesToCategoryEntity")
    Category toCategoryEntity(CategoryDto model);

    @Named("childCategoriesToCategoryDto")
    default List<CategoryDto> dtoChildCategories(List<Category> childCategories) {
        return childCategories.stream().map(this::toCategoryDto).toList();
    }

    @Named("childCategoriesToCategoryEntity")
    default List<Category> entityChildCategories(List<CategoryDto> childCategories) {
        return childCategories.stream().map(this::toCategoryEntity).toList();
    }
}
