package com.project.donate.core.helpers;

import com.project.donate.core.model.Project;
import com.project.donate.core.model.response.ProjectRS;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ResponsesMappers {
    public static ResponseEntity mapProjects(List<Project> allProjects) {

        List<ProjectRS> projectsRS = allProjects.stream()
                .map(p -> {
                    ProjectRS projectRS = new ProjectRS();
                    projectRS.setId(p.getId());
                    projectRS.setName(p.getName());
                    projectRS.setSummary(p.getSummary());
                    projectRS.setAddress(p.getAddress());
                    projectRS.setOpened(p.isOpened());

                    if (p.getAddress() != null) {
                        projectRS.setValidationPhase(p.isValidationPhase());
                        projectRS.setIfProjectSuccessful(p.getExecutedWithSuccess());
                    }

                    return projectRS;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(projectsRS);
    }
}
