package org.prograIII.ProyectoFinalPrograIII;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.prograIII.ProyectoFinalPrograIII.Database.Repositories.TurnoRepository;
import org.prograIII.ProyectoFinalPrograIII.Services.TurnoService;
import org.prograIII.ProyectoFinalPrograIII.Database.Entities.TurnoEntity;
import org.springframework.transaction.annotation.Transactional;
import umg.principal.Models.Turno;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class ProyectoFinalPrograIiiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TurnoService turnoService;

	@Autowired
	private TurnoRepository turnoRepository;

	@Test
	public void testQueueTurno_ShouldInsertAsPendiente() {

		Turno turno = new Turno();
		turno.setId(1);  // Unique ID for the Turno
		turno.setClienteId(1);  // Example client ID
		turno.setServicioId(2); // Example service ID
		turno.setFechaCreacion(LocalDateTime.now());
		turno.setEstado("PENDIENTE");// Creation date

		// Act: Call the queueTurno method to add the Turno to the queue
		turnoService.queueTurno(turno);

		// Assert: Verify that the TurnoEntity is saved with state "PENDIENTE"
		TurnoEntity turnoEntity = turnoRepository.findById(turno.getId()).orElse(null);
		assertNotNull(turnoEntity, "TurnoEntity should not be null after saving");
		assertEquals("PENDIENTE", turnoEntity.getEstado(), "Estado should be PENDIENTE after enqueuing");
	}

	@Test
	public void testAttendTurno_ShouldUpdateToAtendido() {
		Turno turno = new Turno();
		turno.setId(1);  // Unique ID for the Turno
		turno.setFechaCreacion(LocalDateTime.now());
		turno.setEstado("PENDIENTE");// Creation date
		turno.setClienteId(1);  // Example client ID
		turno.setServicioId(2); // Example service ID

		// Arrange: Queue the Turno first to have it in the "PENDIENTE" state
		turnoService.queueTurno(turno);

		// Act: Now attend the Turno, which should change its state to "ATENDIDO"
		turnoService.processNextTurno(); // This should change the estado to ATENDIDO

		// Assert: Verify that the TurnoEntity's state was updated to ATENDIDO
		TurnoEntity turnoEntity = turnoRepository.findById(turno.getId()).orElse(null);
		assertNotNull(turnoEntity, "TurnoEntity should not be null after attending");
		assertEquals("ATENDIDO", turnoEntity.getEstado(), "Estado should be ATENDIDO after attending");
	}


}
