package ABMVersion;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.ModificarVersionDTOIn;
import ABMVersion.dtos.NuevaVersionDTO;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import java.util.List;

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
    public void agregarVersion(NuevaVersionDTO nuevaVersionDTO) throws VersionException {
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
}
