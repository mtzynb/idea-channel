package com.rahand.idea.channel.enums;

public enum Bank {
    SAMAN("SABCIR"),
    MIDDLE_EAST("KHMIIR"),
    TOURISM("TOURISM"),
    BOOMIR("BOOMIR");

    private String bankName;

    Bank(String bankName) {
        this.bankName = bankName;
    }

    public String value() {
        return name();
    }

    public String getBankName() {
        return bankName;
    }
}
