package com.project.donate.core.controllers;

import com.project.donate.core.service.project.ProjectsService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    private ProjectsService projectsService;

    public ProjectController(ProjectsService projectsService){

        Assert.notNull(projectsService, "ProjectsService should not be null");
        this.projectsService = projectsService;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity register(@RequestParam(name = "projectFile") MultipartFile projectFile,
                                   @RequestParam(name = "summary") String summary,
                                   @RequestParam(name = "name") String name ) {

        projectsService.registerProject(projectFile, summary, name);

        return null;
    }
}
