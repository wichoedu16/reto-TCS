package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDTO;
import com.banco.cliente.exception.RecursoDuplicadoException;
import com.banco.cliente.exception.RecursoNoEncontradoException;

import java.util.List;

public interface ClienteService {

    ClienteDTO crear(ClienteDTO dto) throws RecursoDuplicadoException;

    List<ClienteDTO> buscarTodos();

    ClienteDTO buscarPorId(Long id) throws RecursoNoEncontradoException;

    ClienteDTO buscarPorClienteId(String clienteId) throws RecursoNoEncontradoException;

    ClienteDTO actualizar(Long id, ClienteDTO dto) throws RecursoNoEncontradoException, RecursoDuplicadoException;

    void eliminar(Long id) throws RecursoNoEncontradoException;

    boolean existePorClienteId(String clienteId);
}
