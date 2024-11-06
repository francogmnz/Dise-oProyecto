package CambioEstado.dtos;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DTOHistorialEstado {
    private String nombreEstadoTramite;
    private Timestamp fechaDesdeTET;
    private Timestamp fechaHastaTET;
    private int contador;

    public int getContador() {
        return contador;
    }

    // Getters y Setters
    public void setContador(int contador) {
        this.contador = contador;
    }

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public Timestamp getFechaDesdeTET() {
        return fechaDesdeTET;
    }

    public void setFechaDesdeTET(Timestamp fechaDesdeTET) {
        this.fechaDesdeTET = fechaDesdeTET;
    }

    public Timestamp getFechaHastaTET() {
        return fechaHastaTET;
    }

    public void setFechaHastaTET(Timestamp fechaHastaTET) {
        this.fechaHastaTET = fechaHastaTET;
    }


}
