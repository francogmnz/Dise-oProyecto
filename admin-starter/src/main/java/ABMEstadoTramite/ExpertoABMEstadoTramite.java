package ABMEstadoTramite;

import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTOIn;
import ABMEstadoTramite.dtos.NuevoEstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.fechaHoraActual;
import utils.FachadaPersistencia;

/**
 *
 * @author matis
 */
public class ExpertoABMEstadoTramite {

    public List<EstadoTramiteDTO> buscarEstadosTramite(int codEstadoTramite, String nombreEstadoTramite, String descripcionEstadoTramite) {

        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        if (codEstadoTramite > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codEstadoTramite");
            criterio1.setOperacion("=");
            criterio1.setValor(codEstadoTramite);
            lCriterio.add(criterio1);
        }

        if (nombreEstadoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombreEstadoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(nombreEstadoTramite);
            lCriterio.add(criterio2);
        }

        if (descripcionEstadoTramite.trim().length() > 0) {
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("descripcionEstadoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(descripcionEstadoTramite);
            lCriterio.add(criterio3);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", lCriterio);
        List<EstadoTramiteDTO> estadoTramiteResultados = new ArrayList<>();

        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            EstadoTramiteDTO estadoTramiteDTO = new EstadoTramiteDTO();
            estadoTramiteDTO.setCodEstadoTramite(estadoTramite.getCodEstadoTramite());
            estadoTramiteDTO.setNombreEstadoTramite(estadoTramite.getNombreEstadoTramite());
            estadoTramiteDTO.setDescripcionEstadoTramite(estadoTramite.getDescripcionEstadoTramite());
            estadoTramiteDTO.setFechaHoraBajaEstadoTramite(estadoTramite.getFechaHoraBajaEstadoTramite());
            estadoTramiteDTO.setFechaHoraAltaEstadoTramite(estadoTramite.getFechaHoraAltaEstadoTramite());
            estadoTramiteResultados.add(estadoTramiteDTO);
        }

        return estadoTramiteResultados;

    }

    public void agregarEstadoTramite(NuevoEstadoTramiteDTO nuevoEstadoTramiteDTO) throws EstadoTramiteException {

        validarEstadoTramiteA(nuevoEstadoTramiteDTO);
        
        FachadaPersistencia.getInstance().iniciarTransaccion();

        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setCodEstadoTramite(nuevoEstadoTramiteDTO.getCodEstadoTramite());
        estadoTramite.setNombreEstadoTramite(nuevoEstadoTramiteDTO.getNombreEstadoTramite());
        estadoTramite.setDescripcionEstadoTramite(nuevoEstadoTramiteDTO.getDescripcionEstadoTramite());
        estadoTramite.setFechaHoraAltaEstadoTramite(fechaHoraActual.obtenerFechaHoraActual());

        FachadaPersistencia.getInstance().guardar(estadoTramite);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public ModificarEstadoTramiteDTO buscarEstadoTramiteAModificar(int codEstadoTramite) throws EstadoTramiteException {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codEstadoTramite);

        criterioList.add(dto);

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) objetoList.get(0);

        ModificarEstadoTramiteDTO modificarEstadoTramiteDTO = new ModificarEstadoTramiteDTO();
        modificarEstadoTramiteDTO.setNombreEstadoTramite(estadoTramiteEncontrado.getNombreEstadoTramite());
        modificarEstadoTramiteDTO.setCodEstadoTramite(estadoTramiteEncontrado.getCodEstadoTramite());
        modificarEstadoTramiteDTO.setDescripcionEstadoTramite(estadoTramiteEncontrado.getDescripcionEstadoTramite());

        return modificarEstadoTramiteDTO;
    }

    public void modificarEstadoTramite(ModificarEstadoTramiteDTOIn modificarEstadoTramiteDTOIn) throws EstadoTramiteException {

        validarDocumentacionM(modificarEstadoTramiteDTOIn);
        FachadaPersistencia.getInstance().iniciarTransaccion();

        int codEstadoTramite = modificarEstadoTramiteDTOIn.getCodEstadoTramite();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        // Buscar el EstadoTramite por su código
        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codEstadoTramite);
        criterioList.add(dto);

        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        criterioList.clear();

        // Buscar versiones activas (fecha desde menor a ahora y sin fecha de baja)
        DTOCriterio dto2 = new DTOCriterio();
        dto2.setAtributo("fechaHastaVersion");
        dto2.setOperacion(">=");
        dto2.setValor(fechaHoraActual.obtenerFechaHoraActual());
        criterioList.add(dto2);

        DTOCriterio dto4 = new DTOCriterio();
        dto4.setAtributo("fechaBajaVersion");
        dto4.setOperacion("=");
        dto4.setValor(null);
        criterioList.add(dto4);

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        // Verificar en cada versión si el EstadoTramite está presente en origen o destino
        for (Object o : objetoList) {
            Version version = (Version) o;
            List<ConfTipoTramiteEstadoTramite> confTTETList = version.getConfTipoTramiteEstadoTramite();

            for (ConfTipoTramiteEstadoTramite confTTET : confTTETList) {
                verificarEstadoTramiteEnOrigenODestinoM(confTTET.getEstadoTramiteOrigen(), confTTET.getEstadoTramiteDestino(), codEstadoTramite);
            }
        }

