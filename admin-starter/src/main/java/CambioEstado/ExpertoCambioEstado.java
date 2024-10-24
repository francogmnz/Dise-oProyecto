package CambioEstado;

import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
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
   
    // Método para cambiar el estado del trámite
    public TramiteEstadoTramite cambiarEstado(int nroTramite) {
        FachadaPersistencia.getInstance().iniciarTransaccion(); // Iniciar la transacción para la persistencia
        List<DTOCriterio> criterioList = new ArrayList<>();

        DTOCriterio dto = new DTOCriterio();
        // Buscar los trámites por número
        dto.setAtributo("nroTramite");
        dto.setOperacion("=");
        dto.setValor(nroTramite);
        criterioList.add(dto);

        try {
            // Buscar el trámite en la base de datos
            List<?> result = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
            List<Tramite> lTramites = new ArrayList<>();
            for (Object obj : result) {
                if (obj instanceof Tramite) {
                    lTramites.add((Tramite) obj);
                }
            }

            // Verificar si se encontró el trámite
            if (lTramites.isEmpty()) {
                throw new CambioEstadoException("Trámite no encontrado.");
            }

            // Obtener el primer trámite
            Tramite tramite = lTramites.get(0);

            // Verificar el número de trámite
            if (nroTramite != tramite.getNroTramite()) {
                throw new CambioEstadoException("Trámite no encontrado, intente nuevamente.");
            }

            // Obtener la fecha de recepción del trámite
            Timestamp fechaRecepcionTramite = tramite.getFechaRecepcionTramite();

            // Crear criterios para buscar la versión
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
            criterioFechaBaja.setOperacion("IS NULL");
            criterioFechaBaja.setValor(null); // Valor null ya que la operación es IS NULL
            criterioList.add(criterioFechaBaja);

            // Obtener el estado actual del trámite
            EstadoTramite estadoActual = tramite.getEstadoTramite();

            // Obtener la versión del trámite
            Version version = tramite.getVersion();
            List<ConfTipoTramiteEstadoTramite> confTTET = version.getConfTipoTramiteEstadoTramite();

            // Verificar la configuración de los estados de origen y destino
            for (ConfTipoTramiteEstadoTramite conf : confTTET) {
                List<EstadoTramite> estadosOrigen = conf.getEstadoTramiteOrigen();
                boolean hayDestino = false;

                // Iterar sobre los estados de origen
                for (EstadoTramite estadoOrigen : estadosOrigen) {
                    List<EstadoTramite> estadosDestino = conf.getEstadoTramiteDestino();

                    // Comprobar si hay un estado destino que coincida
                    for (EstadoTramite estadoDestino : estadosDestino) {
                        if (estadoOrigen.getCodEstadoTramite() == estadoDestino.getCodEstadoTramite()) {
                            hayDestino = true;
                            break;
                        }
                    }
                }
                // Si no hay destino, el trámite finaliza
                if (!hayDestino) {
                    tramite.setFechaFinTramite(new Timestamp(System.currentTimeMillis()));
                    FachadaPersistencia.getInstance().guardar(tramite); // Guardar el cambio
                    FachadaPersistencia.getInstance().finalizarTransaccion(); // Confirmar transacción
                    return null; // No hay más estados, retorna null
                }
            }

            // Actualizar TramiteEstadoTramite (TET)
            for (TramiteEstadoTramite tramiteEstado : tramite.getTramiteEstadoTramite()) {
                if (tramiteEstado.getFechaHoraAltaTET() == null) {
                    tramiteEstado.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));

                    // Crear nuevo estado del trámite (nuevo TET)
                    TramiteEstadoTramite nuevoTET = new TramiteEstadoTramite();
                    nuevoTET.setEstadoTramite(tramiteEstado.getEstadoTramite()); // Asigna el nuevo estado
                    nuevoTET.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
                    nuevoTET.setContadorTET(tramiteEstado.getContadorTET() + 1);

                    // Guardar el nuevo TET
                    FachadaPersistencia.getInstance().guardar(nuevoTET); // Guardar el nuevo estado en la BD
                    tramite.addTramiteEstadoTramite(nuevoTET); // Asociar el nuevo estado al trámite

                    // Guardar el trámite actualizado
                    FachadaPersistencia.getInstance().guardar(tramite); // Guardar el trámite con el nuevo TET
                    FachadaPersistencia.getInstance().finalizarTransaccion(); // Confirmar la transacción

                    return nuevoTET; // Retorna el nuevo TramiteEstadoTramite
                }
            }
        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion(); // Deshacer la transacción en caso de error
            e.printStackTrace(); // Opcional, para depurar
        }
        return null;
    }
    
    public List<TramiteEstadoTramite> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException {
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
    List<TramiteEstadoTramite> historialEstados = tramite.getTramiteEstadoTramite();
    
    FachadaPersistencia.getInstance().finalizarTransaccion();
    
    return historialEstados;
}


        // Actualizar el estado del trámite
       // tramite.setNombreEstadoTramite(estadoDestino.getNombreEstadoDestino());
        // Aquí puedes agregar la lógica para actualizar la fecha de recepción si es necesario
/*
        // Registrar en el historial
        DTOHistorialEstado historial = new DTOHistorialEstado();
        historial.setNombreEstadoTramite(estadoDestino.getNombreEstadoDestino());
        historial.setFechaDesdeTET(LocalDateTime.now());
        // Setear la fecha hasta si es necesario
*/
        // Guardar cambios en la base de datos
        
    }


    
/*
    // Método para guardar el historial de estado
    private void guardarHistorial(DTOHistorialEstado historial) {
        FachadaPersistencia.getInstance().guardar(historial);
    }

    // Método para obtener el historial de estados
    public List<DTOHistorialEstado> obtenerHistorialEstados(int nroTramite) {
        // Implementa la lógica para recuperar el historial de estados desde la base de datos
        return new ArrayList<>(); // Reemplaza con la implementación real
    }*/
