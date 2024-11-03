package CambioEstado;

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
   public TramiteEstadoTramite cambiarEstado(int nroTramite){
   return expertoCambioEstado.cambiarEstado(nroTramite);
   }
   
   public List<DTOMostrarHistorial> obtenerHistorialEstados(int nroTramite) throws CambioEstadoException{
       return expertoCambioEstado.obtenerHistorialEstados(nroTramite);
   }



}