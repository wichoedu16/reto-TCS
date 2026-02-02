package com.banco.cuenta.messaging;

import com.banco.cuenta.entity.ClienteCache;
import com.banco.cuenta.repository.ClienteCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ClienteEventListener {

    private final ClienteCacheRepository repo;

    @RabbitListener(queues = "banco.clientes.queue")
    public void onClienteUpserted(ClienteUpsertedEvent event) {
        ClienteCache c = repo.findById(event.clienteId())
                .orElseGet(() -> new ClienteCache(event.clienteId(), event.nombre(), event.estado(), LocalDateTime.now()));

        c.setNombre(event.nombre());
        c.setEstado(event.estado());
        c.setUpdatedAt(LocalDateTime.now());

        repo.save(c);
    }
}
