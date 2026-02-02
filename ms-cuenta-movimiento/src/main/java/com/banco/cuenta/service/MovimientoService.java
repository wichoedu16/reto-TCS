package com.banco.cuenta.service;

import com.banco.cuenta.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService {
    MovimientoDTO crearMovimiento(MovimientoDTO dto);
    MovimientoDTO obtenerPorId(Long id);
    List<MovimientoDTO> listar();
    void eliminar(Long id);

    List<MovimientoDTO> listarPorNumeroCuenta(String numeroCuenta);
}
