package org.prograIII.ProyectoFinalPrograIII;

import org.prograIII.ProyectoFinalPrograIII.Database.Entities.TurnoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import umg.principal.Models.Historial;
import umg.principal.Models.Turno;
import org.prograIII.ProyectoFinalPrograIII.Services.TurnoService;
import java.time.LocalDateTime;
import org.prograIII.ProyectoFinalPrograIII.Services.HistorialService;

@SpringBootApplication
public class ProyectoFinalPrograIiiApplication implements CommandLineRunner {

	@Autowired
	TurnoService turnoService;

	@Autowired
	HistorialService historialService;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalPrograIiiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		turnoService.loadFromDB();
		historialService.loadFromDB();
	}

}
