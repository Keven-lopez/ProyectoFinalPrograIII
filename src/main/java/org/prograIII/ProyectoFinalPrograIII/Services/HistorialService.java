package org.prograIII.ProyectoFinalPrograIII.Services;

import org.prograIII.ProyectoFinalPrograIII.Database.Repositories.HistorialRepository;
import org.springframework.stereotype.Service;
import umg.principal.EstructurasDeDatos.ListaHistorial;
import umg.principal.Models.Historial;
import org.prograIII.ProyectoFinalPrograIII.Database.Entities.HistorialEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialService {
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
        System.out.println(historial);
    }

    public void showHistorial(){
        System.out.println(lista.getAll());
    }

    public void loadFromDB(){
        List<HistorialEntity> historialEntities = historialRepository.findAll();
        for(HistorialEntity historialEntity : historialEntities){
            lista.addHistorial(historialEntity);
        }
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
