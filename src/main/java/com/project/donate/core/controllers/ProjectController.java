package com.project.donate.core.controllers;

import com.project.donate.core.auth.SecurityServiceImpl;
import com.project.donate.core.auth.UserService;
import com.project.donate.core.helpers.ResponsesMappers;
import com.project.donate.core.model.Project;
import com.project.donate.core.model.User;
import com.project.donate.core.model.dtos.OpenProjectRQ;
import com.project.donate.core.model.response.ExecutorRS;
import com.project.donate.core.model.response.OpenedProjectRS;
import com.project.donate.core.model.response.ProjectExecutionRS;
import com.project.donate.core.model.response.ProjectRS;
import com.project.donate.core.service.project.HandlingProjectService;
import com.project.donate.core.service.project.ProjectInfoService;
import com.project.donate.core.service.project.ProjectsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/project")
public class ProjectController {

    private ProjectsService projectsService;
    private SecurityServiceImpl securityService;
    private HandlingProjectService handlingProjectService;
    private ProjectInfoService projectInfoService;
    private UserService userService;

    public ProjectController(ProjectsService projectsService, SecurityServiceImpl securityService, HandlingProjectService handlingProjectService,
                             ProjectInfoService projectInfoService, UserService userService) {

        Assert.notNull(projectsService, "ProjectsService should not be null");
        Assert.notNull(securityService, "SecurityServiceImpl should not be null");
        Assert.notNull(securityService, "HandlingProjectService should not be null");
        Assert.notNull(projectInfoService, "ProjectInfoService should not be null");
        Assert.notNull(userService, "UserService should not be null");
        this.projectsService = projectsService;
        this.securityService = securityService;
        this.handlingProjectService = handlingProjectService;
        this.projectInfoService = projectInfoService;
        this.userService = userService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity register(@RequestParam MultipartFile multipartFile, @RequestParam String name, @RequestParam String summary) {

        Optional<Project> project = projectsService.registerProjectInDatabase(multipartFile,
                summary, name);

        if (project.isEmpty()) return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity registerInBlockchain(@RequestParam String passwordToWallet, @RequestParam long projectId) {

        handlingProjectService.registerInBlockchain(passwordToWallet, projectId);

        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/open")
    public ResponseEntity openProject(@RequestBody OpenProjectRQ projectRQ) {
        String openProjectAddress = handlingProjectService.openProject(projectRQ.getPasswordToWallet(), projectRQ.getProjectId(), projectRQ.getAmount());

        handlingProjectService.registerInBlockchain(projectRQ.getPasswordToWallet(), projectRQ.getProjectId());

        OpenedProjectRS projectRS = new OpenedProjectRS();
        projectRS.setProjectAddress(openProjectAddress);

        return ResponseEntity.ok(projectRS);
    }

    @PostMapping(path = "/close/{projectId}")
    public ResponseEntity closeProject(@PathVariable long projectId, @RequestParam String walletPass) {

        boolean result = handlingProjectService.executeAndCloseProject(walletPass, projectId);

        ProjectExecutionRS projectExecutionRS = new ProjectExecutionRS();
        projectExecutionRS.setResult(result);

        return ResponseEntity.ok(projectExecutionRS);
    }

    @GetMapping(path = "/all")
    public ResponseEntity getAllProjects() {

        List<Project> allProjects = projectsService.getAllProjects();

        return ResponsesMappers.mapProjects(allProjects);
    }

    @GetMapping(path = "/detalis/{id}")
    public ResponseEntity getDetailedProject(@PathVariable long id) {
        Project project = projectsService.getProject(id);

        ProjectRS projectRS = new ProjectRS();
        projectRS.setId(project.getId());
        projectRS.setName(project.getName());
        projectRS.setSummary(project.getSummary());
        projectRS.setOwner(project.getOwner().getUsername());

        if (project.getAddress() != null) {

            projectInfoService.validateIfProjectAddressMatchesContractProjectId(project);

            projectRS.setAddress(project.getAddress());
            projectRS.setOpened(projectInfoService.isProjectOpened(id));
            projectRS.setValidationPhase(projectInfoService.isValidationPhase(id));
            projectRS.setGoalAmount(projectInfoService.getProjectContractGoalAmount(id));
            projectRS.setActualBalance(projectInfoService.getProjectCurrentBalance(id));
            projectRS.setVerified(projectInfoService.getIfProjectVerified(id));
            projectRS.setIfProjectSuccessful(projectInfoService.isExecutedWithSuccess(id));
            if(project.getOpeningDate() != null) {
                projectRS.setOpeningDate(new SimpleDateFormat("dd/MM/yyyy").format(project.getOpeningDate()));
            }

            if(project.getExecutionDate() != null) {
                projectRS.setExecutionDate(new SimpleDateFormat("dd/MM/yyyy").format(project.getExecutionDate()));
            }

        }

        if (project.getAddress() != null && projectInfoService.isValidationPhase(id)) {
            projectRS.setCanUserVote(projectInfoService.canUserVote(id));
            projectRS.setDonatorsNumber(projectInfoService.getDonatorsNumber(id));
            projectRS.setNumberOfVotes(projectInfoService.getNumberOfVotes(id));
            projectRS.setValidationTimeLeft(projectInfoService.getValidationTimeLeft(id));
        }

        return ResponseEntity.ok(projectRS);
    }

    @CrossOrigin(value = {"*"}, exposedHeaders = {"Content-Disposition"})
    @GetMapping(path = "/download/details/{id}")
    public ResponseEntity<byte[]> downloadProjectPdf(@PathVariable long id) {

        Project project = projectsService.getProject(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(project.getDataType()));
        headers.setContentLength(project.getData().length);
        headers.set("Content-Disposition", "attachment; filename=" + id + "_project_details.pdf");

        return new ResponseEntity<>(project.getData(), headers, HttpStatus.OK);
    }

    @GetMapping(path = "/my")
    public ResponseEntity getMyProjects() {

        String loggedInUsername = securityService.findLoggedInUsername();

        List<Project> allProjects = projectsService.getUserProject(loggedInUsername);

        return ResponsesMappers.mapProjects(allProjects);
    }

    @PostMapping(path = "/executors/{projectId}")
    public ResponseEntity getExecutorsForProject(@PathVariable long projectId) {

        List<String> projectExecutors = projectInfoService.getProjectExecutors(projectId);

        List<ExecutorRS> executorRSList = projectExecutors.stream()
                .map(this::mapExecutor)
                .collect(Collectors.toList());

        return ResponseEntity.ok(executorRSList);
    }

    private ExecutorRS mapExecutor(String executorAddress) {

        User userFromDatabase = userService.getByAddress(executorAddress);

        ExecutorRS executorRS = new ExecutorRS();

        if (userFromDatabase != null) {
            executorRS.setName(userFromDatabase.getName());
            executorRS.setAddress(userFromDatabase.getAccount());
        }
        return executorRS;
    }

}
