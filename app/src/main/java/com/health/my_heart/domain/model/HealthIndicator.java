package com.health.my_heart.domain.model;

public class HealthIndicator {
    private String value;
    private String date;

    public HealthIndicator() {
    }

    public HealthIndicator(String value, String date) {
        this.value = value;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }
}
