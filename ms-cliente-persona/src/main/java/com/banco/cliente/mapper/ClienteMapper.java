package com.banco.cliente.mapper;

import com.banco.cliente.dto.ClienteDTO;
import com.banco.cliente.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    ClienteDTO entityToDto(ClienteEntity entity);

    List<ClienteDTO> entitiesToDtos(List<ClienteEntity> entities);

    @Mapping(target = "id", ignore = true)
    ClienteEntity dtoToDomain(ClienteDTO dto);

}