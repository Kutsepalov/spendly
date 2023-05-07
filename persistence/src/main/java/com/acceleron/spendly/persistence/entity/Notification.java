package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.Notification.TABLE_NAME;

@Getter
@Setter
@ToString
@Entity
@Table(name = TABLE_NAME)
@RequiredArgsConstructor
@NoArgsConstructor
public class Notification {

    public static final String TABLE_NAME = "NOTIFICATIONS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "NAME")
    private String data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="SENDER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User sender;
    @Column(name = "SENDER_ID")
    private UUID senderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="RECEIVER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User receiver;
    @NonNull
    @Column(name = "RECEIVER_ID")
    private UUID receiverId;

    @Column(name = "IS_READ")
    private boolean isRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification that)) {
            return false;
        }
        if (isRead != that.isRead) {
            return false;
        }
        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!type.equals(that.type)) {
            return false;
        }
        if (!Objects.equals(data, that.data)) {
            return false;
        }
        if (!Objects.equals(senderId, that.senderId)) {
            return false;
        }
        return receiverId.equals(that.receiverId);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + type.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (senderId != null ? senderId.hashCode() : 0);
        result = 31 * result + receiverId.hashCode();
        result = 31 * result + (isRead ? 1 : 0);
        return result;
    }
}
