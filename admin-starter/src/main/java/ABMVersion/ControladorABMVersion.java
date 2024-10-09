package ABMVersion;

import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.Version;
import java.util.List;
import org.omnifaces.util.Messages;

public class ControladorABMVersion {

    private ExpertoABMVersion expertoABMVersion = new ExpertoABMVersion();

    public int obtenerUltimoNumeroVersion(int codTipoTramite) {
        return expertoABMVersion.obtenerUltimoNumeroVersion(codTipoTramite);
    }

    public void anularVersion(int nroVersion, int codTipoTramite) {
        try {
            expertoABMVersion.anularVersion(nroVersion, codTipoTramite);
        } catch (VersionException e) {
            // Manejo de la excepción, por ejemplo, mostrar un mensaje de error
            Messages.addGlobalError("Error al anular la versión: " + e.getMessage());
        }
    }

    public DTOVersionM modificarVersion(int codTipoTramite) {

        return expertoABMVersion.modificarVersion(codTipoTramite);
    }

    // Confirmar datos de versión
    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion) throws VersionException {
    return expertoABMVersion.confirmacion(dtoDatosVersion);
}


    public List<DTOTipoTramiteVersion> mostrarVersion() {
        return expertoABMVersion.mostrarVersion();
    }

}
