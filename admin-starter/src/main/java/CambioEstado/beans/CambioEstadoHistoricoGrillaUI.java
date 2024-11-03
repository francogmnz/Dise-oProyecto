package CambioEstado.beans;


import CambioEstado.dtos.DTOMostrarHistorial;
import java.sql.Timestamp;
import java.util.List;


public class CambioEstadoHistoricoGrillaUI {
    private String nombreEstadoTramite;
    private int nroTramite;
    private Timestamp fechaDesdeTET;
    private Timestamp fechaHastaTET;
    private int contador;
    List<DTOMostrarHistorial> mostrarHistorial;
    


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

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public List<DTOMostrarHistorial> getMostrarHistorial() {
        return mostrarHistorial;
    }

    public void setMostrarHistorial(List<DTOMostrarHistorial> mostrarHistorial) {
        this.mostrarHistorial = mostrarHistorial;
    }
    
}
    