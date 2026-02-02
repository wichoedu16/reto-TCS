package com.banco.cuenta.service.impl;

import com.banco.cuenta.dto.ReporteDTO;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.exception.RecursoNoEncontradoException;
import com.banco.cuenta.repository.ClienteCacheRepository;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import com.banco.cuenta.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final ClienteCacheRepository clienteCacheRepository;

    @Override
    public List<ReporteDTO> generarEstadoCuenta(String clienteId, LocalDate desde, LocalDate hasta) {
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);
        if (cuentas.isEmpty()) {
            throw new RecursoNoEncontradoException("Cuenta", "clienteId", clienteId);
        }

        String clienteNombre = clienteCacheRepository.findById(clienteId)
                .map(c -> c.getNombre())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "clienteId", clienteId));

        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();

        List<ReporteDTO> filas = new ArrayList<>();

        for (Cuenta cuenta : cuentas) {
            List<Movimiento> movimientos = movimientoRepository
                    .findByCuenta_IdAndFechaGreaterThanEqualAndFechaLessThan(cuenta.getId(), inicio, fin);

            for (Movimiento mov : movimientos) {
                filas.add(ReporteDTO.builder()
                        .fecha(mov.getFecha().toLocalDate())
                        .cliente(clienteNombre)
                        .numeroCuenta(cuenta.getNumeroCuenta())
                        .tipo(cuenta.getTipoCuenta())
                        .saldoInicial(cuenta.getSaldoInicial())
                        .estado(cuenta.getEstado())
                        .movimiento(mov.getValor())
                        .saldoDisponible(mov.getSaldo())
                        .build());
            }
        }

        filas.sort(Comparator.comparing(ReporteDTO::getFecha).reversed());
        return filas;
    }
}
