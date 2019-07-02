package com.project.donate.core.service.project;

import com.google.common.collect.Lists;
import com.project.donate.core.Web3jServiceSupplier;
import com.project.donate.core.auth.SecurityService;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.exceptions.ProjectRetrievalException;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.User;
import com.project.donate.core.repositories.ProjectRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectsService {

    private ProjectRepository projectRepository;
    private Web3jServiceSupplier web3jServiceSupplier;
    private UserService userService;
    private SecurityService securityService;

    private final Logger logger = Logger.getLogger(ProjectsService.class);

    @Autowired
    public ProjectsService(ProjectRepository projectRepository, Web3jServiceSupplier web3jServiceSupplier,
                           UserService userService, SecurityService securityService) {

        Assert.notNull(projectRepository, "ProjectRepository should not be null");
        Assert.notNull(web3jServiceSupplier, "Web3jServiceSupplier should not be null");
        Assert.notNull(userService, "UserService should not be null");
        Assert.notNull(securityService, "SecurityService should not be null");

        this.securityService = securityService;
        this.userService = userService;
        this.web3jServiceSupplier = web3jServiceSupplier;
        this.projectRepository = projectRepository;
    }

    public Optional<Project> registerProjectInDatabase(MultipartFile projectFile, String summary, String name) {

        Optional<Project> savedProject = Optional.empty();

        try {

            Project project = new Project();
            project.setName(name);
            project.setDataType(projectFile.getContentType());
            project.setSummary(summary);
            project.setData(projectFile.getBytes());

            String loggedInUsername = securityService.findLoggedInUsername();

            savedProject = Optional.of(projectRepository.save(project));

            savedProject.ifPresent(project1 -> userService.addProjectToUser(loggedInUsername, project1));

        } catch (IOException ex) {
            logger.warn("Can't extract bytes from project file.");
        }

        return savedProject;
    }

    public Project getProject(long id) {

        return projectRepository.findById(id)
                .orElseThrow(ProjectRetrievalException::new);
    }


    public List<Project> getAllProjects() {
        return Lists.newArrayList(projectRepository.findAll());
    }

    public List<Project> getUserProject(String username) {

        User userFromDatabase = userService.getUserFromDatabase(username);

        Set<Project> projects = userFromDatabase.getProjects();

        return Lists.newArrayList(projects);
    }

    public void changeValidationPhase(long projectId, boolean value) {
        Project project = getProject(projectId);
        project.setValidationPhase(value);
        projectRepository.save(project);
    }

    public void changeOpenedStatus(long projectId, boolean value) {
        Project project = getProject(projectId);
        project.setOpened(value);
        projectRepository.save(project);
    }

    public void changeExecutionStatus(long projectId, boolean value) {
        Project project = getProject(projectId);
        project.setExecutedWithSuccess(value);
        projectRepository.save(project);
    }

    public void setCloseDate(long projectId) {
        Project project = getProject(projectId);
        project.setExecutionDate(new Date());
        projectRepository.save(project);
    }
}
