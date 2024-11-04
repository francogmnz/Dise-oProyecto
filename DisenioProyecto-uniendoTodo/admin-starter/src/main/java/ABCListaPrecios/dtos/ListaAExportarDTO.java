
package ABCListaPrecios.dtos;


public class ListaAExportarDTO {
   private int codTipoTramite;
   private String nombreTipoTramite;
   private String descripcionTipoTramite;
   private double precioTipoTramite;

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    public double getPrecioTipoTramite() {
        return precioTipoTramite;
    }

    public void setPrecioTipoTramite(double precioTipoTramite) {
        this.precioTipoTramite = precioTipoTramite;
    }
  
  
}
