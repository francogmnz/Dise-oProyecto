package CambioEstado.dtos;

import java.time.LocalDateTime;

public class CambioEstadoDTO {
    private int idTramite;
    private String estadoAnterior;
    private String nuevoEstado;
    private String descripcion;
    private LocalDateTime fechaCambio;

    // Constructor
    public CambioEstadoDTO(int idTramite, String nuevoEstado, String descripcion) {
        this.idTramite = idTramite;
        this.nuevoEstado = nuevoEstado;
        this.descripcion = descripcion;
        this.fechaCambio = LocalDateTime.now(); // Se establece la fecha y hora actual
    }

    // Getters y Setters
    public int getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(int idTramite) {
        this.idTramite = idTramite;
    }

    public String getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(String estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }
}
