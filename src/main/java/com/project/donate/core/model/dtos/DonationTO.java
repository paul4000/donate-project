package com.project.donate.core.model.dtos;

public class DonationTO {

    private double amountOfDonation;
    private String passToWallet;


    public double getAmountOfDonation() {
        return amountOfDonation;
    }

    public void setAmountOfDonation(double amountOfDonation) {
        this.amountOfDonation = amountOfDonation;
    }

    public String getPassToWallet() {
        return passToWallet;
    }

    public void setPassToWallet(String passToWallet) {
        this.passToWallet = passToWallet;
    }
}
