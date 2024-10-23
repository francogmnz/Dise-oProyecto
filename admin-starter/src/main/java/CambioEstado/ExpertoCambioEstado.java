package CambioEstado;

import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOMensajeExito;
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
import java.util.Date;
import java.util.List;
import utils.DTOCriterio;

public class ExpertoCambioEstado {
public List<DTOTramitesVigentes> buscarTramites(int legajoConsultor) {
    // Iniciar transacción
    FachadaPersistencia.getInstance().iniciarTransaccion();
    List<DTOTramitesVigentes> dtoTramitesVigentesList = new ArrayList<>();

    try {
        System.out.println("Legajo del consultor recibido: " + legajoConsultor);

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        dto.setAtributo("legajoConsultor");
        dto.setOperacion("=");
        dto.setValor(legajoConsultor);

        criterioList.add(dto);

        DTOCriterio dto2 = new DTOCriterio();
        dto2.setAtributo("fechaHoraBajaConsultor");
        dto2.setOperacion("=");
        dto2.setValor(null);

        criterioList.add(dto2);

        List<Object> lConsultor = FachadaPersistencia.getInstance().buscar("Consultor", criterioList);

        if (lConsultor.isEmpty()) {
            System.out.println("No se encontró el consultor con legajo: " + legajoConsultor);
            throw new CambioEstadoException("No se encontro la categoría seleccionada.");
        }

        Consultor consultorEncontrado = (Consultor) lConsultor.get(0);
        System.out.println("Consultor encontrado: " + consultorEncontrado.getNombreConsultor());

        criterioList.clear();
        DTOCriterio criterio1 = new DTOCriterio();
        criterio1.setAtributo("consultor");
        criterio1.setOperacion("=");
        criterio1.setValor(consultorEncontrado);

        criterioList.add(criterio1);

        DTOCriterio criterio2 = new DTOCriterio();
        criterio2.setAtributo("fechaFinTramite");
        criterio2.setOperacion("=");
        criterio2.setValor(null);

        criterioList.add(criterio2);

        DTOCriterio criterio3 = new DTOCriterio();
        criterio3.setAtributo("fechaAnulacionTramite");
        criterio3.setOperacion("=");
        criterio3.setValor(null);

        criterioList.add(criterio3);

        System.out.println("Criterios de búsqueda configurados: " + criterioList);

        // Buscar trámites del consultor
        List<Object> lTramites = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
        System.out.println("Trámites encontrados: " + lTramites.size());

        DTOTramitesVigentes dtoTramitesVigentes = new DTOTramitesVigentes();
        dtoTramitesVigentes.setCodConsultor(legajoConsultor); // Asignar código del consultor

        // Agregar cada trámite encontrado al DTOTramitesVigentes
        for (Object tramiteObj : lTramites) {
            Tramite tramite = (Tramite) tramiteObj;
            System.out.println("Trámite encontrado con número: " + tramite.getNroTramite());

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
        System.err.println("Error en la búsqueda de trámites: " + e.getMessage());
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    // Retornar la lista final de trámites vigentes
    return dtoTramitesVigentesList;
}


    /* public TramiteEstadoTramite cambiarEstado(int nroTramite) {
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
     */
    // Método para cambiar el estado del trámite
    /* public void cambiarEstado(int nroTramite, DTOEstadoOrigen estadoOrigen, DTOEstadoDestino estadoDestino, String descripcion) throws CambioEstadoException {
        // Obtener el trámite actual
        TramiteDTO tramite = obtenerTramite(nroTramite);
        if (tramite == null) {
            throw new CambioEstadoException("El trámite no existe.");
        }

        // Actualizar el estado del trámite
        tramite.setNombreEstadoTramite(estadoDestino.getNombreEstadoDestino());
        // Aquí puedes agregar la lógica para actualizar la fecha de recepción si es necesario

        // Registrar en el historial
        DTOHistorialEstado historial = new DTOHistorialEstado();
        historial.setNombreEstadoTramite(estadoDestino.getNombreEstadoDestino());
        historial.setFechaDesdeTET(LocalDateTime.now());
        // Setear la fecha hasta si es necesario

        // Guardar cambios en la base de datos
        guardarTramite(tramite);
        guardarHistorial(historial);
    }

    // Método para obtener un trámite por número
    /*private TramiteDTO obtenerTramite(int nroTramite) {
        List<TramiteDTO> tramites = FachadaPersistencia.getInstance().buscar("Tramite", nroTramite);
        return tramites.isEmpty() ? null : tramites.get(0);
    }

    // Método para obtener todos los trámites
    public List<Object> buscarConsultor(String legajoConsultor) {
        // Utilizar FachadaPersistencia para buscar al consultor
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> criterio1 = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
/*
        if (legajoConsultor != null) {
            criterio1.setAtributo("legajoConsutltor");
            criterio1.setOperacion("=");
            criterio1.setValor(legajoConsultor);
            System.out.println("No se encontraron trámites para el legajo: " + legajo);
        }

        return tramites;*/
    // Método para guardar el trámite actualizado
    private void guardarTramite(TramiteDTO tramite) {
        FachadaPersistencia.getInstance().guardar(tramite);
    }

    // Método para guardar el historial de estado
    private void guardarHistorial(DTOHistorialEstado historial) {
        FachadaPersistencia.getInstance().guardar(historial);
    }

    // Método para obtener el historial de estados
    public List<DTOHistorialEstado> obtenerHistorialEstados(int nroTramite) {
        // Implementa la lógica para recuperar el historial de estados desde la base de datos
        return new ArrayList<>(); // Reemplaza con la implementación real
    }
}
