package com.banco.cuenta.controller;

import com.banco.cuenta.dto.MovimientoDTO;
import com.banco.cuenta.service.MovimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoDTO crear(@Validated @RequestBody MovimientoDTO dto) {
        return movimientoService.crearMovimiento(dto);
    }

    @GetMapping("/{id}")
    public MovimientoDTO obtener(@PathVariable Long id) {
        return movimientoService.obtenerPorId(id);
    }

    @GetMapping
    public List<MovimientoDTO> listar() {
        return movimientoService.listar();
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public List<MovimientoDTO> listarPorCuenta(@PathVariable String numeroCuenta) {
        return movimientoService.listarPorNumeroCuenta(numeroCuenta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        movimientoService.eliminar(id);
    }
}
