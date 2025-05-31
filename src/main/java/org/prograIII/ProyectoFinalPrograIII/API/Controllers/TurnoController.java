package org.prograIII.ProyectoFinalPrograIII.API.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

import org.prograIII.ProyectoFinalPrograIII.Services.TurnoService;
import org.prograIII.ProyectoFinalPrograIII.Database.Entities.TurnoEntity;
import org.prograIII.ProyectoFinalPrograIII.API.Requests.CreateTurnoRequest;
import umg.principal.Models.Turno;
import org.prograIII.ProyectoFinalPrograIII.Services.HistorialService;
import org.prograIII.ProyectoFinalPrograIII.API.Requests.CreateHistorialRequest;


@RestController
public class TurnoController {

    @Autowired
    private final TurnoService turnoService;

    @Autowired
    private final HistorialService historialService;

    public TurnoController(TurnoService turnoService, HistorialService historialService) {
        this.turnoService = turnoService;
        this.historialService = historialService;
    }

    @PostMapping("/turno")
    public ResponseEntity<Turno> createTurno(@RequestBody CreateTurnoRequest request) {
        try {
            // Create new Turno object
            Turno turno = new Turno();
            turno.setClienteId(request.getClienteId());
            turno.setServicioId(request.getServicioId());
            turno.setFechaCreacion(LocalDateTime.now());
            turno.setEstado("PENDIENTE");

            // Queue the turno (this will also save to DB)
            turnoService.queueTurno(turno);

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
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}