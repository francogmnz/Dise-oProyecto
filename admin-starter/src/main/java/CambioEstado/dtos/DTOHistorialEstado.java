package CambioEstado.dtos;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class DTOHistorialEstado {
    private String nombreEstadoTramite;
    private Timestamp fechaDesdeTET;
    private Timestamp fechaHastaTET;
    private int contadorTET;

    public int getContadorTET() {
        return contadorTET;
    }

    public void setContadorTET(int contadorTET) {
        this.contadorTET = contadorTET;
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
