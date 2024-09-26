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
        nueva.setNroVersion(1);//Cambiar despues
        int codTipoTramite = dtoDatosVersion.getCodTipoTramite();
        List<DTOCriterio> lc = new ArrayList<>();
        DTOCriterio c = new DTOCriterio();
        c.setAtributo("codTipoTramite");
        c.setOperacion("=");
        c.setValor(codTipoTramite);
        lc.add(c);
        List o = f.buscar("TipoTramite", lc);
        if (o.size() > 0) {
            TipoTramite t = (TipoTramite) o.get(0);
            nueva.setTipoTramite(t);
            System.out.println(t.getNombreTipoTramite());
        } else {
            //Hacer excepcion no existe el tipo tramite
            return false;
        }
        for (DTOEstadoOrigenIN dtoO : dtoDatosVersion.getDtoEstadoOrigenList()) {
            lc = new ArrayList<>();
            c = new DTOCriterio();
            c.setAtributo("codEstadoTramite");
            c.setOperacion("=");
            c.setValor(dtoO.getCodEstadoTramite());
            EstadoTramite eO = (EstadoTramite) f.buscar("EstadoTramite", lc).get(0);

            System.out.println("Nombre Estado" + eO.getNombreEstadoTramite());

            ConfTipoTramiteEstadoTramite cttet = new ConfTipoTramiteEstadoTramite();
            cttet.setEtapaOrigen(codTipoTramite);

            List<EstadoTramite> listaEstadoTramiteDestino = new ArrayList<>();
            // Crear un nuevo criterio de búsqueda basado en el código de estado trámite
            c.setAtributo("codEstadoTramite");
            c.setOperacion("=");
            c.setValor(dtoO.getDtoEstadoDeastinoList());
            EstadoTramite eDestino = (EstadoTramite) f.buscar("EstadoTramite", lc).get(0);

            System.out.println("Nombre Estado" + eDestino.getNombreEstadoTramite());

            for (DTOEstadoDestinoIN dtoD : dtoDatosVersion.getDtoEstadoDestinoList()) {
                lc = new ArrayList<>();
                c = new DTOCriterio();
                c.setAtributo("codEstadoTramite");
                c.setOperacion("=");
                c.setValor(dtoD.getCodEstadoTramite());

                EstadoTramite eD = (EstadoTramite) f.buscar("EstadoTramite", lc).get(0);

                System.out.println("Nombre Estado" + eD.getNombreEstadoTramite());

                listaEstadoTramiteDestino.add(eD);

                cttet.setEstadoTramiteDestino(listaEstadoTramiteDestino);

                f.guardar(cttet);

            }

            f.finalizarTransaccion();
        }
                return true;

    }
}
