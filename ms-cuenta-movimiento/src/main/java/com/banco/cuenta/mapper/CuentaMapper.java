package com.banco.cuenta.mapper;

import com.banco.cuenta.dto.CuentaDTO;
import com.banco.cuenta.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaMapper {

    CuentaDTO entityToDto(Cuenta entity);

    List<CuentaDTO> entitiesToDtos(List<Cuenta> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "saldoActual", ignore = true) // se setea por @PrePersist o en service
    Cuenta dtoToEntity(CuentaDTO dto);
}
