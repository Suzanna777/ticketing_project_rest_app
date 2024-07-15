package com.bank.serviceMethod.implMethod;


import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.TaskDatadto;
import com.bank.dtoData.UserDatadto;
import com.bank.entityTable.ProjectTable;
import com.bank.entityTable.TaskTable;
import com.bank.enums.Status;
import com.bank.mapper.ProjectMapper;
import com.bank.mapper.TaskMapper;
import com.bank.mapper.UserMapper;
import com.bank.repository.TaskRepository;
import com.bank.serviceMethod.TaskServiceMethod;
import com.bank.serviceMethod.UserServiceMethod;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TaskServiceMethodimpl implements TaskServiceMethod {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectMapper projectMapper;
    private final UserServiceMethod userServiceMethod;
    private final UserMapper userMapper;

    public TaskServiceMethodimpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectMapper projectMapper, UserServiceMethod userServiceMethod, UserMapper userMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userServiceMethod = userServiceMethod;
        this.userMapper = userMapper;
    }


    @Override
    public TaskDatadto findById(Long id) {

        Optional<TaskTable> task = taskRepository.findById(id);

        if(task.isPresent()){
            return taskMapper.convertToDto(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDatadto> listAllTasks() {
        return taskRepository.findAll().stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDatadto dto) {

        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        TaskTable task = taskMapper.convertToEntity(dto);
        taskRepository.save(task);

    }

    @Override
    public void update(TaskDatadto dto) {

        Optional<TaskTable> task = taskRepository.findById(dto.getId());
        TaskTable convertedTask  = taskMapper.convertToEntity(dto);

        if(task.isPresent()){
            convertedTask.setTaskStatus(dto.getTaskStatus() == null ? task.get().getTaskStatus() : dto.getTaskStatus());
            convertedTask.setAssignedDate(task.get().getAssignedDate());
            taskRepository.save(convertedTask);
        }

    }

    @Override
    public void delete(Long id) {

        Optional<TaskTable> foundTask = taskRepository.findById(id);

        if(foundTask.isPresent()){
            foundTask.get().setIsDeleted(true);
            taskRepository.save(foundTask.get());
        }

    }

    @Override
    public int totalNonCompletedTask(String projectCode) {
        return taskRepository.totalNonCompletedTasks(projectCode);
    }

    @Override
    public int totalCompletedTask(String projectCode) {
        return taskRepository.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDatadto projectDTO) {
        ProjectTable project = projectMapper.convertToEntity(projectDTO);
        List<TaskTable> tasks = taskRepository.findAllByProject(project);
        tasks.forEach(task -> delete(task.getId()));
    }

    @Override
    public void completeByProject(ProjectDatadto projectDTO) {
        ProjectTable project = projectMapper.convertToEntity(projectDTO);
        List<TaskTable> tasks = taskRepository.findAllByProject(project);
        tasks.stream().map(taskMapper::convertToDto).forEach(taskDTO -> {
            taskDTO.setTaskStatus(Status.COMPLETE);
            update(taskDTO);
        });
    }

    @Override
    public List<TaskDatadto> listAllTasksByStatusIsNot(Status status) {

        UserDatadto loggedInUser = userServiceMethod.findByUserName("john@employee.com");

        List<TaskTable> tasks = taskRepository.
                findAllByTaskStatusIsNotAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDatadto> listAllTasksByStatus(Status status) {

        UserDatadto loggedInUser = userServiceMethod.findByUserName("john@employee.com");

        List<TaskTable> tasks = taskRepository.
                findAllByTaskStatusAndAssignedEmployee(status, userMapper.convertToEntity(loggedInUser));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TaskDatadto> listAllNonCompletedByAssignedEmployee(UserDatadto assignedEmployee) {
        List<TaskTable> tasks = taskRepository
                .findAllByTaskStatusIsNotAndAssignedEmployee(Status.COMPLETE, userMapper.convertToEntity(assignedEmployee));
        return tasks.stream().map(taskMapper::convertToDto).collect(Collectors.toList());
    }

}





















