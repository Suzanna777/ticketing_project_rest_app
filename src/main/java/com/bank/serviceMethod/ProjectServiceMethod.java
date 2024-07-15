package com.bank.serviceMethod;

import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.UserDatadto;

import java.util.List;

public interface ProjectServiceMethod {

    ProjectDatadto getByProjectCode(String code);
    List<ProjectDatadto> listAllProjects();
    void save(ProjectDatadto dto);
    void update(ProjectDatadto dto);
    void delete(String code);
    void complete(String code);
    List<ProjectDatadto> listAllProjectDetails();

    List<ProjectDatadto> listAllNonCompletedByAssignedManager(UserDatadto assignedManager);

}

