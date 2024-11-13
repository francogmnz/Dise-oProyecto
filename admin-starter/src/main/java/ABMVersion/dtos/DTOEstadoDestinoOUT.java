
package ABMVersion.dtos;

/**
 *
 * @author Franco
 */
public class DTOEstadoDestinoOUT {

    int codEstadoTramite;
    private String nombreEstadoDestino;

    public String getNombreEstadoDestino() {
        return nombreEstadoDestino;
    }

    public void setNombreEstadoDestino(String nombreEstadoDestino) {
        this.nombreEstadoDestino = nombreEstadoDestino;
    }

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }
}
