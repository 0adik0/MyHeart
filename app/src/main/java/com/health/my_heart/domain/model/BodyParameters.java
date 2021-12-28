package com.health.my_heart.domain.model;

public class BodyParameters {

    String date;
    String growth;
    String weight;

    public BodyParameters() {
    }

    public BodyParameters(String date, String growth, String weight){
        this.date = date;
        this.growth = growth;
        this.weight = weight;
    }

    public String getDate() {
        return date;
    }

    public String getGrowth() {
        return growth;
    }

    public String getWeight() {
        return weight;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setGrowth(String growth) {
        this.growth = growth;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
