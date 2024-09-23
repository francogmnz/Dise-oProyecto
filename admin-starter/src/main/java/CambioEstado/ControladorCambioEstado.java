package CambioEstado;

import CambioEstado.dtos.CambioEstadoDTO;
import ABMArticulo.exceptions.CambioEstadoException;
import CambioEstado.dtos.DTOEstadoDestino;
import CambioEstado.dtos.DTOEstadoOrigen;
import RegistrarTramite.dtos.TramiteDTO;
import java.util.List;
import utils.FachadaPersistencia;

public class ControladorCambioEstado {
    private ExpertoCambioEstado expertoCambioEstado = new ExpertoCambioEstado();

    public void cambiarEstado(CambioEstadoDTO cambioEstadoDTO) throws CambioEstadoException {
        // Aquí deberías tener lógica para obtener el estado origen y destino si es necesario
        DTOEstadoOrigen estadoOrigen = obtenerEstadoOrigen(cambioEstadoDTO.getIdTramite());
        DTOEstadoDestino estadoDestino = obtenerEstadoDestino(cambioEstadoDTO.getNuevoEstado());

        // Llamada al experto para cambiar el estado
        expertoCambioEstado.cambiarEstado(cambioEstadoDTO.getIdTramite(), estadoOrigen, estadoDestino, cambioEstadoDTO.getDescripcion());
    }

    private DTOEstadoOrigen obtenerEstadoOrigen(int idTramite) {
        // Implementa lógica para obtener el estado origen del trámite
        return new DTOEstadoOrigen(); // Reemplaza con la lógica real
    }

    private DTOEstadoDestino obtenerEstadoDestino(String nombreEstado) {
        // Implementa lógica para obtener el estado destino
        return new DTOEstadoDestino(); // Reemplaza con la lógica real
    }
    public List<TramiteDTO> obtenerTodosTramites() {
    return FachadaPersistencia.getInstance().buscarTodosTramites();
}

}
