
package ABMConsultor.dtos;


import java.sql.Timestamp;

public class DTOConsultor {
    private int legajoConsultor;
    private String nombreConsultor;
    private int numMaximoTramites;
    private Timestamp fechaAltaConsultor;
    private Timestamp fechaBajaConsultor;

    public DTOConsultor() {
    }
    
    public int getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(int legajoConsultor) {
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

    public Timestamp getFechaAltaConsultor() {
        return fechaAltaConsultor;
    }

    public void setFechaAltaConsultor(Timestamp fechaAltaConsultor) {
        this.fechaAltaConsultor = fechaAltaConsultor;
    }

    public Timestamp getFechaBajaConsultor() {
        return fechaBajaConsultor;
    }

    public void setFechaBajaConsultor(Timestamp fechaBajaConsultor) {
        this.fechaBajaConsultor = fechaBajaConsultor;
    }


}

