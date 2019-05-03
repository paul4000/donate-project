package com.project.donate.core.service.project;

import com.project.donate.core.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectsService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectsService(ProjectRepository projectRepository) {

        Assert.notNull(projectRepository, "ProjectRepository should not be null");

        this.projectRepository = projectRepository;

    }

    public void registerProject(MultipartFile projectFile, String summary, String name){

    }

}
