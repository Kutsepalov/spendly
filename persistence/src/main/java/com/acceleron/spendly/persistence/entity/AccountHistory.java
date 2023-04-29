package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.AccountHistory.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountHistory {
    public static final String TABLE_NAME = "ACCOUNTS_HISTORY";

    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private Account account;
    @Column(name = "ACCOUNT_ID")
    private UUID accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="RECORD_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ToString.Exclude
    private Record recordEntity;
    @Column(name = "RECORD_ID")
    private Long recordId;

    @Column(name = "AMOUNT")
    private BigDecimal balance;

    @Column(name = "CHANGE_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp changeDatetime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountHistory that)) {
            return false;
        }
        if (!Objects.equals(id, that.id)) {
            return false;
        }
        if (!accountId.equals(that.accountId)) {
            return false;
        }
        if (!balance.equals(that.balance)) {
            return false;
        }
        return changeDatetime.equals(that.changeDatetime);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + accountId.hashCode();
        result = 31 * result + balance.hashCode();
        result = 31 * result + changeDatetime.hashCode();
        return result;
    }
}
