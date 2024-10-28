package CambioEstado.dtos;

import java.time.LocalDateTime;

public class DTOHistorialEstado {
    private String nombreEstadoTramite;
    private LocalDateTime fechaDesdeTET;
    private LocalDateTime fechaHastaTET;

    // Getters y Setters

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public LocalDateTime getFechaDesdeTET() {
        return fechaDesdeTET;
    }

    public void setFechaDesdeTET(LocalDateTime fechaDesdeTET) {
        this.fechaDesdeTET = fechaDesdeTET;
    }

    public LocalDateTime getFechaHastaTET() {
        return fechaHastaTET;
    }

    public void setFechaHastaTET(LocalDateTime fechaHastaTET) {
        this.fechaHastaTET = fechaHastaTET;
    }
}
