package CambioEstado;

import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import entidades.Consultor;
import entidades.Tramite;
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