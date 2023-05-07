package com.acceleron.spendly.core.mapper;

public interface EntityMapper<E,T> {

    T toDto(E entity);
    E toEntity(T dto);
}
