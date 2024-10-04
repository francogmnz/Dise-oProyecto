package ABMVersion;

import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.FachadaPersistencia;

public class ControladorABMVersion {

    private ExpertoABMVersion expertoABMVersion = new ExpertoABMVersion();

    // Buscar versiones basadas en el número, nombre y código del tipo de trámite
    public List<VersionDTO> buscarVersion(int nroVersion, String nombreTipoTramite, int codTipoTramite) {
        return expertoABMVersion.buscarVersion(nroVersion, codTipoTramite, nombreTipoTramite);
    }

    public int obtenerUltimoNumeroVersion(int codTipoTramite) {
        return expertoABMVersion.obtenerUltimoNumeroVersion(codTipoTramite);
    }

   /* public Version obtenerUltimaVersion(int codTipoTramite) {
        return expertoABMVersion.obtenerUltimaVersion(codTipoTramite);
    } */

    // Dar de baja una versión
    public void darDeBajaVersion(int codTipoTramite) {
        try {
            expertoABMVersion.darDeBajaVersion(codTipoTramite);
            Messages.addGlobalInfo("La versión se ha dado de baja exitosamente.");
        } catch (VersionException e) {
            Messages.addGlobalError("Error al dar de baja la versión: " + e.getMessage());
        }
    }

    public DTOVersionM modificarVersion(int codTipoTramite) {

        return expertoABMVersion.modificarVersion(codTipoTramite);
    }

    // Confirmar datos de versión
    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion) {
        return expertoABMVersion.confirmacion(dtoDatosVersion);
    }
    

    public boolean estadoOrigenExiste(int codEstadoDestino, List<DTOEstadoOrigenIN> listaEstadosOrigen) {
        return expertoABMVersion.estadoOrigenExiste(codEstadoDestino, listaEstadosOrigen);
    }
public List<DTOTipoTramiteVersion> mostrarVersion() {
        return expertoABMVersion.mostrarVersion();
    }
}
