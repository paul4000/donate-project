package com.project.donate.core.controllers;

import com.project.donate.core.model.dtos.DonationTO;
import com.project.donate.core.model.dtos.ExecutorsDTO;
import com.project.donate.core.model.response.DonateRS;
import com.project.donate.core.model.response.OpenValidationRS;
import com.project.donate.core.model.response.VotingRS;
import com.project.donate.core.service.project.DonationProjectService;
import com.project.donate.core.service.project.HandlingProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/donate")
public class DonationController {

    private DonationProjectService donationProjectService;
    private HandlingProjectService handlingProjectService;

    @Autowired
    public DonationController(DonationProjectService donationProjectService, HandlingProjectService handlingProjectService) {

        Assert.notNull(donationProjectService, "DonationProjectService should not be null");
        Assert.notNull(handlingProjectService, "HandlingProjectService should not be null");

        this.handlingProjectService = handlingProjectService;
        this.donationProjectService = donationProjectService;
    }


    @PostMapping(path = "/{id}")
    public ResponseEntity donateProject(@PathVariable long id, @RequestBody DonationTO donation) {

        donationProjectService.donateProject(donation.getPassToWallet(), id, donation.getAmountOfDonation());

        DonateRS donateRS = new DonateRS();
        donateRS.setProjectId(id);

        return ResponseEntity.ok(donateRS);
    }

    @PostMapping(path = "/executors/{projectId}")
    public ResponseEntity addExecutorsToProject(@PathVariable long projectId, @RequestParam String walletPass, @RequestBody ExecutorsDTO executorsDTO) {

        executorsDTO.getChosenExecutors()
                .forEach(e -> handlingProjectService.addExecutors(e.getAddress(), projectId, e.getAmount(), walletPass));

        handlingProjectService.openValidationPhase(walletPass, projectId);


        OpenValidationRS openValidationRS = new OpenValidationRS();
        openValidationRS.setProjectId(projectId);

        return ResponseEntity.ok(openValidationRS);
    }

    @PostMapping(path ="/vote/{projectId}")
    public ResponseEntity voteForExecution(@PathVariable long projectId, @RequestParam int value, @RequestParam String walletPass) {
        donationProjectService.voteForExecution(projectId, value, walletPass);

        VotingRS votingRS = new VotingRS();
        votingRS.setId(value);

        return ResponseEntity.ok(votingRS);
    }

}
