package InformeTC.beans;

import utils.BeansUtils;
import InformeTC.ControladorInformeTramitesCancelados;
import InformeTC.dtos.TramiteDTO;

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
import java.util.List;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiInformeTramitesCancelados")
@SessionScoped

public class UIInformeTramitesCancelados implements Serializable {
    
    private ControladorInformeTramitesCancelados controladorTramitesCancelados = new ControladorInformeTramitesCancelados();
    
    private Date fechaDesde;
    private Date fechaHasta;
    private List<TramiteDTO> listaTramites;
    
    public ControladorInformeTramitesCancelados getControladorInformeTramitesCancelados() {
        return controladorTramitesCancelados;
    }

    public void setControladorInformeTramitesCancelados(ControladorInformeTramitesCancelados controladorTramitesCancelados) {
        this.controladorTramitesCancelados = controladorTramitesCancelados;
    }

    // Getters y setters para fechaDesde y fechaHasta

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<TramiteDTO> getListaTramites() {
        return listaTramites;
    }

    public void setListaTramites(List<TramiteDTO> listaTramites) {
        this.listaTramites = listaTramites;
    }
    
    

    public List<TramiteDTO> buscarTramites() {
               
        listaTramites = controladorTramitesCancelados.buscarTramites(fechaDesde,fechaHasta);
        
        return listaTramites;

    }
}

