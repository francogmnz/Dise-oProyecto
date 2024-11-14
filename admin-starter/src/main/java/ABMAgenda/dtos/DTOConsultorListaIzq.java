
package ABMAgenda.dtos;

public class DTOConsultorListaIzq {
    
    private int legajoConsultor;
    private String nombreConsultor;
    private int numMaximoTramites;
    private int tramAct;
    
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
    
    public int NumMaximoTramites() {
        return numMaximoTramites;
    }

    public void NumMaximoTramites(int nroMaximoTramites) {
        this.numMaximoTramites = nroMaximoTramites;
    }

    public int getNumMaximoTramites() {
        return numMaximoTramites;
    }

    public void setNumMaximoTramites(int nroMaximoTramites) {
        this.numMaximoTramites = nroMaximoTramites;
    }

    public int getTramAct() {
        return tramAct;
    }

    public void setTramAct(int tramAct) {
        this.tramAct = tramAct;
    }
    
}
