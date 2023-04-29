package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.Account.TABLE_NAME;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = TABLE_NAME)
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
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "COLOR")
    private String color;

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
