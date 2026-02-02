package com.banco.cuenta.service;

import com.banco.cuenta.dto.MovimientoDTO;
import com.banco.cuenta.entity.Cuenta;
import com.banco.cuenta.entity.Movimiento;
import com.banco.cuenta.exception.SaldoNoDisponibleException;
import com.banco.cuenta.mapper.MovimientoMapper;
import com.banco.cuenta.repository.CuentaRepository;
import com.banco.cuenta.repository.MovimientoRepository;
import com.banco.cuenta.service.impl.MovimientoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MovimientoService Tests")
class MovimientoServiceTest {

    @Mock private CuentaRepository cuentaRepository;
    @Mock private MovimientoRepository movimientoRepository;
    @Mock private MovimientoMapper movimientoMapper;

    @InjectMocks
    private MovimientoServiceImpl service;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("225487");
        cuenta.setSaldoInicial(new BigDecimal("100.00"));
        cuenta.setSaldoActual(new BigDecimal("100.00"));
        cuenta.setEstado(true);
        cuenta.setClienteId("CLI001");
        cuenta.setTipoCuenta("Corriente");
    }

    @Test
    @DisplayName("Depósito: actualiza saldo y registra movimiento")
    void depositoOk() {
        MovimientoDTO dto = MovimientoDTO.builder()
                .numeroCuenta("225487")
                .valor(new BigDecimal("600.00"))
                .build();

        Movimiento movEntity = new Movimiento();
        MovimientoDTO dtoOut = MovimientoDTO.builder()
                .numeroCuenta("225487")
                .valor(new BigDecimal("600.00"))
                .saldo(new BigDecimal("700.00"))
                .build();

        when(cuentaRepository.findByNumeroCuenta("225487")).thenReturn(Optional.of(cuenta));
        when(cuentaRepository.save(any(Cuenta.class))).thenAnswer(inv -> inv.getArgument(0));
        when(movimientoMapper.dtoToEntity(dto)).thenReturn(movEntity);
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(inv -> inv.getArgument(0));
        when(movimientoMapper.entityToDto(any(Movimiento.class))).thenReturn(dtoOut);

        MovimientoDTO result = service.crearMovimiento(dto);

        assertThat(result.getSaldo()).isEqualByComparingTo(new BigDecimal("700.00"));
        verify(cuentaRepository, times(1)).save(argThat(c -> c.getSaldoActual().compareTo(new BigDecimal("700.00")) == 0));
        verify(movimientoRepository, times(1)).save(argThat(m -> m.getSaldo().compareTo(new BigDecimal("700.00")) == 0));
    }

    @Test
    @DisplayName("Retiro sin saldo: lanza SaldoNoDisponible y no guarda nada")
    void retiroSinSaldo() {
        MovimientoDTO dto = MovimientoDTO.builder()
                .numeroCuenta("225487")
                .valor(new BigDecimal("-200.00"))
                .build();

        when(cuentaRepository.findByNumeroCuenta("225487")).thenReturn(Optional.of(cuenta));

        assertThatThrownBy(() -> service.crearMovimiento(dto))
                .isInstanceOf(SaldoNoDisponibleException.class)
                .hasMessageContaining("Saldo no disponible");

        verify(cuentaRepository, never()).save(any());
        verify(movimientoRepository, never()).save(any());
    }
}
