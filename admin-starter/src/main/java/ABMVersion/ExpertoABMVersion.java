package ABMVersion;

import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.DTODatosVersionIn;
import ABMVersion.dtos.DTOEstadoDestinoIN;
import ABMVersion.dtos.DTOEstadoOrigenIN;
import ABMVersion.dtos.DTOVersionM;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMVersion {

    // Método para buscar versiones
    public List<VersionDTO> buscarVersion(int nroVersion, int codTipoTramite, String nombreTipoTramite) {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> primerCriterio = new ArrayList<>();

        if (nroVersion > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("nroVersion");
            criterio1.setOperacion("=");
            criterio1.setValor(nroVersion);
            primerCriterio.add(criterio1);
        }

        if (codTipoTramite > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("codTipoTramite");
            criterio2.setOperacion("=");
            criterio2.setValor(codTipoTramite);
            primerCriterio.add(criterio2);
        }

        if (nombreTipoTramite != null && !nombreTipoTramite.trim().isEmpty()) {
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("nombreTipoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(nombreTipoTramite);
            primerCriterio.add(criterio3);
        }

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("Version", primerCriterio);
        List<VersionDTO> versionResultado = new ArrayList<>();

        for (Object x : objetoList) {
            if (x instanceof Version) {
                Version version = (Version) x;
                VersionDTO versionDTO = new VersionDTO();
                versionDTO.setNroVersion(version.getNroVersion());
                versionDTO.setDescripcionVersion(version.getDescripcionVersion());
                versionDTO.setFechaDesdeVersion(version.getFechaDesdeVersion());
                versionDTO.setFechaHastaVersion(version.getFechaHastaVersion());
                versionDTO.setFechaBajaVersion(version.getFechaBajaVersion());
                versionResultado.add(versionDTO);
            }
        }
        FachadaPersistencia.getInstance().finalizarTransaccion();
        return versionResultado;
    }

    public void modificarVersion(DTOVersionM dtoVersionM) throws VersionException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            // Buscar el mayor número de versión
            List<Object> todasVersiones = FachadaPersistencia.getInstance().buscar("Version", null);
            int maxNroVersion = 0;

            // Iterar para obtener el número de versión más alto
            for (Object obj : todasVersiones) {
                Version versionExistente = (Version) obj;
                if (versionExistente.getNroVersion() > maxNroVersion) {
                    maxNroVersion = versionExistente.getNroVersion();
                }
            }

            // Incrementar el número de versión
            dtoVersionM.setNroVersion(maxNroVersion + 1);

            // Crear nueva versión
            Version nuevaVersion = new Version();
            nuevaVersion.setNroVersion(dtoVersionM.getNroVersion());
            nuevaVersion.setDescripcionVersion(dtoVersionM.getDescripcionVersion());
            nuevaVersion.setFechaDesdeVersion(new Timestamp(dtoVersionM.getFechaDesdeVersion().getTime()));
            nuevaVersion.setFechaHastaVersion(new Timestamp(dtoVersionM.getFechaHastaVersion().getTime()));

            // Guardar la nueva versión
            FachadaPersistencia.getInstance().guardar(nuevaVersion);

            // Finalizar transacción
            FachadaPersistencia.getInstance().finalizarTransaccion();

        } catch (Exception e) {

        }
    }

    // Método para buscar una versión para modificar
    public ModificarVersionDTO buscarVersionAModificar(int nroVersion) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(nroVersion);

        criterioList.add(dto);

        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            return null; // Manejo adecuado si no se encuentra la versión
        }

        Version versionEncontrada = (Version) lVersion.get(0);

        ModificarVersionDTO modificarVersionDTO = new ModificarVersionDTO();
        modificarVersionDTO.setDescripcionVersion(versionEncontrada.getDescripcionVersion());
        //modificarVersionDTO.setFechaDesdeVersion(versionEncontrada.getFechaDesdeVersion());
        // modificarVersionDTO.setFechaHastaVersion(versionEncontrada.getFechaHastaVersion());

        return modificarVersionDTO;
    }

    // Método para modificar una versión
    public void modificarVersion(ModificarVersionDTO modificarVersionDTO) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(modificarVersionDTO.getNumVersion());

        criterioList.add(dto);

        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            // Manejo adecuado si no se encuentra la versión
            FachadaPersistencia.getInstance().finalizarTransaccion();
            return;
        }

        Version versionEncontrada = (Version) lVersion.get(0);

        versionEncontrada.setDescripcionVersion(modificarVersionDTO.getDescripcionVersion());
        versionEncontrada.setFechaDesdeVersion(modificarVersionDTO.getFechaDesdeVersion());
        versionEncontrada.setFechaHastaVersion(modificarVersionDTO.getFechaHastaVersion());

        FachadaPersistencia.getInstance().guardar(versionEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    // Método para dar de baja una versión
    public void darDeBajaVersion(int nroVersion) throws VersionException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Crear el criterio de búsqueda para encontrar la versión
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(nroVersion);

        criterioList.add(dto);

        // Buscar la versión con el criterio dado
        List<Object> lVersion = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        if (lVersion.isEmpty()) {
            // Lanzar una excepción si la versión no se encuentra
            throw new VersionException("La versión no existe");
        }

        // Obtener la versión encontrada y actualizar la fecha de baja
        Version versionEncontrada = (Version) lVersion.get(0);
        versionEncontrada.setFechaBajaVersion(new Timestamp(System.currentTimeMillis()));

        // Guardar los cambios y finalizar la transacción
        FachadaPersistencia.getInstance().guardar(versionEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    // En ExpertoABMVersion.java

    public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaHoraBajaEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
        List<EstadoTramite> estadosTramiteActivos = new ArrayList<>();

        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            estadosTramiteActivos.add(estadoTramite);
        }

        return estadosTramiteActivos;
    }

    public List<TipoTramite> obtenerTiposTramitesActivos() {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaHoraBajaTipoTramite");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
        List<TipoTramite> tiposTramitesActivos = new ArrayList<>();

        for (Object x : objetoList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            tiposTramitesActivos.add(tipoTramite);
        }

        return tiposTramitesActivos;
    }

    public ModificarVersionDTO obtenerVersionPorNumero(int nroVersion) {
        // Criterios de búsqueda para encontrar la versión por número
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        // Establece el atributo y el valor para la búsqueda
        dto.setAtributo("nroVersion");
        dto.setOperacion("=");
        dto.setValor(nroVersion);
        criterioList.add(dto);

        // Realiza la búsqueda en la base de datos usando FachadaPersistencia
        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("Version", criterioList);
        ModificarVersionDTO modificarVersionDTO = null;

        // Si se encuentra una versión, la convertimos a ModificarVersionDTO
        if (!objetoList.isEmpty()) {
            // Suponiendo que el primer resultado es el que buscamos
            Object versionObject = objetoList.get(0);
            modificarVersionDTO = (ModificarVersionDTO) versionObject;
        }

        return modificarVersionDTO; // Retorna el objeto encontrado o null si no se encontró
    }

