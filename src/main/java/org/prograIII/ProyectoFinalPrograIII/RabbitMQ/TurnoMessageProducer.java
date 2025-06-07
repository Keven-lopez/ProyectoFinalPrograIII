package org.prograIII.ProyectoFinalPrograIII.RabbitMQ;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umg.principal.Models.Turno;

@Service
public class TurnoMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(TurnoMessageProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendTurnoCreated(Turno turno) {
        try {
            TurnoMessage message = new TurnoMessage(
                    turno.getId(),
                    turno.getClienteId(),
                    turno.getServicioId(),
                    turno.getFechaCreacion(),
                    turno.getEstado(),
                    "CREATED"
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TURNO_EXCHANGE,
                    RabbitMQConfig.TURNO_CREATED_ROUTING_KEY,
                    message
            );

            logger.info("Sent turno created message: {}", message);
        } catch (Exception e) {
            logger.error("Error sending turno created message", e);
        }
    }

    public void sendTurnoProcessed(Turno turno) {
        try {
            TurnoMessage message = new TurnoMessage(
                    turno.getId(),
                    turno.getClienteId(),
                    turno.getServicioId(),
                    turno.getFechaCreacion(),
                    turno.getEstado(),
                    "PROCESSED"
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TURNO_EXCHANGE,
                    RabbitMQConfig.TURNO_PROCESSED_ROUTING_KEY,
                    message
            );

            logger.info("Sent turno processed message: {}", message);
        } catch (Exception e) {
            logger.error("Error sending turno processed message", e);
        }
    }

    public void sendTurnoDeleted(int turnoId) {
        try {
            TurnoMessage message = new TurnoMessage(
                    turnoId,
                    null,
                    null,
                    null,
                    "DELETED",
                    "DELETED"
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.TURNO_EXCHANGE,
                    RabbitMQConfig.TURNO_DELETED_ROUTING_KEY,
                    message
            );

            logger.info("Sent turno deleted message: {}", message);
        } catch (Exception e) {
            logger.error("Error sending turno deleted message", e);
        }
    }
}
