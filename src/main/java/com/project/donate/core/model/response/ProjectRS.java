package com.project.donate.core.model.response;

public class ProjectRS {

    private long id;
    private String name;
    private String summary;
    private String address;
    private String owner;
    private Boolean isOpened;
    private Boolean isValidationPhase;
    private Boolean ifProjectSuccessful;
    private double goalAmount;
    private double actualBalance;
    private Boolean canUserVote;
    private int donatorsNumber;
    private int numberOfVotes;
    private Boolean verified;
    private int validationTimeLeft;
    private String openingDate;
    private String executionDate;

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

    public Boolean getCanUserVote() {
        return canUserVote;
    }

    public void setCanUserVote(Boolean canUserVote) {
        this.canUserVote = canUserVote;
    }

    public int getDonatorsNumber() {
        return donatorsNumber;
    }

    public void setDonatorsNumber(int donatorsNumber) {
        this.donatorsNumber = donatorsNumber;
    }

    public int getNumberOfVotes() {
        return numberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        this.numberOfVotes = numberOfVotes;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getValidationTimeLeft() {
        return validationTimeLeft;
    }

    public void setValidationTimeLeft(int validationTimeLeft) {
        this.validationTimeLeft = validationTimeLeft;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(String executionDate) {
        this.executionDate = executionDate;
    }
}
