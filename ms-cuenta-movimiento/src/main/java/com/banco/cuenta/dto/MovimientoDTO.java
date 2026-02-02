package com.banco.cuenta.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * valor positivo = DEPOSITO
 * valor negativo = RETIRO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoDTO {

    private Long id;

    private LocalDateTime fecha;

    private String tipoMovimiento;

    @NotNull
    private BigDecimal valor;

    private BigDecimal saldo;

    @NotBlank
    private String numeroCuenta;
}