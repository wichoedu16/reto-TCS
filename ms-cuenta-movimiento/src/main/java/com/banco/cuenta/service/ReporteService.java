package com.banco.cuenta.service;

import com.banco.cuenta.dto.ReporteDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {
    List<ReporteDTO> generarEstadoCuenta(String clienteId, LocalDate desde, LocalDate hasta);
}
