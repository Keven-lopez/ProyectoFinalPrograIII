package org.prograIII.ProyectoFinalPrograIII.Database.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import umg.principal.Models.Turno;

import java.time.LocalDateTime;

@Entity
@Table(name = "turno")
@Getter
@Setter
public class TurnoEntity extends Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cliente_id")
    private int clienteId;

    @Column(name = "servicio_id")
    private int servicioId;

}