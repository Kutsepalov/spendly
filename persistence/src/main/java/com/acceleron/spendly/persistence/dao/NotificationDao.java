package com.acceleron.spendly.persistence.dao;

import com.acceleron.spendly.persistence.entity.Notification;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Long> {

    List<Notification> findAllByReceiverId(UUID receiverId);
    Optional<Notification> findByIdAndReceiverId(Long id, @NonNull UUID receiverId);
}
