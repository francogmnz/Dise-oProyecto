
package CambioEstado.beans;

import entidades.EstadoTramite;
import java.sql.Timestamp;

 
public class CambioEstadoGrillaUI {

    private int codConsultor;
    private int nroTramite;
    private Timestamp fechaInicioTramite;
    private Timestamp  fechaRecepcionTramite;

    public int getCodConsultor() {
        return codConsultor;
    }

    public void setCodConsultor(int codConsultor) {
        this.codConsultor = codConsultor;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codEstadoTramite) {
        this.codEstadoTramite = codEstadoTramite;
    }

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }
    private int codEstadoTramite;
    private String nombreEstadoTramite;
    
    
  
}