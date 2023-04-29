package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryDao extends JpaRepository<Category, UUID> {
    @Query(nativeQuery = true,
            value = "DELETE FROM CATEGORIES " +
                    "WHERE CATEGORIES.ID = :id ")
    @Modifying
    Category deleteCategory(UUID id);
}