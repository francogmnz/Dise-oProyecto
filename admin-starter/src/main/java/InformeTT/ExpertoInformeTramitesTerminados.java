package InformeTT;

import entidades.Tramite;
import java.util.ArrayList;
import java.util.List;
import InformeTT.dtos.TramiteDTO;
import utils.DTOCriterio;
import utils.FachadaPersistencia;
import java.util.Date;
import java.sql.Timestamp;


public class ExpertoInformeTramitesTerminados {
    
    public List<TramiteDTO> buscarTramites(Date fechaDesde, Date fechaHasta) throws Exception {
        
        if (fechaDesde == null) {
            throw new Exception("Ingrese una fechaDesde");
        }
        if (fechaHasta == null) {
            throw new Exception("Ingrese una fechaHasta");
        }
        // Convertir Date a Timestamp
        Timestamp tsDesde = new Timestamp(fechaDesde.getTime());
        Timestamp tsHasta = new Timestamp(fechaHasta.getTime());

        // Imprimir para verificar las conversiones
        System.out.println("fechaDesde (Timestamp): " + tsDesde);
        System.out.println("fechaHasta (Timestamp): " + tsHasta);
    
        List<DTOCriterio> criterios = new ArrayList<>();

        // Agregar criterios de búsqueda
        DTOCriterio criterio1 = new DTOCriterio();
        
        criterio1.setAtributo("fechaFinTramite");
        criterio1.setOperacion(">=");
        criterio1.setValor(fechaDesde);
                
        criterios.add(criterio1);
        
        DTOCriterio criterio2 = new DTOCriterio();
        
        criterio2.setAtributo("fechaFinTramite");
        criterio2.setOperacion("<=");
        criterio2.setValor(fechaHasta);
        
        criterios.add(criterio2);
  

        // Llamar al método de búsqueda
        List resultados = FachadaPersistencia.getInstance().buscar("Tramite", criterios);


        List<TramiteDTO> tramitesResultado = new ArrayList<>();
        
        for (Object x : resultados) {
            
            Tramite tramite = (Tramite) x;
            TramiteDTO dtoTramite = new TramiteDTO();
            dtoTramite.setNroTramite(tramite.getNroTramite());
            dtoTramite.setFechaInicioTramite(tramite.getFechaInicioTramite());
            dtoTramite.setFechaFinTramite(tramite.getFechaFinTramite());
            dtoTramite.setTipoTramite(tramite.getTipoTramite());
            dtoTramite.setPrecioTramite(tramite.getPrecioTramite());
            tramitesResultado.add(dtoTramite);
        }
        
        return tramitesResultado;
    }
}
