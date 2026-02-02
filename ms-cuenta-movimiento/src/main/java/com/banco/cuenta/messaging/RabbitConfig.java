package com.banco.cuenta.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange clientesExchange() {
        return ExchangeBuilder.topicExchange("banco.clientes.exchange").durable(true).build();
    }

    @Bean
    public Queue clientesQueue() {
        return QueueBuilder.durable("banco.clientes.queue").build();
    }

    @Bean
    public Binding clientesBinding(TopicExchange clientesExchange, Queue clientesQueue) {
        return BindingBuilder.bind(clientesQueue).to(clientesExchange).with("cliente.upserted");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
