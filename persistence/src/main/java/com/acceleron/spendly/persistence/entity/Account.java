package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.Account.TABLE_NAME;

@Table(name = TABLE_NAME)
@Entity
@Getter
@Setter
@ToString
public class Account {

    public static final String TABLE_NAME = "ACCOUNTS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User user;
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;

    @Formula("(SELECT COALESCE((SELECT AH.AMOUNT FROM " + AccountHistory.TABLE_NAME + " AH" +
            " INNER JOIN " + TABLE_NAME + " A ON AH.ACCOUNT_ID = A.ID WHERE AH.ACCOUNT_ID = id" +
            " ORDER BY AH.CHANGE_DATETIME DESC LIMIT 1), 0))")
    private BigDecimal amount = BigDecimal.ZERO;
    @Column(name = "CURRENCY", nullable = false)
    @NonNull
    private String currency;
    @Column(name = "COLOR")
    private String color;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "account")
    @ToString.Exclude
    private List<AccountHistory> accountHistories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "account")
    @ToString.Exclude
    private List<Record> records;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account account)) {
            return false;
        }
        if (id != account.id) {
            return false;
        }
        if (userId != account.userId) {
            return false;
        }
        if (!Objects.equals(name, account.name)) {
            return false;
        }
        if (!type.equals(account.type)) {
            return false;
        }
        if (!amount.equals(account.amount)) {
            return false;
        }
        if (!currency.equals(account.currency)) {
            return false;
        }
        return Objects.equals(color, account.color);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + type.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
