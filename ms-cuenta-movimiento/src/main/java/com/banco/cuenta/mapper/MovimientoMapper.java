package com.banco.cuenta.mapper;

import com.banco.cuenta.dto.MovimientoDTO;
import com.banco.cuenta.entity.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovimientoMapper {

    @Mapping(target = "numeroCuenta", source = "cuenta.numeroCuenta")
    MovimientoDTO entityToDto(Movimiento entity);

    List<MovimientoDTO> entitiesToDtos(List<Movimiento> entities);

    // Para crear movimiento, la Cuenta se resuelve en el service (por numeroCuenta)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    @Mapping(target = "saldo", ignore = true)
    @Mapping(target = "cuenta", ignore = true)
    Movimiento dtoToEntity(MovimientoDTO dto);
}
