package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.User.TABLE_NAME;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    public static final String TABLE_NAME = "USERS";

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "DEFAULT_CURRENCY")
    private String currency;

    @Column(name = "USERNAME")
    private String username;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Account> accounts;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User user)) {
            return false;
        }

        if (!id.equals(user.id)) {
            return false;
        }
        if (!username.equals(user.username)) {
            return false;
        }
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}