package com.bank.serviceMethod.implMethod;

import com.bank.dtoData.ProjectDatadto;
import com.bank.dtoData.TaskDatadto;
import com.bank.dtoData.UserDatadto;
import com.bank.entityTable.UserTable;
import com.bank.mapper.UserMapper;
import com.bank.repository.UserRepository;
import com.bank.serviceMethod.ProjectServiceMethod;
import com.bank.serviceMethod.TaskServiceMethod;
import com.bank.serviceMethod.UserServiceMethod;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceMethodimpl implements UserServiceMethod {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectServiceMethod projectServiceMethod;

    private final TaskServiceMethod taskServiceMethod;
    public UserServiceMethodimpl(UserRepository userRepository, UserMapper userMapper, @Lazy  ProjectServiceMethod projectServiceMethod, @Lazy  TaskServiceMethod taskServiceMethod) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectServiceMethod = projectServiceMethod;
        this.taskServiceMethod = taskServiceMethod;
    }


    @Override
    public UserDatadto findByUserName(String username) {
        UserTable user = userRepository.findByUserNameAndIsDeleted(username, false);
        return userMapper.convertToDto(user);
    }

    @Override
    public List<UserDatadto> listAllUsers() {
        List<UserTable> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void save(UserDatadto user) {

        user.setEnabled(true);

        UserTable obj = userMapper.convertToEntity(user);

        userRepository.save(obj);

    }

//    @Override
//    public void deleteByUserName(String username) {
//
//        userRepository.deleteByUserName(username);
//    }

    @Override
    public UserDatadto update(UserDatadto user) {

        //Find current user
        UserTable user1 = userRepository.findByUserNameAndIsDeleted(user.getUserName(), false);  //has id
        //Map update user dto to entity object
        UserTable convertedUser = userMapper.convertToEntity(user);   // has id?
        //set id to the converted object
        convertedUser.setId(user1.getId());
        //save the updated user in the db
        userRepository.save(convertedUser);

        return findByUserName(user.getUserName());

    }

    @Override
    public void delete(String username) {

        UserTable user = userRepository.findByUserNameAndIsDeleted(username, false);

        if (checkIfUserCanBeDeleted(user)) {
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());  // harold@manager.com-2
            userRepository.save(user);
        }

    }

    @Override
    public List<UserDatadto> listAllByRole(String role) {
        List<UserTable> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role, false);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    private boolean checkIfUserCanBeDeleted(UserTable user) {

        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDatadto> projectDTOList = projectServiceMethod.listAllNonCompletedByAssignedManager(userMapper.convertToDto(user));
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDatadto> taskDTOList = taskServiceMethod.listAllNonCompletedByAssignedEmployee(userMapper.convertToDto(user));
                return taskDTOList.size() == 0;
            default:
                return true;
        }

    }

}

