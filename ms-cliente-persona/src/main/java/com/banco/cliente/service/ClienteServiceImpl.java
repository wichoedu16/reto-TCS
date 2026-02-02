package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDTO;
import com.banco.cliente.entity.ClienteEntity;
import com.banco.cliente.exception.RecursoDuplicadoException;
import com.banco.cliente.exception.RecursoNoEncontradoException;
import com.banco.cliente.mapper.ClienteMapper;
import com.banco.cliente.messaging.ClienteEventPublisher;
import com.banco.cliente.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final ClienteMapper mapper;
    private final ClienteEventPublisher eventPublisher;

    @Override
    public ClienteDTO crear(ClienteDTO dto) throws RecursoDuplicadoException {
        if (repository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new RecursoDuplicadoException("Cliente", "identificación", dto.getIdentificacion());
        }
        if (repository.existsByClienteId(dto.getClienteId())) {
            throw new RecursoDuplicadoException("Cliente", "clienteId", dto.getClienteId());
        }
        ClienteEntity saved = repository.save(mapper.dtoToDomain(dto));
        eventPublisher.publishClienteUpserted(saved.getClienteId(), saved.getNombre(), saved.getEstado());
        return mapper.entityToDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> buscarTodos() {
        return mapper.entitiesToDtos(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorId(Long id) throws RecursoNoEncontradoException {
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));
        return mapper.entityToDto(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarPorClienteId(String clienteId) throws RecursoNoEncontradoException {
        ClienteEntity entity = repository.findByClienteId(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", "clienteId", clienteId));
        return mapper.entityToDto(entity);
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) throws RecursoNoEncontradoException, RecursoDuplicadoException {
        ClienteEntity entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));

        if (!entity.getIdentificacion().equals(dto.getIdentificacion())
                && repository.existsByIdentificacion(dto.getIdentificacion())) {
            throw new RecursoDuplicadoException("Cliente", "identificación", dto.getIdentificacion());
        }

        entity.setNombre(dto.getNombre());
        entity.setGenero(dto.getGenero());
        entity.setEdad(dto.getEdad());
        entity.setIdentificacion(dto.getIdentificacion());
        entity.setDireccion(dto.getDireccion());
        entity.setTelefono(dto.getTelefono());
        entity.setContrasena(dto.getContrasena());
        entity.setEstado(dto.getEstado());

        ClienteEntity saved = repository.save(entity);
        eventPublisher.publishClienteUpserted(saved.getClienteId(), saved.getNombre(), saved.getEstado());
        return mapper.entityToDto(saved);
    }

    @Override
    public void eliminar(Long id) throws RecursoNoEncontradoException {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Cliente", id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorClienteId(String clienteId) {
        return repository.existsByClienteId(clienteId);
    }
}