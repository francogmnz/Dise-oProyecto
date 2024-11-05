package CambioEstado;

import CambioEstado.dtos.DTOEstadoDestinoCE;
import CambioEstado.dtos.DTOEstadoOrigen;
import CambioEstado.dtos.DTOEstadoOrigenCE;
import CambioEstado.dtos.DTOEstadosVersion;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOMostrarHistorial;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import Version.beans.NodoIU;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public DTOEstadoOrigenCE mostrarEstadosPosibles(int nroTramite) throws CambioEstadoException {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);


        List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        if (tramites == null || tramites.isEmpty()) {
            throw new CambioEstadoException("El tramite no existe");
        }

        Tramite tramiteElegido = (Tramite) tramites.get(0);

        EstadoTramite estadoOrigen = tramiteElegido.getEstadoTramite();
        
        DTOEstadoOrigenCE estadoOrigenDTO = new DTOEstadoOrigenCE();
        estadoOrigenDTO.setCodEstadoOrigen(estadoOrigen.getCodEstadoTramite());
        estadoOrigenDTO.setNombreEstadoOrigen(estadoOrigen.getNombreEstadoTramite());


        Version versionTramite = tramiteElegido.getVersion();
        List<ConfTipoTramiteEstadoTramite> listaConfiguraciones = versionTramite.getConfTipoTramiteEstadoTramite();


        List<DTOEstadoDestinoCE> estadosDestinoList = new ArrayList<>();

        for (ConfTipoTramiteEstadoTramite config : listaConfiguraciones) {
            List<EstadoTramite> estadosDestinos = config.getEstadoTramiteDestino();
            for(EstadoTramite estado: estadosDestinos){
                if(estado.getCodEstadoTramite() != estadoOrigen.getCodEstadoTramite() 
                        && estado.getFechaHoraBajaEstadoTramite() == null){
                    DTOEstadoDestinoCE estadoDestinoDTO = new DTOEstadoDestinoCE();
                    estadoDestinoDTO.setCodEstadoDestino(estado.getCodEstadoTramite());
                    estadoDestinoDTO.setNombreEstadoDestino(estado.getNombreEstadoTramite());
                    estadosDestinoList.add(estadoDestinoDTO);
                }
            }       
        }
        estadoOrigenDTO.addEstadosDestinos(estadosDestinoList);

        return estadoOrigenDTO;
    }   
    
    
    private void guardarTramite(TramiteDTO tramite) {
        FachadaPersistencia.getInstance().guardar(tramite);
    }
    
    
    

    public EstadoTramite cambiarEstado(int nroTramite) {
        // Iniciar transacción
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            // Obtener el trámite por su número
            Tramite tramite = obtenerTramitePorNumero(nroTramite);

            if (tramite == null) {
                throw new CambioEstadoException("Trámite no encontrado.");
            }

            // Obtener la versión del trámite y su JSON de dibujo
            Version version = tramite.getVersion();
            String dibujoJSON = version.getDibujo();

            // Deserializar el JSON en una lista de NodoIU
            ObjectMapper objectMapper = new ObjectMapper();
            List<NodoIU> nodos = objectMapper.readValue(dibujoJSON, new TypeReference<List<NodoIU>>() {
            });

            // Obtener el estado actual del trámite
            int estadoActualCodigo = tramite.getEstadoTramite().getCodEstadoTramite();

            // Buscar el nodo que representa el estado actual
            NodoIU nodoActual = nodos.stream()
                    .filter(n -> n.getCodigo() == estadoActualCodigo)
                    .findFirst()
                    .orElse(null);

            if (nodoActual == null || nodoActual.getDestinos().isEmpty()) {
                // Si no hay destinos, se considera el fin del trámite
                tramite.setFechaFinTramite(new Timestamp(System.currentTimeMillis()));
                FachadaPersistencia.getInstance().guardar(tramite);
                FachadaPersistencia.getInstance().finalizarTransaccion();
                return null;
            }

            // Obtener el siguiente estado de destino
            int nuevoEstadoCodigo = nodoActual.getDestinos().get(0); // Toma el primer destino
            EstadoTramite nuevoEstado = obtenerEstadoPorCodigo(nuevoEstadoCodigo);

            // Registrar el cambio de estado
            TramiteEstadoTramite nuevoTET = new TramiteEstadoTramite();
            nuevoTET.setEstadoTramite(nuevoEstado);
            nuevoTET.setFechaDesdeTET(new Timestamp(System.currentTimeMillis()));
            nuevoTET.setContadorTET(obtenerContadorTET(tramite) + 1);

            // Guardar el nuevo estado del trámite
            FachadaPersistencia.getInstance().guardar(nuevoTET);
            tramite.addTramiteEstadoTramite(nuevoTET);
            tramite.setEstadoTramite(nuevoEstado);

            FachadaPersistencia.getInstance().guardar(tramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();

            return nuevoEstado;

        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            e.printStackTrace();
            return null;
        }
    }

// Método para obtener el trámite por número
    private Tramite obtenerTramitePorNumero(int nroTramite) {
        List<DTOCriterio> criterioList = new ArrayList<>();

        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        List<?> result = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        return result.stream()
                .filter(obj -> obj instanceof Tramite)
                .map(obj -> (Tramite) obj)
                .findFirst()
                .orElse(null);  // Devuelve null si no se encuentra el trámite
    }

    private EstadoTramite obtenerEstadoPorCodigo(int codigo) {
        List<DTOCriterio> criterioList = new ArrayList<>();

        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codigo);
        criterioList.add(dto);

        List<?> result = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);

        return result.stream()
                .filter(obj -> obj instanceof EstadoTramite)
                .map(obj -> (EstadoTramite) obj)
                .findFirst()
                .orElse(null);  // Devuelve null si no se encuentra el estado
    }

// Método auxiliar para obtener el contador TET actual
private int obtenerContadorTET(Tramite tramite) {
        return tramite.getTramiteEstadoTramite().stream()
                .mapToInt(TramiteEstadoTramite::getContadorTET)
                .max()
                .orElse(0);  // Devuelve 0 si no hay ningún estado previo
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
