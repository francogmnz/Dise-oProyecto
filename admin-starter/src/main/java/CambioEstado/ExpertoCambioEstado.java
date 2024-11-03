package CambioEstado;

import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOMostrarHistorial;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import com.google.gson.Gson;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.Consultor;
import entidades.EstadoTramite;
import entidades.Tramite;
import entidades.TramiteEstadoTramite;
import entidades.Version;
import java.sql.Timestamp;
import utils.FachadaPersistencia;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;

public class ExpertoCambioEstado {

    public List<DTOTramitesVigentes> buscarTramites(int legajoConsultor) {
        // Iniciar transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOTramitesVigentes> dtoTramitesVigentesList = new ArrayList<>();

        try {
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio dto = new DTOCriterio();
            dto.setAtributo("legajoConsultor");
            dto.setOperacion("=");
            dto.setValor(legajoConsultor);

            criterioList.add(dto);
            List<Object> lConsultor = FachadaPersistencia.getInstance().buscar("Consultor", criterioList);

            if (lConsultor.isEmpty()) {
                // Si no se encuentra el consultor, retornar null
                return null;
            }

            Consultor consultorEncontrado = (Consultor) lConsultor.get(0);
            criterioList.clear();

            // Crear criterio para buscar trámites del consultor
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("consultor");
            criterio1.setOperacion("=");
            criterio1.setValor(consultorEncontrado);

            criterioList.add(criterio1);

            // Criterio para buscar trámites vigentes (fechaFinTramite es null)
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("fechaFinTramite");
            criterio2.setOperacion("=");
            criterio2.setValor(null);

            criterioList.add(criterio2);

            // Buscar trámites del consultor
            List<Object> lTramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

            DTOTramitesVigentes dtoTramitesVigentes = new DTOTramitesVigentes();
            dtoTramitesVigentes.setCodConsultor(legajoConsultor); // Asignar código del consultor

            // Agregar cada trámite encontrado al DTOTramitesVigentes
            for (Object tramiteObj : lTramites) {
                Tramite tramite = (Tramite) tramiteObj;
                TramiteDTO dtoTramite = new TramiteDTO();
                dtoTramite.setNroTramite(tramite.getNroTramite());
                dtoTramite.setFechaInicioTramite(tramite.getFechaInicioTramite());
                dtoTramite.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite());
                dtoTramite.setEstadoTramite(tramite.getEstadoTramite());
                dtoTramite.setNombreEstadoTramite(tramite.getEstadoTramite().getNombreEstadoTramite());

                // Agregar el trámite al DTO
                dtoTramitesVigentes.addTramite(dtoTramite);
            }

            dtoTramitesVigentesList.add(dtoTramitesVigentes); // Agregar DTO a la lista final

            // Finalizar la transacción exitosamente
            FachadaPersistencia.getInstance().finalizarTransaccion();
        } catch (Exception e) {
            // En caso de error, revertir la transacción
            throw e; // Volver a lanzar la excepción
        }

        // Retornar la lista final de trámites vigentes
        return dtoTramitesVigentesList;
    }
