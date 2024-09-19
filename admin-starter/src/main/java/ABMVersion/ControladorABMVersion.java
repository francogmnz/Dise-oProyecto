package ABMVersion;
import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.ModificarVersionDTOIn;
import ABMVersion.dtos.DTODatosVersion;
import ABMVersion.dtos.HistorialVersionDTO;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import java.util.List;
import utils.FachadaPersistencia;

/**
 * Controlador para la gestión de versiones.
 * Autor: Franco
 */
public class ControladorABMVersion {
    
    private ExpertoABMVersion expertoABMVersion = new ExpertoABMVersion();

    // Método para buscar versiones
    public List<VersionDTO> buscarVersion(int nroVersion, String nombreTipoTramite, int codTipoTramite) {
        return expertoABMVersion.buscarVersion(nroVersion, codTipoTramite, nombreTipoTramite);
    }    

    // Método para agregar una nueva versión
    public void agregarVersion(DTODatosVersion nuevaVersionDTO) throws VersionException {
        expertoABMVersion.agregarVersion(nuevaVersionDTO);
    }

    // Método para buscar una versión para modificar
    public ModificarVersionDTO buscarVersionAModificar(int nroVersion) {
        return (ModificarVersionDTO) expertoABMVersion.buscarVersionAModificar(nroVersion);
    }

    // Método para modificar una versión
    public void modificarVersion(ModificarVersionDTOIn modificarVersionDTOIn) {
        expertoABMVersion.modificarVersion(modificarVersionDTOIn);
    }

    // Método para dar de baja una versión
    public void darDeBajaVersion(int nroVersion) throws VersionException {
        expertoABMVersion.darDeBajaVersion(nroVersion);
    }
    public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        return expertoABMVersion.obtenerEstadosTramiteActivos();
    }
    public List<TipoTramite> obtenerTiposTramitesActivos() {
        return expertoABMVersion.obtenerTiposTramitesActivos();
    }
  //  public List<HistorialVersionDTO> obtenerHistorialVersion(int nroVersion) {
    //    expertoABMVersion.obtenerHistorialVersion(nroVersion);


}
