package InformeTT;

import InformeTT.dtos.TramiteDTO;
import java.util.Date;
import java.util.List;


public class ControladorInformeTramitesTerminados {
 
    private ExpertoInformeTramitesTerminados expertoTramitesTerminados = new ExpertoInformeTramitesTerminados();
    
    public List<TramiteDTO> buscarTramites(Date fechaDesde, Date fechaHasta){
        return expertoTramitesTerminados.buscarTramites(fechaDesde, fechaHasta);
    }
}