        if (estadoTramiteEncontrado.getNombreEstadoTramite() == "Iniciado") {
            throw new EstadoTramiteException("No se puede modificar el Estado Iniciado");
        }

        // Modificar el EstadoTramite si no hay problema
        estadoTramiteEncontrado.setCodEstadoTramite(modificarEstadoTramiteDTOIn.getCodEstadoTramite());
        estadoTramiteEncontrado.setNombreEstadoTramite(modificarEstadoTramiteDTOIn.getNombreEstadoTramite());
        estadoTramiteEncontrado.setDescripcionEstadoTramite(modificarEstadoTramiteDTOIn.getDescripcionEstadoTramite());

        // Guardar los cambios
        FachadaPersistencia.getInstance().guardar(estadoTramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public void darDeBajaEstadoTramite(int codEstadoTramite) throws EstadoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codEstadoTramite);

        criterioList.add(dto);

        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        if (estadoTramiteEncontrado.getNombreEstadoTramite() == "Iniciado" || estadoTramiteEncontrado.getCodEstadoTramite() == 1) {
            throw new EstadoTramiteException("No se puede dar de baja el Estado Iniciado");
        }

        criterioList.clear();

        // Buscar versiones con fecha actual y sin fecha de baja
        DTOCriterio dto2 = new DTOCriterio();
        dto2.setAtributo("fechaHastaVersion");
        dto2.setOperacion(">=");
        dto2.setValor(fechaHoraActual.obtenerFechaHoraActual());

        criterioList.add(dto2);

        DTOCriterio dto3 = new DTOCriterio();
        dto3.setAtributo("fechaBajaVersion");
        dto3.setOperacion("=");
        dto3.setValor(null);

        criterioList.add(dto3);

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("Version", criterioList);

        // Verificar cada versión
        for (Object o : objetoList) {
            Version version = (Version) o;
            List<ConfTipoTramiteEstadoTramite> confTTETList = version.getConfTipoTramiteEstadoTramite();

            // Verificar si el EstadoTramite está en origen o en destino
            for (ConfTipoTramiteEstadoTramite confTTET : confTTETList) {
                verificarEstadoTramiteEnOrigenODestino(confTTET.getEstadoTramiteOrigen(), confTTET.getEstadoTramiteDestino(), codEstadoTramite);
            }
        }

        // Dar de baja el EstadoTramite
        estadoTramiteEncontrado.setFechaHoraBajaEstadoTramite(fechaHoraActual.obtenerFechaHoraActual());
        FachadaPersistencia.getInstance().guardar(estadoTramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    private void verificarEstadoTramiteEnOrigenODestino(EstadoTramite estadoTramiteOrigen, List<EstadoTramite> estadoTramiteDestino, int codEstadoTramite) throws EstadoTramiteException {
        // Verificar si el estado está en la lista de origen     
        if (estadoTramiteOrigen.getCodEstadoTramite() == codEstadoTramite) {
            throw new EstadoTramiteException("No se pudo eliminar el Estado porque está presente en una versión actual/posterior");
        }

        // Verificar si el estado está en la lista de destino
        for (EstadoTramite estado : estadoTramiteDestino) {
            if (estado.getCodEstadoTramite() == codEstadoTramite) {
                throw new EstadoTramiteException("No se pudo eliminar el Estado porque está presente en una versión actual/posterior");
            }
        }
    }

    private void verificarEstadoTramiteEnOrigenODestinoM(EstadoTramite estadoTramiteOrigen, List<EstadoTramite> estadoTramiteDestino, int codEstadoTramite) throws EstadoTramiteException {
        // Verificar si el estado está en la lista de origen
        if (estadoTramiteOrigen.getCodEstadoTramite() == codEstadoTramite) {
            throw new EstadoTramiteException("No se pudo modficar el Estado porque está presente en una versión actual/posterior");
        }

        // Verificar si el estado está en la lista de destino
        for (EstadoTramite estado : estadoTramiteDestino) {
            if (estado.getCodEstadoTramite() == codEstadoTramite) {
                throw new EstadoTramiteException("No se pudo modificar el Estado porque está presente en una versión actual/posterior");
            }
        }
    }

    private void validarEstadoTramiteA(NuevoEstadoTramiteDTO nuevoETDTO) throws EstadoTramiteException {

        if (nuevoETDTO.getCodEstadoTramite() <= 0) {
            throw new EstadoTramiteException("El codigo debe ser un entero mayor a cero.");
        }

        String nombreEstadoTramite = nuevoETDTO.getNombreEstadoTramite();
        if (nombreEstadoTramite == null || nombreEstadoTramite.trim().isEmpty() || nombreEstadoTramite.length() > 255) {
            throw new EstadoTramiteException("El nombre debe tener entre 1 y 255 caracteres.");
        }

        String descripcionET = nuevoETDTO.getDescripcionEstadoTramite();
        if (descripcionET == null || descripcionET.trim().isEmpty() || descripcionET.length() > 255) {
            throw new EstadoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }

        List<DTOCriterio> criterioCodET = new ArrayList<>();
        DTOCriterio dtoCodET = new DTOCriterio();
        dtoCodET.setAtributo("codEstadoTramite");
        dtoCodET.setOperacion("=");
        dtoCodET.setValor(nuevoETDTO.getCodEstadoTramite());
        criterioCodET.add(dtoCodET);

        List lETCod = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioCodET);
        if (!lETCod.isEmpty()) {
            throw new EstadoTramiteException("El codigo del Estado Tramite ya existe.");
        }

        List<DTOCriterio> criterioNombreET = new ArrayList<>();
        DTOCriterio dtoNombreET = new DTOCriterio();
        dtoNombreET.setAtributo("nombreEstadoTramite");
        dtoNombreET.setOperacion("=");
        dtoNombreET.setValor(nombreEstadoTramite);
        criterioNombreET.add(dtoNombreET);

        DTOCriterio dtoFechaBaja = new DTOCriterio();
        dtoFechaBaja.setAtributo("fechaHoraBajaEstadoTramite");
        dtoFechaBaja.setOperacion("=");
        dtoFechaBaja.setValor(null);
        criterioNombreET.add(dtoFechaBaja);

        List lETNombre = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioNombreET);
        if (!lETNombre.isEmpty()) {
            throw new EstadoTramiteException("El nombre del Estado Tramite ya existe.");
        }

