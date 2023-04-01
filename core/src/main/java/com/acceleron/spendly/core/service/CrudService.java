package com.acceleron.spendly.core.service;

public interface CrudService<T, I> {

    T findById(I id);
    T save(T account);
    T update(T account);
    T delete(I id);
}
