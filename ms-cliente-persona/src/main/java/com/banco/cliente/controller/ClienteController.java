package com.banco.cliente.controller;

import com.banco.cliente.dto.ClienteDTO;
import com.banco.cliente.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO crear(@Validated(ClienteDTO.Crear.class) @RequestBody ClienteDTO dto) {
        return clienteService.crear(dto);
    }

    @GetMapping
    public List<ClienteDTO> buscarTodos() {
        return clienteService.buscarTodos();
    }

    @GetMapping("/{id}")
    public ClienteDTO buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @GetMapping("/buscar/{clienteId}")
    public ClienteDTO buscarPorClienteId(@PathVariable String clienteId) {
        return clienteService.buscarPorClienteId(clienteId);
    }

    @PutMapping("/{id}")
    public ClienteDTO actualizar(
            @PathVariable Long id,
            @Validated(ClienteDTO.Actualizar.class) @RequestBody ClienteDTO dto) {
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
    }

    @GetMapping("/existe/{clienteId}")
    public Boolean existeCliente(@PathVariable String clienteId) {
        return clienteService.existePorClienteId(clienteId);
    }
}
