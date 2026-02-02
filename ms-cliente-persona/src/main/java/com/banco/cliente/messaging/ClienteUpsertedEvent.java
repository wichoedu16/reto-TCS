package com.banco.cliente.messaging;

public record ClienteUpsertedEvent(
        String clienteId,
        String nombre,
        Boolean estado
) {}