public boolean confirmacion(DTODatosVersionIn dtoDatosVersion) {
    
    FachadaPersistencia f = FachadaPersistencia.getInstance();
    Version nueva = new Version();
    // Buscar si ya existe una versión
    List<DTOCriterio> criterioList = new ArrayList<>();
    DTOCriterio criterio = new DTOCriterio();
    criterio.setAtributo("descripcionVersion"); // O cualquier otro campo que identifique la versión
    criterio.setOperacion("=");
    criterio.setValor(dtoDatosVersion.getDescripcionVersion());
    criterioList.add(criterio);

    List<Object> versionesExistentes = f.buscar("Version", criterioList);

    if (!versionesExistentes.isEmpty()) {
    // Establecer la versión de forma autoincremental
    int nuevoNumeroVersion = obtenerUltimoNumeroVersion(f) + 1; // Asegúrate de tener este método
    nueva.setNroVersion(nuevoNumeroVersion);
    
    // Configurar los demás atributos de la versión
    nueva.setDescripcionVersion(dtoDatosVersion.getDescripcionVersion());
    nueva.setFechaDesdeVersion(new Timestamp(dtoDatosVersion.getFechaDesdeVersion().getTime()));
    nueva.setFechaHastaVersion(new Timestamp(dtoDatosVersion.getFechaHastaVersion().getTime()));
    int codTipoTramite = dtoDatosVersion.getCodTipoTramite();

    // Búsqueda del TipoTramite
    List<DTOCriterio> lc = new ArrayList<>();
    DTOCriterio c = new DTOCriterio();
    c.setAtributo("codTipoTramite");
    c.setOperacion("=");
    c.setValor(codTipoTramite);
    lc.add(c);

    List<Object> o = f.buscar("TipoTramite", lc);
    
    if (!o.isEmpty()) {
        TipoTramite t = (TipoTramite) o.get(0);
        nueva.setTipoTramite(t);
    } else {
        System.out.println("TipoTramite no encontrado.");
        return false;
    }

    try {
        // Procesar Estados de Origen
        for (DTOEstadoOrigenIN dtoO : dtoDatosVersion.getDtoEstadoOrigenList()) {
            // Búsqueda del Estado de Origen
            List<DTOCriterio> lcEstado = new ArrayList<>();
            DTOCriterio criterioEstado = new DTOCriterio();
            criterioEstado.setAtributo("codEstadoTramite");
            criterioEstado.setOperacion("=");
            criterioEstado.setValor(dtoO.getCodEstadoTramite());
            lcEstado.add(criterioEstado);

            List<Object> resultadoEstado = f.buscar("EstadoTramite", lcEstado);
            if (!resultadoEstado.isEmpty()) {
                EstadoTramite eO = (EstadoTramite) resultadoEstado.get(0);
                System.out.println("Nombre Estado Origen: " + eO.getNombreEstadoTramite());

                ConfTipoTramiteEstadoTramite cttet = new ConfTipoTramiteEstadoTramite();
                cttet.setEtapaOrigen(codTipoTramite);
                cttet.setEstadoTramiteOrigen(eO);

                List<EstadoTramite> listaEstadoTramiteDestino = new ArrayList<>();
                for (DTOEstadoDestinoIN dtoD : dtoDatosVersion.getDtoEstadoDestinoList()) {
                    // Búsqueda del Estado de Destino
                    List<DTOCriterio> lcDestino = new ArrayList<>();
                    DTOCriterio criterioDestino = new DTOCriterio();
                    criterioDestino.setAtributo("codEstadoTramite");
                    criterioDestino.setOperacion("=");
                    criterioDestino.setValor(dtoD.getCodEstadoTramite());
                    lcDestino.add(criterioDestino);

                    List<Object> resultadoDestino = f.buscar("EstadoTramite", lcDestino);
                    if (!resultadoDestino.isEmpty()) {
                        EstadoTramite eD = (EstadoTramite) resultadoDestino.get(0);
                        System.out.println("Nombre Estado Destino: " + eD.getNombreEstadoTramite());
                        listaEstadoTramiteDestino.add(eD);
                    } else {
                        System.out.println("Estado de Destino no encontrado: " + dtoD.getCodEstadoTramite());
                    }
                }

                cttet.setEstadoTramiteDestino(listaEstadoTramiteDestino);
                nueva.addConfTipoTramiteEstadoTramite(cttet);
                
                // Guardar cada configuración de estado
                f.guardar(cttet);
            } else {
                System.out.println("Estado de Origen no encontrado: " + dtoO.getCodEstadoTramite());
            }
        }

        // Guardar la versión
        f.guardar(nueva);
        f.finalizarTransaccion();
        
        System.out.println("Versión guardada correctamente.");
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

private int obtenerUltimoNumeroVersion(FachadaPersistencia f) {
    // Método para obtener el último número de versión
    List<DTOCriterio> lc = new ArrayList<>();
    DTOCriterio c = new DTOCriterio();
    c.setAtributo("nroVersion");
    c.setOperacion("ORDER BY");
    lc.add(c);

    List<Object> versiones = f.buscar("Version", lc);
    if (!versiones.isEmpty()) {
        Version ultimaVersion = (Version) versiones.get(0);
        return ultimaVersion.getNroVersion();
    }
    return 0; // Si no hay versiones, retornar 0
}

}



