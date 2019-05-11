package com.project.donate.core.models.dtos;

public class UserTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private String passwordConfirmation;
    private String passwordToAccount;
    private String passwordToAccountConfirm;
    private String accountRole;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getPasswordToAccount() {
        return passwordToAccount;
    }

    public void setPasswordToAccount(String passwordToAccount) {
        this.passwordToAccount = passwordToAccount;
    }

    public String getPasswordToAccountConfirm() {
        return passwordToAccountConfirm;
    }

    public void setPasswordToAccountConfirm(String passwordToAccountConfirm) {
        this.passwordToAccountConfirm = passwordToAccountConfirm;
    }

    public String getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(String accountRole) {
        this.accountRole = accountRole;
    }
}
