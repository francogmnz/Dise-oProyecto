package CambioEstado;

import CambioEstado.dtos.DTOEstadoOrigenCE;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOMostrarHistorial;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.exceptions.CambioEstadoException;
import entidades.EstadoTramite;
import entidades.TramiteEstadoTramite;
import java.util.ArrayList;
import java.util.List;


public class ControladorCambioEstado {
    private ExpertoCambioEstado expertoCambioEstado = new ExpertoCambioEstado();
    
        // Método para buscar trámites basados en el legajo del consultor
   public List<DTOTramitesVigentes> buscarTramites(int legajoConsultor) {
    List<DTOTramitesVigentes> tramites = expertoCambioEstado.buscarTramites(legajoConsultor);
    // Asegurarse de que no se devuelva null
    return (tramites != null) ? tramites : new ArrayList<>(); // Devuelve una lista vacía si es null
} 
   
   public List<DTOHistorialEstado> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException{
       return expertoCambioEstado.obtenerHistorialEstados(nroTramite);
   }
   public DTOEstadoOrigenCE mostrarEstadosPosibles(int nroTramite) throws CambioEstadoException {
       return expertoCambioEstado.mostrarEstadosPosibles(nroTramite);
   }

   public void cambiarEstado(int nroTramite, int codEstadoDestino) throws CambioEstadoException {
        expertoCambioEstado.cambiarEstado(nroTramite,codEstadoDestino);
   }

}