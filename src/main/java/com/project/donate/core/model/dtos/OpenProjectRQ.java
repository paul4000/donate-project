package com.project.donate.core.model.dtos;

public class OpenProjectRQ {

    private String passwordToWallet;
    private long projectId;
    private String amount;

    public String getPasswordToWallet() {
        return passwordToWallet;
    }

    public void setPasswordToWallet(String passwordToWallet) {
        this.passwordToWallet = passwordToWallet;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
