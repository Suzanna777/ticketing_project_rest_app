package com.bank.serviceMethod;

import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.TaskDatadto;
import com.bank.dtoData.UserDatadto;
import com.bank.enums.Status;

import java.util.List;

public interface TaskServiceMethod {


    TaskDatadto findById(Long id);
    List<TaskDatadto> listAllTasks();

    void save(TaskDatadto dto);
    void update(TaskDatadto dto);
    void delete(Long id);

    int totalNonCompletedTask(String projectCode);
    int totalCompletedTask(String projectCode);

    void deleteByProject(ProjectDatadto projectDTO);

    void completeByProject(ProjectDatadto projectDTO);

    List<TaskDatadto> listAllTasksByStatusIsNot(Status status);
    List<TaskDatadto> listAllTasksByStatus(Status status);

    List<TaskDatadto> listAllNonCompletedByAssignedEmployee(UserDatadto assignedEmployee);

}
