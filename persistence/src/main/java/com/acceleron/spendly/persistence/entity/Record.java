package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.Record.TABLE_NAME;

@Table(name = TABLE_NAME)
@Entity
@Setter
@Getter
@ToString
public class Record {

    public static final String TABLE_NAME = "RECORDS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "TARGET_AMOUNT")
    private BigDecimal targetAmount;
    @Column(name = "TARGET_CURRENCY")
    private String targetCurrency;

    @Column(name = "NOTE", columnDefinition = "TEXT", length = 65535)
    private String note;
    @Column(name = "PAYEE")
    private String payee;
    @Column(name = "TYPE")
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", insertable = false, updatable = false, nullable = false)
    private Category category;
    @Column(name = "CATEGORY_ID")
    private UUID categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private Account account;
    @Column(name = "ACCOUNT_ID")
    private UUID accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TARGET_ACCOUNT_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ToString.Exclude
    private Account targetAccount;
    @Column(name = "TARGET_ACCOUNT_ID")
    private UUID targetAccountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User user;
    @NonNull
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "CREATION_DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationDatetime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "recordEntity")
    @ToString.Exclude
    private List<AccountHistory> accountHistories;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Record recordEntity)) {
            return false;
        }

        if (!Objects.equals(id, recordEntity.id)) {
            return false;
        }
        if (!amount.equals(recordEntity.amount)) {
            return false;
        }
        if (!currency.equals(recordEntity.currency)) {
            return false;
        }
        if (!Objects.equals(targetAmount, recordEntity.targetAmount)) {
            return false;
        }
        if (!Objects.equals(targetCurrency, recordEntity.targetCurrency)) {
            return false;
        }
        if (!Objects.equals(note, recordEntity.note)) {
            return false;
        }
        if (!Objects.equals(payee, recordEntity.payee)) {
            return false;
        }
        if (!Objects.equals(type, recordEntity.type)) {
            return false;
        }
        if (!category.equals(recordEntity.category)) {
            return false;
        }
        return creationDatetime.equals(recordEntity.creationDatetime);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + amount.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + (targetAmount != null ? targetAmount.hashCode() : 0);
        result = 31 * result + (targetCurrency != null ? targetCurrency.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (payee != null ? payee.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + category.hashCode();
        result = 31 * result + creationDatetime.hashCode();
        return result;
    }
}
