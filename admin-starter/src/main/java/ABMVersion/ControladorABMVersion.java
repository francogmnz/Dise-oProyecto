package ABMVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import java.util.List;
import org.omnifaces.util.Messages;

public class ControladorABMVersion {
    
    private ExpertoABMVersion expertoABMVersion = new ExpertoABMVersion();
    public List<VersionDTO> buscarVersion(int nroVersion, String nombreTipoTramite, int codTipoTramite) {
        return expertoABMVersion.buscarVersion(nroVersion, codTipoTramite, nombreTipoTramite);
    }    

    public DTOVersionM buscarVersionAModificar(int nroVersion) {
        return (DTOVersionM) expertoABMVersion.buscarVersionAModificar(nroVersion);
    }
  public DTOVersionM modificar(int codTipoTramite) {
    try {
        return expertoABMVersion.modificar(codTipoTramite);
    } catch (Exception e) {
        Messages.addGlobalError("Error al modificar la versión: " + e.getMessage());
    }
    return null;
  }
    public void darDeBajaVersion(int nroVersion) throws VersionException {
        expertoABMVersion.darDeBajaVersion(nroVersion);
    }

    public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        return expertoABMVersion.obtenerEstadosTramiteActivos();
    }

    public List<TipoTramite> obtenerTiposTramitesActivos() {
        return expertoABMVersion.obtenerTiposTramitesActivos();
    }

    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion){
    return expertoABMVersion.confirmacion(dtoDatosVersion);
    
            } 
    
}
