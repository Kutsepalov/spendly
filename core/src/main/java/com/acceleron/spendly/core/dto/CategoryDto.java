package com.acceleron.spendly.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

import static java.util.Collections.emptyList;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private UUID id;
    private String name;
    private String icon;
    private String color;
    private String parentCategoryId;
    private List<CategoryDto> childCategories = emptyList();
}
