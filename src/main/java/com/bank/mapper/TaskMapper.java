package com.bank.mapper;

import com.bank.dtoData.TaskDatadto;
import com.bank.entityTable.TaskTable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class TaskMapper {


    private final ModelMapper modelMapper;

    public TaskMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TaskTable convertToEntity(TaskDatadto dto){
        return modelMapper.map(dto,TaskTable.class);
    }

    public TaskDatadto convertToDto(TaskTable entity){
        return modelMapper.map(entity,TaskDatadto.class);
    }
}

