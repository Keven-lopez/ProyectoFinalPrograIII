package org.prograIII.ProyectoFinalPrograIII.Database.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umg.principal.Models.Historial;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial")
@Getter
@Setter
public class HistorialEntity extends Historial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "turno_id")
    private int turnoId;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "observaciones")
    private String observaciones;
}
