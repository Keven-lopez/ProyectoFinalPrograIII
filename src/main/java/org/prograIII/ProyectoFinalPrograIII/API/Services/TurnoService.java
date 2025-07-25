package org.prograIII.ProyectoFinalPrograIII.API.Services;

import umg.principal.Database.Entities.TurnoEntity;
import org.springframework.stereotype.Service;
import umg.principal.EstructurasDeDatos.ColaDeTurnos;
import umg.principal.Database.Repositories.TurnoRepository;
import umg.principal.Models.Turno;
import umg.principal.EstructurasDeDatos.PilaDeAcciones;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TurnoService {
    Logger logger = Logger.getLogger(TurnoService.class.getName());
    private final TurnoRepository turnoRepository;
    private final ColaDeTurnos cola;
    private final PilaDeAcciones pila;

    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
        this.cola = new ColaDeTurnos();
        this.pila = new PilaDeAcciones();
    }

    public void queueTurno(Turno turno) {
        //insertar turno en la cola y pila
        cola.insertar(turno);
        pila.insertar(turno);

        //guardar turno en db
        TurnoEntity turnoEntity = mapToEntity(turno);
        turnoRepository.save(turnoEntity);

        logger.info("Turno encolado: " + turnoEntity);
    }

    public TurnoEntity processNextTurno() {
        Turno next = cola.quitar();
        if (next == null) {
            logger.warning("No hay turnos en cola.");
            return null;
        }

        next.setEstado("ATENDIDO");

        TurnoEntity entity = mapToEntity(next);
        turnoRepository.save(entity);

        logger.info("Turno atendido y guardado: " + next);
        return mapToEntity(next);
    }

    public void mostrarCola() {
        cola.buscarCola();
    }

    public void loadFromDB() {
        List<TurnoEntity> pendientes = turnoRepository.findByEstadoOrderByIdAsc("PENDIENTE");

        for (TurnoEntity entity : pendientes) {
            Turno turno = mapToModel(entity);
            cola.insertar(turno);
            pila.insertar(turno);
            logger.info("Turno cargado en cola: " + turno);
        }
    }

    public boolean deleteTurnoById(int id) {
        if (turnoRepository.existsById(id)) {
            turnoRepository.deleteById(id);
            cola.deleteById(id);
            logger.info("Turno eliminado con ID: " + id);
            return true;
        } else {
            logger.warning("Turno no encontrado con ID: " + id);
            return false;
        }
    }


    private TurnoEntity mapToEntity(Turno turno) {
        TurnoEntity entity = new TurnoEntity();
        entity.setId(turno.getId());
        entity.setFechaCreacion(turno.getFechaCreacion());
        entity.setEstado(turno.getEstado());
        entity.setClienteId(turno.getClienteId());
        entity.setServicioId(turno.getServicioId());
        return entity;
    }

    private Turno mapToModel(TurnoEntity entity) {
        Turno turno = new Turno();
        turno.setId(entity.getId());
        turno.setFechaCreacion(entity.getFechaCreacion());
        turno.setEstado(entity.getEstado());
        turno.setClienteId(entity.getClienteId());
        turno.setServicioId(entity.getServicioId());
        return turno;
    }

}
