package ABMVersion;

import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.FachadaPersistencia;

public class ControladorABMVersion {
    
    private ExpertoABMVersion expertoABMVersion = new ExpertoABMVersion();

    // Buscar versiones basadas en el número, nombre y código del tipo de trámite
    public List<VersionDTO> buscarVersion(int nroVersion, String nombreTipoTramite, int codTipoTramite) {
        return expertoABMVersion.buscarVersion(nroVersion, codTipoTramite, nombreTipoTramite);
    }

   /* public void modificarVersion(int codTipoTramite, DTOVersionM dtoVersionm) throws VersionException {
       expertoABMVersion.modificarVersion(codTipoTramite, dtoVersionm);
    } */

    // Buscar una versión específica para modificar
    public DTOVersionM buscarVersionAModificar(int nroVersion) {
        return (DTOVersionM) expertoABMVersion.buscarVersionAModificar(nroVersion);
    }

    /*  public void modificarVersion(int codTipoTramite, DTOVersionM dtoVersionm) throws VersionException {
        expertoABMVersion.modificarVersion(codTipoTramite, dtoVersionm);
    }*/
    // Dar de baja una versión
    public void darDeBajaVersion(int nroVersion) {
        try {
            expertoABMVersion.darDeBajaVersion(nroVersion);
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
    
}
