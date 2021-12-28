package com.health.my_heart.domain.model;

public class Relative {
    private final String fio;
    private final String phone;

    public Relative(String fio, String phone) {
        this.fio = fio;
        this.phone = phone;
    }

    public String getFio() {
        return fio;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isEmpty() {
        return fio.isEmpty() && phone.isEmpty();
    }
}