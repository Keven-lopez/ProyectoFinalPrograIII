package org.prograIII.ProyectoFinalPrograIII.RabbitMQ;

import org.prograIII.ProyectoFinalPrograIII.API.Services.HistorialService;
import org.prograIII.ProyectoFinalPrograIII.API.Services.TurnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TurnoMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TurnoMessageConsumer.class);

    @Autowired
    private TurnoService turnoService;

    @Autowired
    private HistorialService historialService;

    @RabbitListener(queues = RabbitMQConfig.TURNO_QUEUE)
    public void handleTurnoCreated(TurnoMessage message) {
        try {
            logger.info("Received turno created message: {}", message);

        } catch (Exception e) {
            logger.error("Error processing turno created message: {}", message, e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.TURNO_PROCESSED_QUEUE)
    public void handleTurnoProcessed(TurnoMessage message) {
        try {
            logger.info("Received turno processed message: {}", message);
        } catch (Exception e) {
            logger.error("Error processing turno processed message: {}", message, e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.TURNO_DELETED_QUEUE)
    public void handleTurnoDeleted(TurnoMessage message) {
        try {
            logger.info("Received turno deleted message: {}", message);

        } catch (Exception e) {
            logger.error("Error processing turno deleted message: {}", message, e);
        }
    }

}