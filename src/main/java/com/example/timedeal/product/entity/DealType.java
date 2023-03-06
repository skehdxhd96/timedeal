package com.example.timedeal.product.entity;

public enum DealType {
    GENERAL, TIMEDEAL;

    public static DealType convert(String type) {

        switch (type) {
            case "GENERAL" :
                return DealType.GENERAL;
            case "TIMEDEAL" :
                return DealType.TIMEDEAL;
            default:
                throw new IllegalArgumentException("There is No Event Type");
        }
    }
}
