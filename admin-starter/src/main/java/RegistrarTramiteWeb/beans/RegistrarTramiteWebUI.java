/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import RegistrarTramiteWeb.dtos.DTOCliente;
import RegistrarTramiteWeb.dtos.DTONumeroTramite;
import RegistrarTramiteWeb.dtos.DTOResumen;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import org.omnifaces.util.Messages;

/**
 *
 * @author licciardi
 */
@Named("uiRegistrarTramiteWeb")
@SessionScoped
public class RegistrarTramiteWebUI implements Serializable {
    
    private ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb = new ControladorRegistrarTramiteWeb();
    private DTOCliente dtoCliente = new DTOCliente();
    private DTOCategoriaTipoTramite categoriaSeleccionada = new DTOCategoriaTipoTramite();
    private DTOTipoTramite tipoTramiteSeleccionado = new DTOTipoTramite();
    private DTONumeroTramite dtoNumeroTramite = new DTONumeroTramite();
        
    private List<DTOCategoriaTipoTramite> listaCategorias;
   
    private List<DTOTipoTramite> listaTiposTramite;
  
    private DTOResumen dtoResumen;  
    
    private String mensajeError;
    
    public ControladorRegistrarTramiteWeb getControladorRegistrarTramiteWeb() {
        return controladorRegistrarTramiteWeb;
    }

    public void setControladorRegistrarTramiteWeb(ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb) {
        this.controladorRegistrarTramiteWeb = controladorRegistrarTramiteWeb;
    }

    public DTOCliente getDtoCliente() {
        return dtoCliente;
    }

    public void setDtoCliente(DTOCliente dtoCliente) {
        this.dtoCliente = dtoCliente;
    }

    public List<DTOCategoriaTipoTramite> getListaCategorias() {
        return listaCategorias;
    }

    public void setListaCategorias(List<DTOCategoriaTipoTramite> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public DTOCategoriaTipoTramite getCategoriaSeleccionada() {
        return categoriaSeleccionada;
    }

    public void setCategoriaSeleccionada(DTOCategoriaTipoTramite categoriaSeleccionada) {
        this.categoriaSeleccionada = categoriaSeleccionada;
    }

    public List<DTOTipoTramite> getListaTiposTramite() {
        return listaTiposTramite;
    }

    public void setListaTiposTramite(List<DTOTipoTramite> listaTiposTramite) {
        this.listaTiposTramite = listaTiposTramite;
    }

    public DTOTipoTramite getTipoTramiteSeleccionado() {
        return tipoTramiteSeleccionado;
    }

    public void setTipoTramiteSeleccionado(DTOTipoTramite tipoTramiteSeleccionado) {
        this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
    }

    public DTOResumen getDtoResumen() {
        return dtoResumen;
    }

    public void setDtoResumen(DTOResumen dtoResumen) {
        this.dtoResumen = dtoResumen;
    }

    public DTONumeroTramite getDtoNumeroTramite() {
        return dtoNumeroTramite;
    }

    public void setDtoNumeroTramite(DTONumeroTramite dtoNumeroTramite) {
        this.dtoNumeroTramite = dtoNumeroTramite;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    
    public String ingresarDNI() {
        try {
            // Busca el cliente por DNI
            DTOCliente clienteBuscado = controladorRegistrarTramiteWeb.buscarClienteIngresado(dtoCliente.getDniCliente());
            if (clienteBuscado != null) {
                
                this.dtoCliente = clienteBuscado;
                return "confirmarCliente?faces-redirect=true";
            } else {
                mensajeError = "Cliente no encontrado.";
                return null; 
            }
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            return null; 
        }
    }  
    
    public String confirmarCliente() {
        listarCategoriasTipoTramite();
        return "seleccionarCategoria?faces-redirect=true"; //?faces-redirect=true
    }

    public void listarCategoriasTipoTramite() {
        try {
            listaCategorias = controladorRegistrarTramiteWeb.listarCategoriasTipoTramite();
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            listaCategorias = null;
        }
    }
    
    public String seleccionarCategoria() {
        if (categoriaSeleccionada.getCodCategoriaTipoTramite() != 0) {
            listarTipoTramites();
            return "seleccionarTipoTramite?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar una categoría.";
            return null;
        }
    }  
    
    public void listarTipoTramites() {
        try {
            listaTiposTramite = controladorRegistrarTramiteWeb.listarTipoTramites(categoriaSeleccionada.getCodCategoriaTipoTramite());
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            listaTiposTramite = null;
        }
    }
    
    public String seleccionarTipoTramite() {
        if (tipoTramiteSeleccionado.getCodTipoTramite() != 0) {
            irAResumen();
            return "mostrarResumen?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar un tipo de trámite.";
            return null;
        }
    }
    
        public void irAResumen() {
        try {
            dtoResumen = controladorRegistrarTramiteWeb.mostrarResumenTipoTramite(tipoTramiteSeleccionado.getCodTipoTramite());
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            dtoResumen = null;
        }
    }
    

    
    public String confirmarTramite() {
        try {
            dtoNumeroTramite = controladorRegistrarTramiteWeb.registrarTramite();
    
            return "mostrarNumeroTramite?faces-redirect=true"; //?faces-redirect=true
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            return null;
        }
    }
    public void cancelar() throws IOException { 
        try {
            controladorRegistrarTramiteWeb.resetearEstado();
            this.resetearEstado();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            Messages.create("Error al redirigir al inicio.").fatal().add();
        }
    }    
    
    public void irAlInicio() throws IOException { 
        try {
            controladorRegistrarTramiteWeb.resetearEstado();
            this.resetearEstado();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            Messages.create("Error al redirigir al inicio.").fatal().add();
        }
    }  
    
    public void resetearEstado() {
        dtoCliente = new DTOCliente();
        categoriaSeleccionada = new DTOCategoriaTipoTramite();
        tipoTramiteSeleccionado = new DTOTipoTramite();
        dtoNumeroTramite = new DTONumeroTramite();
        mensajeError = null;
        listaCategorias = null;
        listaTiposTramite = null;
        dtoResumen = null;
    }     
}
