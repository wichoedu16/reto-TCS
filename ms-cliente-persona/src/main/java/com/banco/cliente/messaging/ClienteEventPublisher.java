package com.banco.cliente.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishClienteUpserted(String clienteId, String nombre, Boolean estado) {
        ClienteUpsertedEvent event = new ClienteUpsertedEvent(clienteId, nombre, estado);
        rabbitTemplate.convertAndSend("banco.clientes.exchange", "cliente.upserted", event);
    }
}
