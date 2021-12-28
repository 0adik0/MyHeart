package com.health.my_heart.domain.model;

public class Diseases {

    Boolean chronicIschemicHeartDisease;
    Boolean chronicIschemicHeartDiseaseUnspecified;
    Boolean otherFormsChronicIschemicHeartDisease;
    Boolean myocardialInfarction;
    Boolean atheroscleroticHeartDisease;
    Boolean ischemicCardiomyopathy;
    String other;

    public Diseases(Boolean chronicIschemicHeartDisease, Boolean chronicIschemicHeartDiseaseUnspecified, Boolean otherFormsChronicIschemicHeartDisease, Boolean myocardialInfarction, Boolean atheroscleroticHeartDisease, Boolean ischemicCardiomyopathy) {
        this.chronicIschemicHeartDisease = chronicIschemicHeartDisease;
        this.chronicIschemicHeartDiseaseUnspecified = chronicIschemicHeartDiseaseUnspecified;
        this.otherFormsChronicIschemicHeartDisease = otherFormsChronicIschemicHeartDisease;
        this.myocardialInfarction = myocardialInfarction;
        this.atheroscleroticHeartDisease = atheroscleroticHeartDisease;
        this.ischemicCardiomyopathy = ischemicCardiomyopathy;
        this.other = "";
    }

    public Boolean getChronicIschemicHeartDisease() {
        return chronicIschemicHeartDisease;
    }

    public Boolean getChronicIschemicHeartDiseaseUnspecified() {
        return chronicIschemicHeartDiseaseUnspecified;
    }

    public Boolean getOtherFormsChronicIschemicHeartDisease() {
        return otherFormsChronicIschemicHeartDisease;
    }

    public Boolean getMyocardialInfarction() {
        return myocardialInfarction;
    }

    public Boolean getAtheroscleroticHeartDisease() {
        return atheroscleroticHeartDisease;
    }

    public Boolean getIschemicCardiomyopathy() {
        return ischemicCardiomyopathy;
    }

    public String getOther() {
        return other;
    }

    public void setOtherFormsChronicIschemicHeartDisease(Boolean otherFormsChronicIschemicHeartDisease) {
        this.otherFormsChronicIschemicHeartDisease = otherFormsChronicIschemicHeartDisease;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
