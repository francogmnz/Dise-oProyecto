package CancelarTramites.beans;
import entidades.TipoTramite;
import utils.BeansUtils;

import entidades.Tramite;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;

import java.util.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiCancelarTramites")
@SessionScoped

public class UICancelarTramites implements Serializable {
    
    private List<Tramite> listaTramites;

    // Getters y setters
    
    public List<Tramite> getListaTramites() {
        return listaTramites;
    }

    public void setListaTramites(List<Tramite> listaTramites) {
        this.listaTramites = listaTramites;
    }
    
    

    public List<Tramite> cancelarTramites() {
        
    
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
        List<Tramite> tramites = new ArrayList<>();
        
        //Recorro los tramites que cumplen los 2 criterios para evaluar si ya vencio el plazo de entrega de documentacion
        for (Object objeto : resultados) {
            if (objeto instanceof Tramite) {
                 
                Tramite tramite = (Tramite) objeto; //casteo a tramite
        
                // Obtener el tipo de trámite y su plazo en días
                TipoTramite tipoTramite = tramite.getTipoTramite();
                int diasDePlazo = tipoTramite.getPlazoEntregaDocumentacionTT();
        
                // Calcular la fecha límite
                Date fechaInicio = tramite.getFechaInicioTramite();
                Calendar fechaLimite = Calendar.getInstance();
                fechaLimite.setTime(fechaInicio);
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

                    
                    // Agregar el trámite a la lista de tramites cancelados
                    tramites.add(tramite);
                }

        
        
            }
        }
        
        FachadaPersistencia.getInstance().finalizarTransaccion();

        // Asignar la lista de trámites al bean
        this.listaTramites = tramites; 

        // Imprimir para verificar el tamaño de la lista
        System.out.println("Total de trámites encontrados: " + tramites.size());

        return tramites;
    }
}

