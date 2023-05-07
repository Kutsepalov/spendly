package com.acceleron.spendly.core.dto.notification;

import com.acceleron.spendly.core.dto.notification.data.GroupInvitation;
import com.acceleron.spendly.core.dto.notification.data.NotificationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;

    private NotificationType type;

    private NotificationData data;
    private UUID senderId;
    private UUID receiverId;
    private boolean isRead;

    @Getter
    public enum NotificationType {
        INVITATION(GroupInvitation.class);

        private final Class<? extends NotificationData> dataClass;

        NotificationType(Class<? extends NotificationData> dataClass) {
            this.dataClass = dataClass;
        }
    }
}
