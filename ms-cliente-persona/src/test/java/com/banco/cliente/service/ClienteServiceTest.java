package com.banco.cliente.service;

import com.banco.cliente.dto.ClienteDTO;
import com.banco.cliente.entity.ClienteEntity;
import com.banco.cliente.exception.RecursoDuplicadoException;
import com.banco.cliente.exception.RecursoNoEncontradoException;
import com.banco.cliente.mapper.ClienteMapper;
import com.banco.cliente.messaging.ClienteEventPublisher;
import com.banco.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ClienteService Tests")
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @Mock
    private ClienteMapper mapper;

    @Mock
    private ClienteEventPublisher eventPublisher;

    @InjectMocks
    private ClienteServiceImpl service;

    private ClienteDTO dto;
    private ClienteEntity entity;

    @BeforeEach
    void setUp() {
        dto = ClienteDTO.builder()
                .nombre("Jose Lema")
                .identificacion("1234567890")
                .clienteId("CLI001")
                .contrasena("1234")
                .estado(true)
                .build();

        entity = new ClienteEntity();
        entity.setId(1L);
        entity.setNombre("Jose Lema");
        entity.setIdentificacion("1234567890");
        entity.setClienteId("CLI001");
        entity.setEstado(true);
    }

    @Test
    @DisplayName("Crear cliente exitosamente")
    void crearClienteExitoso() {
        when(repository.existsByIdentificacion(any())).thenReturn(false);
        when(repository.existsByClienteId(any())).thenReturn(false);
        when(mapper.dtoToDomain(dto)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.entityToDto(entity)).thenReturn(dto);

        ClienteDTO result = service.crear(dto);

        assertThat(result).isNotNull();
        verify(repository, times(1)).save(entity);
        verify(eventPublisher, times(1)).publishClienteUpserted(entity.getClienteId(), entity.getNombre(), entity.getEstado());
    }

    @Test
    @DisplayName("Error al crear cliente con identificación duplicada")
    void crearClienteIdentificacionDuplicada() {
        when(repository.existsByIdentificacion("1234567890")).thenReturn(true);

        assertThatThrownBy(() -> service.crear(dto))
                .isInstanceOf(RecursoDuplicadoException.class)
                .hasMessageContaining("identificación");

        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Buscar cliente por ID exitoso")
    void buscarPorIdExitoso() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.entityToDto(entity)).thenReturn(dto);

        ClienteDTO resultado = service.buscarPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Jose Lema");
    }

    @Test
    @DisplayName("Error al buscar cliente inexistente")
    void buscarPorIdNoEncontrado() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(999L))
                .isInstanceOf(RecursoNoEncontradoException.class);
    }

    @Test
    @DisplayName("Eliminar cliente exitoso")
    void eliminarClienteExitoso() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminar(1L);

        verify(repository, times(1)).deleteById(1L);
    }
}
