package com.health.my_heart.domain.model;

public enum QuizResult {
    RED("Красная зона"), YELLOW("Жёлтая зона"), GREEN("Зелёная зона");
    private final String name;

    QuizResult(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
