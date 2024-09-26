package ABMVersion;

import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMVersion.dtos.DTODatosVersion;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.HistorialVersionDTO;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import Version.beans.NodoIU;
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

    /**
     * Busca versiones según el número de versión y el tipo de trámite.
     *
     * @param nroVersion Número de versión a buscar.
     * @param nombreTipoTramite Nombre del tipo de trámite.
     * @param codTipoTramite Código del tipo de trámite.
     * @return Lista de VersionDTO que coinciden con los criterios de búsqueda.
     */
    public List<VersionDTO> buscarVersion(int nroVersion, String nombreTipoTramite, int codTipoTramite) {
        return expertoABMVersion.buscarVersion(nroVersion, codTipoTramite, nombreTipoTramite);
    }    

    /**
     * Agrega una nueva versión.
     *
     * @param nuevaVersionDTO Datos de la nueva versión.
     * @throws VersionException Si hay un error al agregar la versión.
     */
    public void modificarVersion(DTOVersionM dtoVersionM) throws VersionException {
        if (dtoVersionM == null) {
            throw new VersionException("Los datos de la nueva versión no pueden ser nulos.");
        }
        expertoABMVersion.modificarVersion(dtoVersionM);
    }

    /**
     * Busca una versión para modificar.
     *
     * @param nroVersion Número de la versión a modificar.
     * @return Objeto ModificarVersionDTO con los datos de la versión.
     */
    public ModificarVersionDTO buscarVersionAModificar(int nroVersion) {
        return (ModificarVersionDTO) expertoABMVersion.buscarVersionAModificar(nroVersion);
    }

    /**
     * Modifica una versión existente.
     *
     * @param modificarVersionDTOIn Datos para modificar la versión.
     */
    public void modificarVersion(ModificarVersionDTO modificarVersionDTO) {
        expertoABMVersion.modificarVersion(modificarVersionDTO);
    }

    /**
     * Da de baja una versión.
     *
     * @param nroVersion Número de la versión a dar de baja.
     * @throws VersionException Si hay un error al dar de baja la versión.
     */
    public void darDeBajaVersion(int nroVersion) throws VersionException {
        expertoABMVersion.darDeBajaVersion(nroVersion);
    }

    /**
     * Obtiene los estados de trámite activos.
     *
     * @return Lista de EstadoTramite activos.
     */
    public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        return expertoABMVersion.obtenerEstadosTramiteActivos();
    }

    /**
     * Obtiene los tipos de trámite activos.
     *
     * @return Lista de TipoTramite activos.
     */
    public List<TipoTramite> obtenerTiposTramitesActivos() {
        return expertoABMVersion.obtenerTiposTramitesActivos();
    }
    public ModificarVersionDTO obtenerVersionPorNumero(int nroVersion) {
    return (ModificarVersionDTO) expertoABMVersion.buscarVersionAModificar(nroVersion);
}
    public boolean confirmacion(DTODatosVersionIn dtoDatosVersion){
    return expertoABMVersion.confirmacion(dtoDatosVersion);
    
            } 
    
}
