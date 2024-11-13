package ABMVersion;

import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.DTOVersionH;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.exceptions.VersionException;
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
  /*public DTOVersionM verVersion(int codTipoTramite, int nroVersion) {
    
        return expertoABMVersion.VerVersion(codTipoTramite, nroVersion);
   
} */


    // Confirmar datos de versión
    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion) throws VersionException {
    return expertoABMVersion.confirmacion(dtoDatosVersion);
}


    public List<DTOTipoTramiteVersion> mostrarVersion(int codTipoTramite, String nombreTipoTramite) {
        return expertoABMVersion.mostrarVersion(codTipoTramite, nombreTipoTramite);
    }
    
    public DTOVersionH mostrarHistoricoVersion(int codTipoTramite) {
    try {
        // Llamar al experto para obtener todas las versiones del tipo de trámite
        return expertoABMVersion.mostrarHistoricoVersion(codTipoTramite);
    } catch (Exception e) {
        Messages.create("Error al recuperar el historial de versiones.").error().detail(e.getMessage()).add();
        return null;// Retornar  vacía en caso de error
    }
}


}
