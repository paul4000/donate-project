package com.project.donate.core.repositories;

import com.project.donate.core.models.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository  extends CrudRepository<Project, Long> {
}
