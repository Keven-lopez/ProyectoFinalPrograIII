package org.prograIII.ProyectoFinalPrograIII.API.Services;

import java.util.logging.Logger;
import umg.principal.Database.Repositories.HistorialRepository;
import org.springframework.stereotype.Service;
import umg.principal.EstructurasDeDatos.ListaHistorial;
import umg.principal.Models.Historial;
import umg.principal.Database.Entities.HistorialEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialService {
    Logger logger = Logger.getLogger(HistorialService.class.getName());
    private final HistorialRepository historialRepository;
    private final ListaHistorial lista;

    public HistorialService(HistorialRepository historialRepository) {
        this.historialRepository = historialRepository;
        this.lista = new ListaHistorial();
    }

    public void insertHistorial(int turno_id, String observacion) {
        Historial historial = new Historial();
        historial.setTurnoId(turno_id);
        historial.setFechaFinalizacion(LocalDateTime.now());
        historial.setObservaciones(observacion);
        lista.addHistorial(historial);

        HistorialEntity historialEntity = mapToHistorial(historial);
        historialRepository.save(historialEntity);
        logger.info(historialEntity.toString());
    }

    public void showHistorial(){
        logger.info(lista.getAll().toString());
    }

    public void loadFromDB(){
        List<HistorialEntity> historialEntities = historialRepository.findAll();
        for(HistorialEntity historialEntity : historialEntities){
            lista.addHistorial(historialEntity);
        }
        logger.info("Historial Cargado");
    }

    private HistorialEntity mapToHistorial(Historial historial) {
        HistorialEntity historialEntity = new HistorialEntity();
        historialEntity.setId(historial.getId());
        historialEntity.setTurnoId(historial.getTurnoId());
        historialEntity.setFechaFinalizacion(historial.getFechaFinalizacion());
        historialEntity.setObservaciones(historial.getObservaciones());
        return historialEntity;
    }

}
