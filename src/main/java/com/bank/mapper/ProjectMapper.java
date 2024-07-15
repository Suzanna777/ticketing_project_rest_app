package com.bank.mapper;

import com.bank.dtoData.ProjectDatadto;
import com.bank.entityTable.ProjectTable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class ProjectMapper {

    private final ModelMapper modelMapper;

    public ProjectMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProjectTable convertToEntity(ProjectDatadto dto){
        return modelMapper.map(dto,ProjectTable.class);

    }

    public ProjectDatadto convertToDto(ProjectTable entity){

        return modelMapper.map(entity,ProjectDatadto.class);
    }

}

