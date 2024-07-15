package com.bank.serviceMethod;

import com.bank.dtoData.UserDatadto;

import java.util.List;

public interface UserServiceMethod {






    UserDatadto findByUserName(String username);
    List<UserDatadto> listAllUsers();
    void save(UserDatadto user);
    //    void deleteByUserName(String username);
    UserDatadto update(UserDatadto user);
    void delete(String username);
    List<UserDatadto> listAllByRole(String role);

}
