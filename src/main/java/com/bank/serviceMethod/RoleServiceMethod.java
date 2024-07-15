package com.bank.serviceMethod;

import com.bank.dtoData.RoleDatadto;

import java.util.List;

public interface RoleServiceMethod {



    List<RoleDatadto> listAllRoles();
    RoleDatadto findById(Long id);
}

