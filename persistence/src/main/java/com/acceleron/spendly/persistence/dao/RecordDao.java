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

    List<Record> findByUserId(UUID id);

    @Query(nativeQuery = true,
            value = "DELETE FROM RECORDS " +
                    "WHERE RECORDS.ID = :id AND RECORDS.USER_ID = :userId ")
    @Modifying
    Record deleteRecord(Long id, UUID userId);
}