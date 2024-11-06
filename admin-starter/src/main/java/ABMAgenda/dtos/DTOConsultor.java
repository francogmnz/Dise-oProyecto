
package ABMAgenda.dtos;

public class DTOConsultor {
    
    private int legajoConsultor;
    private String nombreConsultor;
    private int nroMaximoTramites;
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
    
    public int NroMaximoTramites() {
        return nroMaximoTramites;
    }

    public void NroMaximoTramites(int nroMaximoTramites) {
        this.nroMaximoTramites = nroMaximoTramites;
    }
    public int getTramAct() {
        return tramAct;
    }

    public void setTramAct(int tramAct) {
        this.tramAct = tramAct;
    }
}