// Método para guardar el trámite actualizado

    private void guardarTramite(TramiteDTO tramite) {
        FachadaPersistencia.getInstance().guardar(tramite);
    }

    public TramiteEstadoTramite cambiarEstado(int nroTramite) {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> criterioList = new ArrayList<>();

        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        try {
            // Buscar el trámite en la base de datos
            List<?> result = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
            Tramite tramite = result.stream()
                    .filter(obj -> obj instanceof Tramite)
                    .map(obj -> (Tramite) obj)
                    .findFirst()
                    .orElseThrow(() -> new CambioEstadoException("Trámite no encontrado."));

            if (nroTramite != tramite.getNroTramite()) {
                throw new CambioEstadoException("Trámite no encontrado, intente nuevamente.");
            }

            Timestamp fechaRecepcionTramite = tramite.getFechaRecepcionTramite();

            // Crear criterios para buscar la versión vigente
            criterioList.clear();
            DTOCriterio criterioFechaDesde = new DTOCriterio();
            criterioFechaDesde.setAtributo("fechaDesdeVersion");
            criterioFechaDesde.setOperacion("<=");
            criterioFechaDesde.setValor(fechaRecepcionTramite);
            criterioList.add(criterioFechaDesde);

            DTOCriterio criterioFechaHasta = new DTOCriterio();
            criterioFechaHasta.setAtributo("fechaHastaVersion");
            criterioFechaHasta.setOperacion(">");
            criterioFechaHasta.setValor(fechaRecepcionTramite);
            criterioList.add(criterioFechaHasta);

            DTOCriterio criterioFechaBaja = new DTOCriterio();
            criterioFechaBaja.setAtributo("fechaBajaVersion");
            criterioFechaBaja.setOperacion("=");
            criterioFechaBaja.setValor(null);
            criterioList.add(criterioFechaBaja);

            EstadoTramite estadoActual = tramite.getEstadoTramite();
            Version version = tramite.getVersion();
            
            //Gson gsonito = (Gson)version.getDibujo();
            
            List<ConfTipoTramiteEstadoTramite> confTTET = version.getConfTipoTramiteEstadoTramite();

            // Lógica para cambiar el estado
            EstadoTramite nuevoEstado = obtenerNuevoEstado(estadoActual, confTTET);
            if (nuevoEstado == null) {
                // Finalizar trámite si no hay más estados de destino
                tramite.setFechaFinTramite(new Timestamp(System.currentTimeMillis()));
                FachadaPersistencia.getInstance().guardar(tramite);
                FachadaPersistencia.getInstance().finalizarTransaccion();
                return null;
            }

            // Registrar el nuevo estado en TramiteEstadoTramite (TET)
            TramiteEstadoTramite nuevoTET = new TramiteEstadoTramite();
            nuevoTET.setEstadoTramite(nuevoEstado);
            nuevoTET.setFechaDesdeTET(new Timestamp(System.currentTimeMillis()));
            nuevoTET.setContadorTET(obtenerContadorTET(tramite) + 1);

            // Guardar el nuevo estado del trámite
            FachadaPersistencia.getInstance().guardar(nuevoTET);
            tramite.addTramiteEstadoTramite(nuevoTET);
            tramite.setEstadoTramite(nuevoEstado); // Actualiza el estado en el trámite

            FachadaPersistencia.getInstance().guardar(tramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();

            return nuevoTET;

        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion(); // Rollback en caso de error
            e.printStackTrace();
        }
        return null;
    }

// Método auxiliar para obtener el próximo estado de destino según la configuración
    private EstadoTramite obtenerNuevoEstado(EstadoTramite estadoActual, List<ConfTipoTramiteEstadoTramite> confTTET) {
        for (ConfTipoTramiteEstadoTramite conf : confTTET) {
            if (conf.getEstadoTramiteOrigen().equals(estadoActual)) {
                List<EstadoTramite> estadosDestino = conf.getEstadoTramiteDestino();
                return estadosDestino.isEmpty() ? null : estadosDestino.get(0); // Retorna el primer destino
            }
        }
        return null;
    }

// Método auxiliar para obtener el contador TET actual
    private int obtenerContadorTET(Tramite tramite) {
        return tramite.getTramiteEstadoTramite().stream()
                .mapToInt(TramiteEstadoTramite::getContadorTET)
                .max()
                .orElse(0);
    }

    public List<DTOMostrarHistorial> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException {
        // Iniciar la transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterios = new ArrayList<>();
        DTOCriterio criterio = new DTOCriterio();
        criterio.setAtributo("nroTramite");
        criterio.setOperacion("=");
        criterio.setValor(nroTramite);
        criterios.add(criterio);

        List<Object> tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterios);
        if (tramites.isEmpty()) {
            throw new CambioEstadoException("Trámite no encontrado.");
        }

        Tramite tramite = (Tramite) tramites.get(0);

        // Obtener el historial de estados
        List<TramiteEstadoTramite> listaTramiteEstado = tramite.getTramiteEstadoTramite();

        // Crear el DTO que contendrá la lista de historial
        DTOMostrarHistorial dtoMostrarHistorial = new DTOMostrarHistorial();

        // Recorrer los estados y llenar el DTO
        for (TramiteEstadoTramite estadoTramite : listaTramiteEstado) {
            EstadoTramite estado = estadoTramite.getEstadoTramite();

            // Crear nuevo objeto DTOHistorialEstado
            DTOHistorialEstado dtoHistorial = new DTOHistorialEstado();

            // Setear los valores en el DTO desde estadoTramite y estado
            dtoHistorial.setContador(estadoTramite.getContadorTET());
            dtoHistorial.setFechaDesdeTET(estadoTramite.getFechaDesdeTET());
            dtoHistorial.setFechaHastaTET(estadoTramite.getFechaHastaTET());
            dtoHistorial.setNombreEstadoTramite(estado.getNombreEstadoTramite());

            // Añadir el DTOHistorialEstado a la lista de historial
            dtoMostrarHistorial.addHistorialEstado(dtoHistorial);
        }

        // Crear la lista de DTOMostrarHistorial para retornar
        List<DTOMostrarHistorial> listaHistorial = new ArrayList<>();
        listaHistorial.add(dtoMostrarHistorial);

        // Finalizar transacción
        FachadaPersistencia.getInstance().finalizarTransaccion();

        // Retornar la lista completa de DTOMostrarHistorial
        return listaHistorial;
    }

}
