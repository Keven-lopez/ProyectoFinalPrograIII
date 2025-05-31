package org.prograIII.ProyectoFinalPrograIII.Database.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.prograIII.ProyectoFinalPrograIII.Database.Entities.TurnoEntity;

import java.util.List;

public interface TurnoRepository extends JpaRepository<TurnoEntity, Integer> {
    List<TurnoEntity> findByEstadoOrderByIdAsc(String estado);
}
