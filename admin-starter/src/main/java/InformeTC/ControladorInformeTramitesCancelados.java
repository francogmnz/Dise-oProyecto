package InformeTC;

import InformeTC.dtos.TramiteDTO;
import java.util.Date;
import java.util.List;

public class ControladorInformeTramitesCancelados {
    
    /*
     private ExpertoInformeTramitesTerminados expertoTramitesTerminados = new ExpertoInformeTramitesTerminados();
    
    public List<TramiteDTO> buscarTramites(Date fechaDesde, Date fechaHasta){
        return expertoTramitesTerminados.buscarTramites(fechaDesde, fechaHasta);
    }
    */
    
    private ExpertoInformeTramitesCancelados expertoTramitesCancelados = new ExpertoInformeTramitesCancelados();
    
    public List<TramiteDTO> buscarTramites(Date fechaDesde, Date fechaHasta){
        return expertoTramitesCancelados.buscarTramites(fechaDesde, fechaHasta);
    }
    
}
