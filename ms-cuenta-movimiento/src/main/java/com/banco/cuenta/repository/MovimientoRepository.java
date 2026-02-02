package com.banco.cuenta.repository;

import com.banco.cuenta.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuenta_IdAndFechaGreaterThanEqualAndFechaLessThan(
            Long cuentaId,
            LocalDateTime desde,
            LocalDateTime hastaExclusivo
    );

    List<Movimiento> findByCuenta_NumeroCuenta(String numeroCuenta);
}
