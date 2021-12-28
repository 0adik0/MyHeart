package com.health.my_heart.domain.model;

public enum ContentType {
    HEART_DISEASES("\"Болезни сердца\"", "болезнь"), DIETS("Диеты", "диету"), SPORT("Cпорт", "статью"), FOOD("Еда", "");
    private final String name;
    private final String titlePostfix;

    ContentType(String name, String postfix) {
        this.name = name;
        this.titlePostfix = postfix;
    }

    public String getName() {
        return name;
    }

    public String getTitlePostfix() {
        return titlePostfix;
    }
}