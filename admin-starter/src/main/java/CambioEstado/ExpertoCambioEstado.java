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
import utils.fechaHoraActual;

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

        System.out.println("Ejecutando mostrarEstadosPosibles con nroTramite: " + nroTramite);
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        
        criterioList.add(dto);


        List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        if (tramites == null || tramites.isEmpty()) {
            throw new CambioEstadoException("El tramite no existe");
        }

        Tramite tramiteElegido = (Tramite) tramites.get(0);
        System.out.println("NroTramite: " + tramiteElegido.getNroTramite() + " - " + tramiteElegido.getEstadoTramite().getNombreEstadoTramite());
        EstadoTramite estadoOrigen = tramiteElegido.getEstadoTramite();
        
        System.out.println("Estado de Origen: " + estadoOrigen.getCodEstadoTramite() + " - " + estadoOrigen.getNombreEstadoTramite());
        
        DTOEstadoOrigenCE estadoOrigenDTO = new DTOEstadoOrigenCE();
        estadoOrigenDTO.setCodEstadoOrigen(estadoOrigen.getCodEstadoTramite());
        estadoOrigenDTO.setNombreEstadoOrigen(estadoOrigen.getNombreEstadoTramite());


        Version versionTramite = tramiteElegido.getVersion();
        List<ConfTipoTramiteEstadoTramite> listaConfiguraciones = versionTramite.getConfTipoTramiteEstadoTramite();


        List<DTOEstadoDestinoCE> estadosDestinoList = new ArrayList<>();

        for (ConfTipoTramiteEstadoTramite config : listaConfiguraciones) {
            if (config.getEstadoTramiteOrigen().getCodEstadoTramite() == estadoOrigen.getCodEstadoTramite()) {
                List<EstadoTramite> estadosDestinos = config.getEstadoTramiteDestino();
                for (EstadoTramite estado : estadosDestinos) {
                    if (estado.getFechaHoraBajaEstadoTramite() == null) {
                        DTOEstadoDestinoCE estadoDestinoDTO = new DTOEstadoDestinoCE();
                        estadoDestinoDTO.setCodEstadoDestino(estado.getCodEstadoTramite());
                        estadoDestinoDTO.setNombreEstadoDestino(estado.getNombreEstadoTramite());
                        estadosDestinoList.add(estadoDestinoDTO);
                        System.out.println("Estado destino añadido: " + estado.getCodEstadoTramite() + " - " + estado.getNombreEstadoTramite());
                    }
                }
            }
        }

        estadoOrigenDTO.addEstadosDestinos(estadosDestinoList);
        return estadoOrigenDTO;
    }   
    
    
    private void guardarTramite(TramiteDTO tramite) {
        FachadaPersistencia.getInstance().guardar(tramite);
    }
    
    
    

    public void cambiarEstado(int nroTramite, int codEstadoDestino) throws CambioEstadoException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio dto = new DTOCriterio();
            dto.setAtributo("nroTramite");
            dto.setOperacion("=");
            dto.setValor(nroTramite);
            criterioList.add(dto);

            List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

            if (tramites == null || tramites.isEmpty()) {
                throw new CambioEstadoException("El trámite no existe");
            }

            Tramite tramite = (Tramite) tramites.get(0);

            criterioList.clear();
            dto = new DTOCriterio();
            dto.setAtributo("codEstadoTramite");
            dto.setOperacion("=");
            dto.setValor(codEstadoDestino);
            criterioList.add(dto);

            List estados = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
            
            
            if (estados == null || estados.isEmpty()) {
                throw new CambioEstadoException("El estado destino no existe");
            }

            EstadoTramite estadoDestino = (EstadoTramite) estados.get(0);    
            // Registrar el cambio de estado
            TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite();
            tramiteEstadoTramite.setFechaDesdeTET(new Timestamp(System.currentTimeMillis()));
            tramiteEstadoTramite.setFechaHastaTET(null);
            tramiteEstadoTramite.setEstadoTramite(estadoDestino);
            tramite.setEstadoTramite(estadoDestino);
            tramite.addTramiteEstadoTramite(tramiteEstadoTramite);
            
            //Le seteo la fechaHasta al EstadoAnterior
            List<TramiteEstadoTramite> tetList = tramite.getTramiteEstadoTramite();
            for(TramiteEstadoTramite tet: tetList){
                if(tet.getFechaHastaTET() == null){
                    tet.setFechaHastaTET(fechaHoraActual.obtenerFechaHoraActual());
                }
            }
            
            //Busco si es el ultimo estado
            Version versionUltimoTramite = tramite.getVersion();
            List<ConfTipoTramiteEstadoTramite> listaConfig = versionUltimoTramite.getConfTipoTramiteEstadoTramite();
            for(ConfTipoTramiteEstadoTramite config: listaConfig){
                if(config.getEstadoTramiteDestino().isEmpty()){
                    tramite.setFechaFinTramite(fechaHoraActual.obtenerFechaHoraActual());
                }
            }
            
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);
            FachadaPersistencia.getInstance().guardar(tramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();

           
        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            e.printStackTrace();
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


    public List<DTOHistorialEstado> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException {

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        List tramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        if (tramites == null || tramites.isEmpty()) {
            throw new CambioEstadoException("El trámite no existe");
        }

        Tramite tramite = (Tramite) tramites.get(0);

        List<TramiteEstadoTramite> historialEstados = tramite.getTramiteEstadoTramite();

        if (historialEstados == null || historialEstados.isEmpty()) {
            throw new CambioEstadoException("No hay historial de estados para el trámite");
        }

 
        List<DTOHistorialEstado> dtoHistorialEstados = new ArrayList<>();
        for (TramiteEstadoTramite tet : historialEstados) {
        
            DTOHistorialEstado dtoHistorialEstado = new DTOHistorialEstado();
            dtoHistorialEstado.setNombreEstadoTramite(tet.getEstadoTramite().getNombreEstadoTramite());
            dtoHistorialEstado.setFechaDesdeTET(tet.getFechaDesdeTET());
            dtoHistorialEstado.setFechaHastaTET(tet.getFechaHastaTET());
            dtoHistorialEstado.setContador(tet.getContadorTET());
            dtoHistorialEstados.add(dtoHistorialEstado);
        }

        return dtoHistorialEstados;
    }

}
