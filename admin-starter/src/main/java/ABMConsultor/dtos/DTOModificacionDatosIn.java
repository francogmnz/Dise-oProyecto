
package ABMConsultor.dtos;

public class DTOModificacionDatosIn {
    
   private int legajoConsultor;
   private int legajoOriginal;
    private String nombreConsultor;
    private int numMaximoTramites;

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

    public int getLegajoOriginal() {
        return legajoOriginal;
    }

    public void setLegajoOriginal(int legajoOriginal) {
        this.legajoOriginal = legajoOriginal;
    }
    
}

