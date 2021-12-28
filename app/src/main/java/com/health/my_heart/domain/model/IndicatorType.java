package com.health.my_heart.domain.model;

public enum IndicatorType {
    PRESSURE("Давление"),
    PULSE("Пульс"),
    WEIGHT("Вес"),
    SUGAR_BLOOD("Сахар в крови"),
    XE_LEVEL("Уровень ХЕ"),
    CHOLESTEROL("Холестерин"),
    IMT("ИМТ"),
    GROWTH("Рост"),
    TEMPERATURE("Температура"),
    SUGAR_URINE("Сахар в моче");

    private final String name;

    IndicatorType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
