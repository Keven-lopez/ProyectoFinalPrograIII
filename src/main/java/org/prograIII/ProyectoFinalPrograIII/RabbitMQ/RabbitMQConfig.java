package org.prograIII.ProyectoFinalPrograIII.RabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Queue names
    public static final String TURNO_QUEUE = "turno.queue";
    public static final String TURNO_PROCESSED_QUEUE = "turno.processed.queue";
    public static final String TURNO_DELETED_QUEUE = "turno.deleted.queue";

    // Exchange names
    public static final String TURNO_EXCHANGE = "turno.exchange";

    // Routing keys
    public static final String TURNO_CREATED_ROUTING_KEY = "turno.created";
    public static final String TURNO_PROCESSED_ROUTING_KEY = "turno.processed";
    public static final String TURNO_DELETED_ROUTING_KEY = "turno.deleted";

    @Bean
    public TopicExchange turnoExchange() {
        return new TopicExchange(TURNO_EXCHANGE);
    }

    @Bean
    public Queue turnoQueue() {
        return QueueBuilder.durable(TURNO_QUEUE).build();
    }

    @Bean
    public Queue turnoProcessedQueue() {
        return QueueBuilder.durable(TURNO_PROCESSED_QUEUE).build();
    }

    @Bean
    public Queue turnoDeletedQueue() {
        return QueueBuilder.durable(TURNO_DELETED_QUEUE).build();
    }

    @Bean
    public Binding turnoBinding() {
        return BindingBuilder.bind(turnoQueue())
                .to(turnoExchange())
                .with(TURNO_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding turnoProcessedBinding() {
        return BindingBuilder.bind(turnoProcessedQueue())
                .to(turnoExchange())
                .with(TURNO_PROCESSED_ROUTING_KEY);
    }

    @Bean
    public Binding turnoDeletedBinding() {
        return BindingBuilder.bind(turnoDeletedQueue())
                .to(turnoExchange())
                .with(TURNO_DELETED_ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}