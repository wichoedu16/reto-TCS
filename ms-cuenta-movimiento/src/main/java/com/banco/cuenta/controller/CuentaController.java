package com.banco.cuenta.controller;

import com.banco.cuenta.dto.CuentaDTO;
import com.banco.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CuentaDTO crear(@Validated(CuentaDTO.Crear.class) @RequestBody CuentaDTO dto) {
        return cuentaService.crear(dto);
    }

    @PutMapping("/{id}")
    public CuentaDTO actualizar(
            @PathVariable Long id,
            @Validated(CuentaDTO.Actualizar.class) @RequestBody CuentaDTO dto
    ) {
        dto.setId(id);
        return cuentaService.actualizar(id, dto);
    }

    @GetMapping("/{id}")
    public CuentaDTO obtener(@PathVariable Long id) {
        return cuentaService.obtenerPorId(id);
    }

    @GetMapping
    public List<CuentaDTO> listar() {
        return cuentaService.listar();
    }

    @GetMapping("/numero/{numeroCuenta}")
    public CuentaDTO obtenerPorNumero(@PathVariable String numeroCuenta) {
        return cuentaService.obtenerPorNumero(numeroCuenta);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<CuentaDTO> listarPorCliente(@PathVariable String clienteId) {
        return cuentaService.listarPorCliente(clienteId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cuentaService.eliminar(id);
    }
}
