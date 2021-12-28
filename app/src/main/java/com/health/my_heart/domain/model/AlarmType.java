package com.health.my_heart.domain.model;

public enum AlarmType {
    ALL_DAY("Весь день"),
    MORNING("Утром"),
    AFTERNOON("Днём"),
    EVENING("Вечером");
    private final String name;

    AlarmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
