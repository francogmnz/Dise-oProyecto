package CambioEstado.beans;


import CambioEstado.dtos.DTOHistorialEstado;
import java.sql.Timestamp;
import java.util.List;


public class CambioEstadoHistoricoGrillaUI {
    private String nombreEstadoTramite;
    private int nroTramite;
    private Timestamp fechaDesdeTET;
    private Timestamp fechaHastaTET;
    private int contadorTET;
    List<DTOHistorialEstado> mostrarHistorial;
    


    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
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

    public int getContadorTET() {
        return contadorTET;
    }

    public void setContadorTET(int contadorTET) {
        this.contadorTET = contadorTET;
    }

    

    public List<DTOHistorialEstado> getMostrarHistorial() {
        return mostrarHistorial;
    }

    public void setMostrarHistorial(List<DTOHistorialEstado> mostrarHistorial) {
        this.mostrarHistorial = mostrarHistorial;
    }
    
   

}
    