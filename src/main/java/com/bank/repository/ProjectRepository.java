package com.bank.repository;

import com.bank.entityTable.ProjectTable;
import com.bank.entityTable.UserTable;
import com.bank.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository  extends JpaRepository<ProjectTable,Long> {

    ProjectTable findByProjectCode(String code);
    List<ProjectTable> findAllByAssignedManager(UserTable manager);
    List<ProjectTable> findAllByProjectStatusIsNotAndAssignedManager(Status status, UserTable assignedManager);

}

