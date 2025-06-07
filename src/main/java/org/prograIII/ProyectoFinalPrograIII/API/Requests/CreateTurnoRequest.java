package org.prograIII.ProyectoFinalPrograIII.API.Requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateTurnoRequest {
    // Getters and setters
    @Schema(description = "ID of the client", example = "101")
    private int clienteId;
    @Schema(description = "ID of the service", example = "5")
    private int servicioId;

    // Constructors
    public CreateTurnoRequest() {}

    public CreateTurnoRequest(int clienteId, int servicioId) {
        this.clienteId = clienteId;
        this.servicioId = servicioId;
    }

}
