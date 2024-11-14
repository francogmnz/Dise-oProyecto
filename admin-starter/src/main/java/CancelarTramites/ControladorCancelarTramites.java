package CancelarTramites;

import CancelarTramites.dtos.TramiteDTO;
import java.util.Date;
import java.util.List;


public class ControladorCancelarTramites {
    
   
    private ExpertoCancelarTramites expertoCancelarTramites = new ExpertoCancelarTramites();
    
    public List<TramiteDTO> cancelarTramites() {
        return expertoCancelarTramites.cancelarTramites();
    }
    
}
