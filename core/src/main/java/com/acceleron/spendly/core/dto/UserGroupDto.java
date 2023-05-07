package com.acceleron.spendly.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupDto {

    private UUID id;
    private String name;
    private String color;
    private Set<UUID> userIds = new HashSet<>();
}