        List<DTOCriterio> criterioDescripcionET = new ArrayList<>();
        DTOCriterio dtoDescripcionET = new DTOCriterio();
        dtoDescripcionET.setAtributo("descripcionEstadoTramite");
        dtoDescripcionET.setOperacion("=");
        dtoDescripcionET.setValor(descripcionET);
        criterioDescripcionET.add(dtoDescripcionET);

        DTOCriterio dtoFechaBajaDesc = new DTOCriterio();
        dtoFechaBajaDesc.setAtributo("fechaHoraBajaEstadoTramite");
        dtoFechaBajaDesc.setOperacion("=");
        dtoFechaBajaDesc.setValor(null);
        criterioNombreET.add(dtoFechaBajaDesc);

        List lDocumentacionDescripcion = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioDescripcionET);
        if (!lDocumentacionDescripcion.isEmpty()) {
            throw new EstadoTramiteException("La descripcion del Estado Tramite ya existe.");
        }

    }

    private void validarDocumentacionM(ModificarEstadoTramiteDTOIn estadoTramiteDTO) throws EstadoTramiteException {

        String nombreET = estadoTramiteDTO.getNombreEstadoTramite();
        if (nombreET == null || nombreET.trim().isEmpty() || nombreET.length() > 255) {
            throw new EstadoTramiteException("El nombre debe tener entre 1 y 255 caracteres.");
        }

        String descripcionET = estadoTramiteDTO.getDescripcionEstadoTramite();
        if (descripcionET == null || descripcionET.trim().isEmpty() || descripcionET.length() > 255) {
            throw new EstadoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }

        List<DTOCriterio> criterioNombreET = new ArrayList<>();
        DTOCriterio dtoNombreET = new DTOCriterio();
        dtoNombreET.setAtributo("nombreEstadoTramite");
        dtoNombreET.setOperacion("=");
        dtoNombreET.setValor(nombreET);
        criterioNombreET.add(dtoNombreET);

        DTOCriterio dtoCodET = new DTOCriterio();
        dtoCodET.setAtributo("codEstadoTramite");
        dtoCodET.setOperacion("<>");
        dtoCodET.setValor(estadoTramiteDTO.getCodEstadoTramite());
        criterioNombreET.add(dtoCodET);

        List lETNombre = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioNombreET);

        List<DTOCriterio> criterioDescripcionET = new ArrayList<>();
        DTOCriterio dtoDescripcionET = new DTOCriterio();
        dtoDescripcionET.setAtributo("descripcionEstadoTramite");
        dtoDescripcionET.setOperacion("=");
        dtoDescripcionET.setValor(descripcionET);
        criterioDescripcionET.add(dtoDescripcionET);

        DTOCriterio dtoDescET = new DTOCriterio();
        dtoDescET.setAtributo("codEstadoTramite");
        dtoDescET.setOperacion("<>");
        dtoDescET.setValor(estadoTramiteDTO.getCodEstadoTramite());
        criterioDescripcionET.add(dtoDescET);

        List lETDescripcion = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioDescripcionET);

        if (!lETDescripcion.isEmpty() && !lETNombre.isEmpty()) {
            throw new EstadoTramiteException("El nombre y la descripcion del EstadoTramite ya existe.");
        }

        if (!lETDescripcion.isEmpty()) {
            throw new EstadoTramiteException("La descripcion del EstadoTramite ya existe.");
        }

        if (!lETNombre.isEmpty()) {
            throw new EstadoTramiteException("El nombre del Estado Tramite ya existe.");
        }

    }
}
