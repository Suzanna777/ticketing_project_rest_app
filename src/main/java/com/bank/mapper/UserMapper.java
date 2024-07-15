package com.bank.mapper;

import com.bank.dtoData.UserDatadto;
import com.bank.entityTable.UserTable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class UserMapper {


    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserTable convertToEntity(UserDatadto dto){
        return modelMapper.map(dto,UserTable.class);

    }

    public UserDatadto convertToDto(UserTable entity){
        return modelMapper.map(entity,UserDatadto.class);
    }

}

