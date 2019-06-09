package com.project.donate.core.controllers;

import com.project.donate.core.model.dtos.DonationTO;
import com.project.donate.core.model.response.DonateRS;
import com.project.donate.core.service.project.DonationProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/donate")
public class DonationController {

    private DonationProjectService donationProjectService;

    @Autowired
    public DonationController(DonationProjectService donationProjectService) {

        Assert.notNull(donationProjectService, "DonationProjectService should not be null");

        this.donationProjectService = donationProjectService;
    }


    @PostMapping(path ="/{id}")
    public ResponseEntity donateProject(@PathVariable long id, @RequestBody DonationTO donation) {

        donationProjectService.donateProject(donation.getPassToWallet(), id, donation.getAmountOfDonation());

        DonateRS donateRS = new DonateRS();
        donateRS.setProjectId(id);

        return ResponseEntity.ok(donateRS);
    }
}
