package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.UserGroup.TABLE_NAME;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = TABLE_NAME)
public class UserGroup {

    public static final String TABLE_NAME = "GROUPS";
    public static final String USERS_GROUPS_TABLE = "USERS_GROUPS";

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COLOR")
    private String color;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = USERS_GROUPS_TABLE,
            joinColumns = @JoinColumn(name = "GROUP_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID")
    )
    @ToString.Exclude
    private Set<User> participants;
}
