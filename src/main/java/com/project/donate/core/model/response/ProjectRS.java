package com.project.donate.core.model.response;

public class ProjectRS {

    private long id;
    private String name;
    private String summary;
    private String address;
    private Boolean isOpened;
    private Boolean isValidationPhase;
    private Boolean ifProjectSuccessful;
    private double goalAmount;
    private double actualBalance;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getOpened() {
        return isOpened;
    }

    public void setOpened(Boolean opened) {
        isOpened = opened;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Boolean getValidationPhase() {
        return isValidationPhase;
    }

    public void setValidationPhase(Boolean validationPhase) {
        isValidationPhase = validationPhase;
    }

    public Boolean getIfProjectSuccessful() {
        return ifProjectSuccessful;
    }

    public void setIfProjectSuccessful(Boolean ifProjectSuccessful) {
        this.ifProjectSuccessful = ifProjectSuccessful;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public double getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(double actualBalance) {
        this.actualBalance = actualBalance;
    }
}
