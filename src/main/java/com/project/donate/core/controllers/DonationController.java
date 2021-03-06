package com.project.donate.core.controllers;

import com.project.donate.core.auth.SecurityServiceImpl;
import com.project.donate.core.helpers.ResponsesMappers;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.dtos.DonationTO;
import com.project.donate.core.model.dtos.ExecutorsDTO;
import com.project.donate.core.model.response.DonateRS;
import com.project.donate.core.model.response.OpenValidationRS;
import com.project.donate.core.model.response.VotingRS;
import com.project.donate.core.service.donation.DonationInfoService;
import com.project.donate.core.service.project.DonationProjectService;
import com.project.donate.core.service.project.HandlingProjectService;
import com.project.donate.core.service.project.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(path = "/donate")
public class DonationController {

    private DonationProjectService donationProjectService;
    private HandlingProjectService handlingProjectService;
    private ProjectsService projectsService;
    private DonationInfoService donationInfoService;
    private SecurityServiceImpl securityService;

    @Autowired
    public DonationController(DonationProjectService donationProjectService, HandlingProjectService handlingProjectService,
                              ProjectsService projectsService,
                              DonationInfoService donationInfoService, SecurityServiceImpl securityService) {

        Assert.notNull(donationProjectService, "DonationProjectService should not be null");
        Assert.notNull(handlingProjectService, "HandlingProjectService should not be null");

        this.securityService = securityService;
        this.projectsService = projectsService;
        this.donationInfoService = donationInfoService;
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

    @GetMapping(path = "/projects")
    public ResponseEntity getDonatedProjects() {

        String loggedInUsername = securityService.findLoggedInUsername();
        List<Long> userDonatedProjects = donationInfoService.getUserDonatedProjects(loggedInUsername);

        List<Project> projectList = userDonatedProjects.stream()
                .map(projectId -> projectsService.getProject(projectId))
                .collect(Collectors.toList());

        return ResponsesMappers.mapProjects(projectList);
    }
}
