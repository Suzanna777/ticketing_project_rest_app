package com.bank.mapper;

import com.bank.dtoData.RoleDatadto;
import com.bank.entityTable.RoleTable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RoleTable convertToEntity(RoleDatadto dto){
        return modelMapper.map(dto,RoleTable.class);
    }

    public RoleDatadto convertToDto(RoleTable entity){
        return modelMapper.map(entity,RoleDatadto.class);
    }

}
