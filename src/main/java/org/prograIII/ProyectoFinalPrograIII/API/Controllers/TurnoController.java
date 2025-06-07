package org.prograIII.ProyectoFinalPrograIII.API.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.prograIII.ProyectoFinalPrograIII.RabbitMQ.TurnoMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.prograIII.ProyectoFinalPrograIII.API.Services.TurnoService;
import umg.principal.Database.Entities.TurnoEntity;
import org.prograIII.ProyectoFinalPrograIII.API.Requests.CreateTurnoRequest;
import umg.principal.Models.Turno;
import org.prograIII.ProyectoFinalPrograIII.API.Services.HistorialService;
import org.prograIII.ProyectoFinalPrograIII.API.Requests.CreateHistorialRequest;

@RestController
@Tag(name = "Turno Management", description = "APIs for managing appointment queues")
@RequestMapping("/api/v1")
@Validated
public class TurnoController {

    @Autowired
    private final TurnoService turnoService;

    @Autowired
    private final HistorialService historialService;

    @Autowired
    private final TurnoMessageProducer messageProducer; // Add RabbitMQ producer

    public TurnoController(TurnoService turnoService, HistorialService historialService,
                           TurnoMessageProducer messageProducer) {
        this.turnoService = turnoService;
        this.historialService = historialService;
        this.messageProducer = messageProducer;
    }

    @Operation(summary = "Create a new Turno")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turno created"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping("/turno")
    public ResponseEntity<Turno> createTurno(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body for creating a Turno",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CreateTurnoRequest.class))
            ) @RequestBody CreateTurnoRequest request) {

        try {
            Turno turno = new Turno();
            turno.setClienteId(request.getClienteId());
            turno.setServicioId(request.getServicioId());
            turno.setFechaCreacion(LocalDateTime.now());
            turno.setEstado("PENDIENTE");

            turnoService.queueTurno(turno);

            messageProducer.sendTurnoCreated(turno);

            return ResponseEntity.status(HttpStatus.CREATED).body(turno);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/turno/siguiente")
    public ResponseEntity<Turno> processNextTurno(@RequestBody CreateHistorialRequest request) {
        try {
            TurnoEntity processed = turnoService.processNextTurno();
            if (processed != null) {
                Turno turno = new Turno();
                turno.setId(processed.getId());
                turno.setClienteId(processed.getClienteId());
                turno.setServicioId(processed.getServicioId());
                turno.setFechaCreacion(processed.getFechaCreacion());
                turno.setEstado(processed.getEstado());

                historialService.insertHistorial(processed.getId(), request.getObservacion());

                messageProducer.sendTurnoProcessed(turno);

                return ResponseEntity.ok(turno);
            } else {
                return ResponseEntity.noContent().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/turno/cola")
    public ResponseEntity<String> showQueue() {
        turnoService.mostrarCola();
        return ResponseEntity.ok("Cola mostrada en consola");
    }

    @DeleteMapping("/turno/{id}")
    public ResponseEntity<Void> deleteTurno(@PathVariable int id) {
        try {
            boolean deleted = turnoService.deleteTurnoById(id);
            if (deleted) {
                messageProducer.sendTurnoDeleted(id);

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/turno/health/rabbitmq")
    public ResponseEntity<Map<String, String>> checkRabbitMQHealth() {
        Map<String, String> health = new HashMap<>();
        try {
            // Simple test to verify RabbitMQ connectivity
            messageProducer.sendTurnoCreated(new Turno()); // Test message
            health.put("status", "UP");
            health.put("message", "RabbitMQ connection is healthy");
            return ResponseEntity.ok(health);
        } catch (Exception e) {
            health.put("status", "DOWN");
            health.put("message", "RabbitMQ connection failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(health);
        }
    }
}