package ABMConsultor.beans;

import java.sql.Timestamp;

public class ConsultorGrillaUI {

    private String legajoConsultor;
    private String nombreConsultor;
    private int numMaximoTramites;
    private Timestamp fechaHoraBajaConsultor;

    public String getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(String legajoConsultor) {
        this.legajoConsultor = legajoConsultor;
    }

    public String getNombreConsultor() {
        return nombreConsultor;
    }

    public void setNombreConsultor(String nombreConsultor) {
        this.nombreConsultor = nombreConsultor;
    }

    public int getNumMaximoTramites() {
        return numMaximoTramites;
    }

    public void setNumMaximoTramites(int numMaximoTramites) {
        this.numMaximoTramites = numMaximoTramites;
    }

    public Timestamp getFechaHoraBajaConsultor() {
        return fechaHoraBajaConsultor;
    }

    public void setFechaHoraBajaConsultor(Timestamp fechaHoraBajaConsultor) {
        this.fechaHoraBajaConsultor = fechaHoraBajaConsultor;
    }

}
