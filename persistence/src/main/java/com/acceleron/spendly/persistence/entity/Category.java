package com.acceleron.spendly.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.acceleron.spendly.persistence.entity.Category.TABLE_NAME;
import static java.util.Collections.emptyList;

@Table(name = TABLE_NAME)
@Entity
@Setter
@Getter
@ToString
public class Category {

    public static final String TABLE_NAME = "CATEGORIES";
    public static final int MAX_CATEGORY_LEVEL = 2;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "ICON")
    private String icon;
    @Column(name = "COLOR")
    private String color;
    @Column(name = "COMMON")
    private boolean isCommon;
    @Column(name = "CATEGORY_LEVEL")
    private int categoryLevel;
    @Column(name = "PARENT_CATEGORY_ID", insertable=false, updatable=false)
    private String parentCategoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", referencedColumnName = "ID", insertable = false, updatable = false, nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "USER_ID")
    private UUID userId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="PARENT_CATEGORY_ID")
    private List<Category> childCategories = emptyList();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category category)) {
            return false;
        }

        if (isCommon != category.isCommon) {
            return false;
        }
        if (categoryLevel != category.categoryLevel) {
            return false;
        }
        if (!id.equals(category.id)) {
            return false;
        }
        if (!name.equals(category.name)) {
            return false;
        }
        if (!icon.equals(category.icon)) {
            return false;
        }
        if (!color.equals(category.color)) {
            return false;
        }
        if (!Objects.equals(parentCategoryId, category.parentCategoryId))
        {
            return false;
        }
        if (!Objects.equals(userId, category.userId)) {
            return false;
        }
        return Objects.equals(childCategories, category.childCategories);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + (isCommon ? 1 : 0);
        result = 31 * result + categoryLevel;
        result = 31 * result + (parentCategoryId != null ? parentCategoryId.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (childCategories != null ? childCategories.hashCode() : 0);
        return result;
    }
}
