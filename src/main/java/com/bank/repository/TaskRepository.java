package com.bank.repository;

import com.bank.entityTable.ProjectTable;
import com.bank.entityTable.TaskTable;
import com.bank.entityTable.UserTable;
import com.bank.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskTable,Long>{



    @Query("SELECT COUNT(t) FROM TaskTable t WHERE t.project.projectCode = ?1 AND t.taskStatus <> 'COMPLETE'")
    int totalNonCompletedTasks(String projectCode);

    @Query(value = "SELECT COUNT(*) " +
            "FROM tasks t JOIN projects p on t.project_id=p.id " +
            "WHERE p.project_code=?1 AND t.task_status='COMPLETE'", nativeQuery = true)
    int totalCompletedTasks(String projectCode);

    List<TaskTable> findAllByProject(ProjectTable project);

    List<TaskTable> findAllByTaskStatusIsNotAndAssignedEmployee(Status status, UserTable user);

    List<TaskTable> findAllByTaskStatusAndAssignedEmployee(Status status, UserTable user);

}
