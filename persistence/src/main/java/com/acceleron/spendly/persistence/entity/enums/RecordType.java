package com.acceleron.spendly.persistence.entity.enums;

public enum RecordType {

    INCOME,
    EXPENSE,
    TRANSFER; //TODO Refactor transfer type to be a separate entity as records with this type have different fields

    public boolean isTransfer() {
        return TRANSFER == this;
    }

    public boolean isIncome() {
        return INCOME == this;
    }

    public boolean isExpense() {
        return EXPENSE == this;
    }
}
