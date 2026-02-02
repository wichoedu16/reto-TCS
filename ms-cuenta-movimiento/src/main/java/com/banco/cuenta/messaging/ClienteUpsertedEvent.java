package com.banco.cuenta.messaging;

public record ClienteUpsertedEvent(
        String clienteId,
        String nombre,
        Boolean estado
) {}
