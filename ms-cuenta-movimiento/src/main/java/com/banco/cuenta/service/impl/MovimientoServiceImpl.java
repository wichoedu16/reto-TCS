package com.banco.cuenta.service.impl;

import com.banco.cuenta.dto.MovimientoDTO;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.exception.RecursoNoEncontradoException;
import com.banco.cuenta.exception.SaldoNoDisponibleException;
import com.banco.cuenta.mapper.MovimientoMapper;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import com.banco.cuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MovimientoServiceImpl implements MovimientoService {

    private final CuentaRepository cuentaRepository;
    private final MovimientoRepository movimientoRepository;
    private final MovimientoMapper movimientoMapper;

    @Override
    public MovimientoDTO crearMovimiento(MovimientoDTO dto) {
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(dto.getNumeroCuenta())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cuenta", "numeroCuenta", dto.getNumeroCuenta()));

        BigDecimal saldoActual = cuenta.getSaldoActual() == null ? cuenta.getSaldoInicial() : cuenta.getSaldoActual();
        BigDecimal nuevoSaldo = saldoActual.add(dto.getValor());

        if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new SaldoNoDisponibleException();
        }

        cuenta.setSaldoActual(nuevoSaldo);
        cuentaRepository.save(cuenta);

        Movimiento mov = movimientoMapper.dtoToEntity(dto);
        mov.setCuenta(cuenta);
        mov.setSaldo(nuevoSaldo);

        if (dto.getTipoMovimiento() == null || dto.getTipoMovimiento().isBlank()) {
            mov.setTipoMovimiento(dto.getValor().compareTo(BigDecimal.ZERO) >= 0 ? "DEPOSITO" : "RETIRO");
        } else {
            mov.setTipoMovimiento(dto.getTipoMovimiento());
        }

        return movimientoMapper.entityToDto(movimientoRepository.save(mov));
    }

    @Override
    @Transactional(readOnly = true)
    public MovimientoDTO obtenerPorId(Long id) {
        Movimiento entity = movimientoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Movimiento", id));
        return movimientoMapper.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listar() {
        return movimientoMapper.entitiesToDtos(movimientoRepository.findAll());
    }

    @Override
    public void eliminar(Long id) {
        if (!movimientoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Movimiento", id);
        }
        movimientoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoDTO> listarPorNumeroCuenta(String numeroCuenta) {
        return movimientoMapper.entitiesToDtos(movimientoRepository.findByCuenta_NumeroCuenta(numeroCuenta));
    }
}
