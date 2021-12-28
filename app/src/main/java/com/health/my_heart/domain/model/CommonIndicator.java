package com.health.my_heart.domain.model;

public class CommonIndicator {
    private String name;
    private String currentValue;
    private String allValues;

    public CommonIndicator() {
    }

    public CommonIndicator(String name, String currentValue, String allValues) {
        this.name = name;
        this.currentValue = currentValue;
        this.allValues = allValues;
    }

    public String getName() {
        return name;
    }

    public String getCurrentValue() {
        return currentValue;
    }
}
