package com.banco.cuenta.service;

import com.banco.cuenta.dto.CuentaDTO;

import java.util.List;

public interface CuentaService {
    CuentaDTO crear(CuentaDTO dto);
    CuentaDTO actualizar(Long id, CuentaDTO dto);
    CuentaDTO obtenerPorId(Long id);
    CuentaDTO obtenerPorNumero(String numeroCuenta);
    List<CuentaDTO> listar();
    void eliminar(Long id);

    List<CuentaDTO> listarPorCliente(String clienteId);
}
