
package CancelarTramites;

import CancelarTramites.dtos.TramiteDTO;
import entidades.TipoTramite;
import utils.BeansUtils;
import entidades.Tramite;

import java.sql.Timestamp;
import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;


public class ExpertoCancelarTramites {
    
    public List<TramiteDTO> cancelarTramites() {
        
    
        List<DTOCriterio> criterios = new ArrayList<>();

        // Agregar criterios de búsqueda
        DTOCriterio criterio1 = new DTOCriterio();
        
        //Busco los tramites que no hayan sido anulados y que no tengan fecha de documentacion presentada
        
        criterio1.setAtributo("fechaAnulacionTramite");
        criterio1.setOperacion("=");
        criterio1.setValor(null);
                
        criterios.add(criterio1);
        
        DTOCriterio criterio2 = new DTOCriterio();
        
        criterio2.setAtributo("fechaPresentacionTotalDocumentacion");
        criterio2.setOperacion("=");
        criterio2.setValor(null);
        
        criterios.add(criterio2);
  

        // Llamar al método de búsqueda
        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<Object> resultados = FachadaPersistencia.getInstance().buscar("Tramite", criterios);
        

        // Convertir List<Object> a List<Tramite>
        List<TramiteDTO> tramites = new ArrayList<>();
        
        //Recorro los tramites que cumplen los 2 criterios para evaluar si ya vencio el plazo de entrega de documentacion
        for (Object objeto : resultados) {
            if (objeto instanceof Tramite) {
                 
                Tramite tramite = (Tramite) objeto; //casteo a tramite
        
                // Obtener el tipo de trámite y su plazo en días
                TipoTramite tipoTramite = tramite.getTipoTramite();
                int diasDePlazo = tipoTramite.getPlazoEntregaDocumentacionTT();
        
                // Calcular la fecha límite

                Calendar fechaLimite = Calendar.getInstance();
                fechaLimite.setTime(tramite.getFechaRecepcionTramite());
                fechaLimite.add(Calendar.DAY_OF_MONTH, diasDePlazo);
        
                // Comparar la fecha límite con la fecha actual
                Date fechaActual = new Date();
                if (fechaActual.after(fechaLimite.getTime())) {
                    System.out.println("El trámite con ID " + tramite.getNroTramite() + " ha vencido el plazo.");
                    
                    // Cambiar la fecha de anulación a la fecha actual
                    tramite.setFechaAnulacionTramite(new Timestamp(fechaActual.getTime()));
                    
                    // Guardar el cambio
                    FachadaPersistencia.getInstance().iniciarTransaccion();
                    try {
                        FachadaPersistencia.getInstance().guardar(tramite);
                    } catch (Exception e) {
                        e.printStackTrace();
                         
                    }
                    
                    //Creo TramiteDTO
                    TramiteDTO tramiteDTO = new TramiteDTO();
                    tramiteDTO.setNroTramite(tramite.getNroTramite());
                    tramiteDTO.setFechaInicioTramite(tramite.getFechaInicioTramite());
                    tramiteDTO.setFechaAnulacionTramite(tramite.getFechaAnulacionTramite());
                    tramiteDTO.setTipoTramite(tramite.getTipoTramite());
                    tramiteDTO.setPlazoEntregaDoc(diasDePlazo);

                    
                    // Agregar el trámite a la lista de tramites cancelados
                    tramites.add(tramiteDTO);
                }

        
        
            }
        }
        
        FachadaPersistencia.getInstance().finalizarTransaccion();


        return tramites;
    }
    
}
