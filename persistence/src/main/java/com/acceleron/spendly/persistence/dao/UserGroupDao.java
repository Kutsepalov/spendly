package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserGroupDao extends JpaRepository<UserGroup, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM GROUPS " +
                    "INNER JOIN USERS_GROUPS UG on GROUPS.ID = UG.GROUP_ID " +
                    "WHERE UG.USER_ID = :userId")
    List<UserGroup> findAll(UUID userId);

    @Query(nativeQuery = true,
            value = "DELETE FROM USERS_GROUPS " +
                    "WHERE USERS_GROUPS.GROUP_ID = :id AND USERS_GROUPS.USER_ID = :userId; " +
                    "DELETE FROM GROUPS " +
                    "WHERE GROUPS.ID = :id;")
    @Modifying
    void delete(UUID id, UUID userId);
}
