package com.banco.cuenta.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaDTO {

    @Null(groups = Crear.class)
    @NotNull(groups = Actualizar.class)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String numeroCuenta;

    @NotBlank
    @Size(max = 20)
    private String tipoCuenta;

    @NotNull
    @PositiveOrZero
    private BigDecimal saldoInicial;

    private BigDecimal saldoActual;

    @NotNull
    private Boolean estado;

    @NotBlank
    private String clienteId;

    public interface Crear {}
    public interface Actualizar {}
}