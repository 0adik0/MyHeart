package com.health.my_heart.domain.model;

public enum DayOfAWeek {
    MONDAY(2),
    TUESDAY(3),
    WEDNESDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7),
    SUNDAY(1);
    private final int key;

    DayOfAWeek(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
