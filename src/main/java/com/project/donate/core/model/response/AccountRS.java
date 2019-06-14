package com.project.donate.core.model.response;

public class AccountRS {

    private String name;
    private String username;
    private String account;
    private double accountBalance;
    private String email;
    private int numberOfProject;
    private long numberOfSuccessfulProjects;
    private long numberOfFailedProjects;

    public long getNumberOfSuccessfulProjects() {
        return numberOfSuccessfulProjects;
    }

    public void setNumberOfSuccessfulProjects(long numberOfSuccessfulProjects) {
        this.numberOfSuccessfulProjects = numberOfSuccessfulProjects;
    }

    public int getNumberOfProject() {
        return numberOfProject;
    }

    public void setNumberOfProject(int numberOfProject) {
        this.numberOfProject = numberOfProject;
    }

    public long getNumberOfFailedProjects() {
        return numberOfFailedProjects;
    }

    public void setNumberOfFailedProjects(long numberOfFailedProjects) {
        this.numberOfFailedProjects = numberOfFailedProjects;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
