package com.bank.serviceMethod.implMethod;

import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.UserDatadto;
import com.bank.entityTable.ProjectTable;
import com.bank.entityTable.UserTable;
import com.bank.enums.Status;
import com.bank.mapper.ProjectMapper;
import com.bank.mapper.UserMapper;
import com.bank.repository.ProjectRepository;
import com.bank.serviceMethod.ProjectServiceMethod;
import com.bank.serviceMethod.TaskServiceMethod;
import com.bank.serviceMethod.UserServiceMethod;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProjectServiceMethodimpl implements ProjectServiceMethod {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserServiceMethod userServiceMethod;
    private final UserMapper userMapper;
    private final TaskServiceMethod taskServiceMethod;

    public ProjectServiceMethodimpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserServiceMethod userServiceMethod, UserMapper userMapper, TaskServiceMethod taskServiceMethod) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userServiceMethod = userServiceMethod;
        this.userMapper = userMapper;
        this.taskServiceMethod = taskServiceMethod;
    }


    @Override
    public ProjectDatadto getByProjectCode(String code) {
        ProjectTable project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }

    @Override
    public List<ProjectDatadto> listAllProjects() {

        List<ProjectTable> list = projectRepository.findAll(Sort.by("projectCode"));

        return list.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDatadto dto) {

        dto.setProjectStatus(Status.OPEN);
        ProjectTable project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDatadto dto) {

        ProjectTable project = projectRepository.findByProjectCode(dto.getProjectCode());

        ProjectTable convertedProject = projectMapper.convertToEntity(dto);

        convertedProject.setId(project.getId());

        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);


    }

    @Override
    public void delete(String code) {
        ProjectTable project = projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);

        project.setProjectCode(project.getProjectCode() + "-" + project.getId());  // SP03-4

        projectRepository.save(project);

        taskServiceMethod.deleteByProject(projectMapper.convertToDto(project));

    }

    @Override
    public void complete(String code) {
        ProjectTable project = projectRepository.findByProjectCode(code);
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);

        taskServiceMethod.completeByProject(projectMapper.convertToDto(project));
    }

    @Override
    public List<ProjectDatadto> listAllProjectDetails() {

        UserDatadto currentUserDTO = userServiceMethod.findByUserName("harold@manager.com");

        UserTable user = userMapper.convertToEntity(currentUserDTO);

        List<ProjectTable> list = projectRepository.findAllByAssignedManager(user);


        return list.stream().map(project -> {

                    ProjectDatadto obj = projectMapper.convertToDto(project);

                    obj.setUnfinishedTaskCounts(taskServiceMethod.totalNonCompletedTask(project.getProjectCode()));
                    obj.setCompleteTaskCounts(taskServiceMethod.totalCompletedTask(project.getProjectCode()));

                    return obj;
                }

        ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDatadto> listAllNonCompletedByAssignedManager(UserDatadto assignedManager) {
        List<ProjectTable> projects = projectRepository
                .findAllByProjectStatusIsNotAndAssignedManager(Status.COMPLETE, userMapper.convertToEntity(assignedManager));
        return projects.stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

}

