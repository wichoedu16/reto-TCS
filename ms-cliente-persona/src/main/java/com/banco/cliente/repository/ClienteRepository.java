package com.banco.cliente.repository;

import com.banco.cliente.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    /**
     * Buscar cliente por clienteId (código de negocio)
     */
    Optional<ClienteEntity> findByClienteId(String clienteId);

    /**
     * Buscar cliente por identificación (cédula)
     */
    Optional<ClienteEntity> findByIdentificacion(String identificacion);

    /**
     * Verificar si existe un cliente con esa identificación
     */
    boolean existsByIdentificacion(String identificacion);

    /**
     * Verificar si existe un cliente con ese clienteId
     */
    boolean existsByClienteId(String clienteId);
}