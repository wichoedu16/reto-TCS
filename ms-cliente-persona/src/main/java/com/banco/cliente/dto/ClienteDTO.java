package com.banco.cliente.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDTO {

    @Null(groups = Crear.class)
    @NotNull(groups = Actualizar.class)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String nombre;

    @Size(max = 20)
    private String genero;

    @PositiveOrZero
    private Integer edad;

    @NotBlank
    @Size(max = 20)
    private String identificacion;

    @Size(max = 200)
    private String direccion;

    @Size(max = 20)
    private String telefono;

    @NotBlank
    @Size(max = 50)
    private String clienteId;

    @NotBlank(groups = Crear.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasena;

    @NotNull
    private Boolean estado;

    public interface Crear {}
    public interface Actualizar {}
}
