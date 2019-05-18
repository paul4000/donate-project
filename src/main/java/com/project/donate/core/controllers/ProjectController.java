package com.project.donate.core.controllers;

import com.project.donate.core.model.Project;
import com.project.donate.core.service.project.ProjectsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    private ProjectsService projectsService;

    public ProjectController(ProjectsService projectsService){

        Assert.notNull(projectsService, "ProjectsService should not be null");
        this.projectsService = projectsService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity register(@RequestParam MultipartFile multipartFile, @RequestParam String name, @RequestParam String summary) {

        Optional<Project> project = projectsService.registerProjectInDatabase(multipartFile,
                summary, name);

        if (project.isEmpty()) return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/register")
    public ResponseEntity registerInBlockchain(@RequestParam String passwordToWallet, @RequestParam long projectId) {

        projectsService.registerInBlockchain(passwordToWallet, projectId);

        return ResponseEntity.ok().build();

    }

    @GetMapping(path = "/open")
    public ResponseEntity openProject(@RequestParam String passwordToWallet, @RequestParam long projectId) {
        projectsService.openProject(passwordToWallet, projectId);
        return null;
    }
}
