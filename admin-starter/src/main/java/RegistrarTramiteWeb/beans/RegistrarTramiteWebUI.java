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
    private int dniCliente;
    private DTOCliente dtoCliente;
       
    private List<DTOCategoriaTipoTramite> listaCategorias;
    private int codCategoriaTipoTramiteSeleccionada;
    
    private List<DTOTipoTramite> listaTiposTramite;
    private int codTipoTramiteSeleccionado;
    
    private DTOResumen dtoResumen;  
    private int numeroTramite;
    
    private String mensajeError;
    public ControladorRegistrarTramiteWeb getControladorRegistrarTramiteWeb() {
        return controladorRegistrarTramiteWeb;
    }

    public void setControladorRegistrarTramiteWeb(ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb) {
        this.controladorRegistrarTramiteWeb = controladorRegistrarTramiteWeb;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public DTOCliente getDtoCliente() {
        return dtoCliente;
    }

    public void setDtoCliente(DTOCliente dtoCliente) {
        this.dtoCliente = dtoCliente;
    }

    public List<DTOCategoriaTipoTramite> getListaCategorias() {
        if (listaCategorias == null) {
            listaCategorias = controladorRegistrarTramiteWeb.listarCategoriasTipoTramite();
        }
        return listaCategorias;
    }

    public void setListaCategorias(List<DTOCategoriaTipoTramite> listaCategorias) {
        this.listaCategorias = listaCategorias;
    }

    public int getCodCategoriaTipoTramiteSeleccionada() {
        return codCategoriaTipoTramiteSeleccionada;
    }

    public void setCodCategoriaTipoTramiteSeleccionada(int codCategoriaTipoTramiteSeleccionada) {
        this.codCategoriaTipoTramiteSeleccionada = codCategoriaTipoTramiteSeleccionada;
    }

    public List<DTOTipoTramite> getListaTiposTramite() {
        if (listaTiposTramite == null) {
            try{
                listaTiposTramite = controladorRegistrarTramiteWeb.listarTipoTramites(codCategoriaTipoTramiteSeleccionada);
            } catch (RegistrarTramiteWebException e) {
                mensajeError = e.getMessage();
                // Manejar el error, por ejemplo, redirigir a una página de error o mostrar un mensaje en la vista
                // Aquí puedes decidir cómo manejarlo, por ahora retornamos null
                return null;
            }
        }
        return listaTiposTramite;
    }

    public void setListaTiposTramite(List<DTOTipoTramite> listaTiposTramite) {
        this.listaTiposTramite = listaTiposTramite;
    }

    public int getCodTipoTramiteSeleccionado() {
        return codTipoTramiteSeleccionado;
    }

    public void setCodTipoTramiteSeleccionado(int codTipoTramiteSeleccionado) {
        this.codTipoTramiteSeleccionado = codTipoTramiteSeleccionado;
    }


    public void setDtoResumen(DTOResumen dtoResumen) {
        this.dtoResumen = dtoResumen;
    }

    public int getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(int numeroTramite) {
        this.numeroTramite = numeroTramite;
    }
    
    
    
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
    public String ingresarDNI() {
        try {
            dtoCliente = controladorRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
            return "confirmarCliente?faces-redirect=true"; //
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            return null; // Permanecer en la misma página
        }
    } 
    
    public String confirmarCliente() {
        return "seleccionarCategoria?faces-redirect=true"; //?faces-redirect=true
    }

    public String seleccionarCategoria() {
        if (codCategoriaTipoTramiteSeleccionada != 0) {
            return "seleccionarTipoTramite?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar una categoría.";
            return null;
        }
    }    
    
    public String seleccionarTipoTramite() {
        if (codTipoTramiteSeleccionado != 0) {
            return "mostrarResumen?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar un tipo de trámite.";
            return null;
        }
    }
    
    public DTOResumen getDtoResumen() {
        if (dtoResumen == null && codTipoTramiteSeleccionado != 0) {
            try {
                dtoResumen = controladorRegistrarTramiteWeb.mostrarResumenTipoTramite(codTipoTramiteSeleccionado);
            } catch (RegistrarTramiteWebException e) {
                mensajeError = e.getMessage();
                // Manejar el error, por ejemplo, redirigir a una página de error o mostrar un mensaje en la vista
                // Aquí puedes decidir cómo manejarlo, por ahora retornamos null
                return null;
            }
        }
        return dtoResumen;
    }
    
    
    public String confirmarTramite() {
        try {
            DTONumeroTramite dtoNumeroTramite = controladorRegistrarTramiteWeb.registrarTramite();
            numeroTramite = dtoNumeroTramite.getNumeroTramite();
            // Guardar el número de trámite en un atributo si es necesario
            // Redirigir al siguiente paso (mostrar número de trámite)
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
    dniCliente = 0;
    dtoCliente = null;
    mensajeError = null;
    listaCategorias = null;
    codCategoriaTipoTramiteSeleccionada = 0;
    listaTiposTramite = null;
    codTipoTramiteSeleccionado = 0;
    dtoResumen = null;
    numeroTramite = 0;
    }   
}
