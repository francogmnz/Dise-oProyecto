package CancelarTramites.beans;
import CancelarTramites.ControladorCancelarTramites;
import CancelarTramites.dtos.TramiteDTO;
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
    
    private List<TramiteDTO> listaTramites;
    
    private ControladorCancelarTramites controladorCancelarTramites = new ControladorCancelarTramites();

    // Getters y setters
    
    public List<TramiteDTO> getListaTramites() {
        return listaTramites;
    }

    public void setListaTramites(List<TramiteDTO> listaTramites) {
        this.listaTramites = listaTramites;
    }
    
    public ControladorCancelarTramites getControladorCancelarTramites() {
        return controladorCancelarTramites;
    }

    public void setControladorCancelarTramites(ControladorCancelarTramites controladorCancelarTramites) {
        this.controladorCancelarTramites = controladorCancelarTramites;
    }
    
    

    public List<TramiteDTO> cancelarTramites() {
        
        listaTramites = controladorCancelarTramites.cancelarTramites();
        
        return listaTramites;        
    
        
    }
}

