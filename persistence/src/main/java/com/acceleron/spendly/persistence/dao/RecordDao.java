package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordDao extends JpaRepository<Record, Long> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM RECORDS " +
                    "WHERE RECORDS.USER_ID = :userId")
    List<Record> findAll(UUID userId);

    @Query(nativeQuery = true,
            value = "DELETE FROM RECORDS " +
                    "WHERE RECORDS.ID = :id AND RECORDS.USER_ID = :userId")
    @Modifying
    void deleteRecord(Long id, UUID userId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM RECORDS " +
                    "WHERE RECORDS.ACCOUNT_ID = :accountId AND RECORDS.USER_ID = :userId")
    List<Record> findByAccountId(UUID accountId, UUID userId);
}