package CambioEstado;

import ABMArticulo.exceptions.CambioEstadoException;
import CambioEstado.dtos.DTOEstadoDestino;
import CambioEstado.dtos.DTOEstadoOrigen;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.TramiteDTO;
import utils.FachadaPersistencia;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExpertoCambioEstado {

   
    // Método para cambiar el estado del trámite
    public void cambiarEstado(int nroTramite, DTOEstadoOrigen estadoOrigen, DTOEstadoDestino estadoDestino, String descripcion) throws CambioEstadoException {
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
    private TramiteDTO obtenerTramite(int nroTramite) {
        List<TramiteDTO> tramites = FachadaPersistencia.getInstance().buscar("Tramite", nroTramite);
        return tramites.isEmpty() ? null : tramites.get(0);
    }

    // Método para obtener todos los trámites
    public List<TramiteDTO> obtenerTodosTramites() {
        return FachadaPersistencia.getInstance().buscarTodosTramites();
    }

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