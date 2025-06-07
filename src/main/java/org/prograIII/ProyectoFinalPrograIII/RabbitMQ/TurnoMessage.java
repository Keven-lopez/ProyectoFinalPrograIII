package org.prograIII.ProyectoFinalPrograIII.RabbitMQ;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TurnoMessage {
    private Integer id;
    private Integer clienteId;
    private Integer servicioId;
    private LocalDateTime fechaCreacion;
    private String estado;
    private String action;

    public TurnoMessage() {}

    public TurnoMessage(Integer id, Integer clienteId, Integer servicioId,
                        LocalDateTime fechaCreacion, String estado, String action) {
        this.id = id;
        this.clienteId = clienteId;
        this.servicioId = servicioId;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.action = action;
    }

    @Override
    public String toString() {
        return "TurnoMessage{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", servicioId=" + servicioId +
                ", fechaCreacion=" + fechaCreacion +
                ", estado='" + estado + '\'' +
                ", action='" + action + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TurnoMessage that = (TurnoMessage) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (clienteId != null ? !clienteId.equals(that.clienteId) : that.clienteId != null) return false;
        if (servicioId != null ? !servicioId.equals(that.servicioId) : that.servicioId != null) return false;
        if (fechaCreacion != null ? !fechaCreacion.equals(that.fechaCreacion) : that.fechaCreacion != null) return false;
        if (estado != null ? !estado.equals(that.estado) : that.estado != null) return false;
        return action != null ? action.equals(that.action) : that.action == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clienteId != null ? clienteId.hashCode() : 0);
        result = 31 * result + (servicioId != null ? servicioId.hashCode() : 0);
        result = 31 * result + (fechaCreacion != null ? fechaCreacion.hashCode() : 0);
        result = 31 * result + (estado != null ? estado.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        return result;
    }
}
