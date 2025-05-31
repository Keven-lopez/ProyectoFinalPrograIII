package org.prograIII.ProyectoFinalPrograIII.API.Requests;

import lombok.Data;

@Data
public class CreateTurnoRequest {
    // Getters and setters
    private int clienteId;
    private int servicioId;

    // Constructors
    public CreateTurnoRequest() {}

    public CreateTurnoRequest(int clienteId, int servicioId) {
        this.clienteId = clienteId;
        this.servicioId = servicioId;
    }

}
