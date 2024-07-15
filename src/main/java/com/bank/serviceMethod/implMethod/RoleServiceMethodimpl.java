package com.bank.serviceMethod.implMethod;

import com.bank.dtoData.RoleDatadto;
import com.bank.entityTable.RoleTable;
import com.bank.mapper.MapperUtil;
import com.bank.mapper.RoleMapper;
import com.bank.repository.RoleRepository;
import com.bank.serviceMethod.RoleServiceMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RoleServiceMethodimpl implements RoleServiceMethod{

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;
    public RoleServiceMethodimpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<RoleDatadto> listAllRoles() {
        List<RoleTable> roleList = roleRepository.findAll();
//        return roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());
        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDatadto())).collect(Collectors.toList());
//        return roleList.stream().map(role -> mapperUtil.convert(role, RoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public RoleDatadto findById(Long id) {
        return roleMapper.convertToDto(roleRepository.findById(id).get());
    }
}

