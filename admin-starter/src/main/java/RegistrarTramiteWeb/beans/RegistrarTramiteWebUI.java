/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import RegistrarTramiteWeb.dtos.DTOCliente;
import RegistrarTramiteWeb.dtos.DTODocumentacion;
import RegistrarTramiteWeb.dtos.DTONumeroTramite;
import RegistrarTramiteWeb.dtos.DTOResumen;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;

    private boolean confirmaCliente;
    
    private int codCategoriaSeleccionada;
    
    private int codTipoTramiteSeleccionado;
   
    private String nombreTipoTramite;
    private String descripcionTipoTramite;
    private int plazoEntregaDocumentacionTT;
    private double precioTramite;

    
    private int numeroTramite;
    private List<DTODocumentacion> documentaciones = new ArrayList<>();

    private List<CategoriaGrillaUI> categoriasGrilla;
    private List<TipoTramiteGrillaUI> tiposTramiteGrilla;

    
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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public boolean isConfirmaCliente() {
        return confirmaCliente;
    }

    public void setConfirmaCliente(boolean confirmaCliente) {
        this.confirmaCliente = confirmaCliente;
    }

    public int getCodCategoriaSeleccionada() {
        return codCategoriaSeleccionada;
    }

    public void setCodCategoriaSeleccionada(int codCategoriaSeleccionada) {
        this.codCategoriaSeleccionada = codCategoriaSeleccionada;
    }

    public int getCodTipoTramiteSeleccionado() {
        return codTipoTramiteSeleccionado;
    }

    public void setCodTipoTramiteSeleccionado(int codTipoTramiteSeleccionado) {
        this.codTipoTramiteSeleccionado = codTipoTramiteSeleccionado;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    public int getPlazoEntregaDocumentacionTT() {
        return plazoEntregaDocumentacionTT;
    }

    public void setPlazoEntregaDocumentacionTT(int plazoEntregaDocumentacionTT) {
        this.plazoEntregaDocumentacionTT = plazoEntregaDocumentacionTT;
    }

    public double getPrecioTramite() {
        return precioTramite;
    }

    public void setPrecioTramite(double precioTramite) {
        this.precioTramite = precioTramite;
    }

    public int getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(int numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public List<DTODocumentacion> getDocumentaciones() {
        return documentaciones;
    }

    public void setDocumentaciones(List<DTODocumentacion> documentaciones) {
        this.documentaciones = documentaciones;
    }
 
    public List<CategoriaGrillaUI> getCategoriasGrilla() {
        return categoriasGrilla;
    }

    public void setCategoriasGrilla(List<CategoriaGrillaUI> categoriasGrilla) {
        this.categoriasGrilla = categoriasGrilla;
    }
    
    public List<TipoTramiteGrillaUI> getTiposTramiteGrilla() {
        return tiposTramiteGrilla;
    }

    public void setTiposTramiteGrilla(List<TipoTramiteGrillaUI> tiposTramiteGrilla) {
        this.tiposTramiteGrilla = tiposTramiteGrilla;
    }

    public void cargarClienteBuscado() throws RegistrarTramiteWebException {
        DTOCliente clienteBuscado = controladorRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
        if (clienteBuscado != null) {
            dniCliente = clienteBuscado.getDniCliente();
            nombreCliente = clienteBuscado.getNombreCliente();
            apellidoCliente = clienteBuscado.getApellidoCliente();
            mailCliente = clienteBuscado.getMailCliente();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cliente no encontrado."));
        }
    }    
    
    public String ingresarDNI() {
        try {
            cargarClienteBuscado();
            return "confirmarCliente?faces-redirect=true";
        } catch (RegistrarTramiteWebException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }
 
    public String volverIngresarDNI() {
        resetearIngresarDNI();
        return "ingresarDNI?faces-redirect=true";
    }    
    
    public void cargarCategoriasTipoTramite() throws RegistrarTramiteWebException {
        categoriasGrilla = new ArrayList<>();
        List<DTOCategoriaTipoTramite> categoriasTipoTramiteDTO = controladorRegistrarTramiteWeb.listarCategoriasTipoTramite();
        for (DTOCategoriaTipoTramite dtoCategoriaTT : categoriasTipoTramiteDTO) {
            CategoriaGrillaUI categoriaUI = new CategoriaGrillaUI();
            categoriaUI.setCodCategoriaTipoTramite(dtoCategoriaTT.getCodCategoriaTipoTramite());
            categoriaUI.setNombreCategoriaTipoTramite(dtoCategoriaTT.getNombreCategoriaTipoTramite());
            categoriaUI.setDescripcionCategoriaTipoTramite(dtoCategoriaTT.getDescripcionCategoriaTipoTramite());
            categoriaUI.setDescripcionWebCategoriaTipoTramite(dtoCategoriaTT.getDescripcionWebCategoriaTipoTramite());
            categoriasGrilla.add(categoriaUI);
        }
    }

    public String confirmarCliente() {
        if (!confirmaCliente) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe confirmar que usted es el cliente mostrado."));
            return null;
        }
        try {
            cargarCategoriasTipoTramite();
            return "seleccionarCategoria?faces-redirect=true";
        } catch (RegistrarTramiteWebException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error - ", e.getMessage()));
            return null;
        }
    }

    public String volverConfirmarCliente() {
        resetearConfirmarCliente();
        return "confirmarCliente?faces-redirect=true";
    }    
    
    public void cargarTiposTramites() throws RegistrarTramiteWebException {
        tiposTramiteGrilla = new ArrayList<>();
        List<DTOTipoTramite> tipoTramitesDTO = controladorRegistrarTramiteWeb.listarTipoTramites(codCategoriaSeleccionada);
        for (DTOTipoTramite tipoTramiteDTO : tipoTramitesDTO) {
            TipoTramiteGrillaUI tipoTramiteGrillaUI = new TipoTramiteGrillaUI();
            tipoTramiteGrillaUI.setCodTipoTramite(tipoTramiteDTO.getCodTipoTramite());
            tipoTramiteGrillaUI.setNombreTipoTramite(tipoTramiteDTO.getNombreTipoTramite());
            tipoTramiteGrillaUI.setDescripcionTipoTramite(tipoTramiteDTO.getDescripcionTipoTramite());
            tipoTramiteGrillaUI.setDescripcionWebTipoTramite(tipoTramiteDTO.getDescripcionWebTipoTramite());
            tipoTramiteGrillaUI.setDocumentaciones(tipoTramiteDTO.getDocumentaciones());
            tiposTramiteGrilla.add(tipoTramiteGrillaUI);
        }
    }
    
    public String seleccionarCategoria() {
        if (codCategoriaSeleccionada != 0) {
            try {
                cargarTiposTramites(); 
                return "seleccionarTipoTramite?faces-redirect=true"; 
            } catch (RegistrarTramiteWebException e) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar una categoría."));            
            return null;
        }
    }
   
    public String volverSeleccionarCategoria() {
        resetearSeleccionarCategoria();
        return "seleccionarCategoria?faces-redirect=true";
    }    
    
    public void cargarResumen() throws RegistrarTramiteWebException {
        DTOResumen dtoResumen = controladorRegistrarTramiteWeb.mostrarResumenTipoTramite(codTipoTramiteSeleccionado);

        dniCliente = dtoResumen.getDniCliente();
        nombreCliente = dtoResumen.getNombreCliente();
        apellidoCliente = dtoResumen.getApellidoCliente();
        mailCliente = dtoResumen.getMailCliente();
        nombreTipoTramite = dtoResumen.getNombreTipoTramite();
        descripcionTipoTramite = dtoResumen.getDescripcionTipoTramite();
        plazoEntregaDocumentacionTT = dtoResumen.getPlazoEntregaDocumentacionTT();
        precioTramite = dtoResumen.getPrecioTramite();
    }
      
    public String seleccionarTipoTramite() {
        if (codTipoTramiteSeleccionado != 0) {
            try {
                cargarResumen(); 
                return "mostrarResumen?faces-redirect=true";
            } catch (RegistrarTramiteWebException e) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));           
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe seleccionar un tipo de trámite."));             
            return null;
        }
    }
    
    public String volverSeleccionarTipoTramite() {
        resetearSeleccionarTipoTramite();
        return "seleccionarTipoTramite?faces-redirect=true";
    }   
    
    public void cargarNumeroTramite() throws RegistrarTramiteWebException {
        DTONumeroTramite dtoNumeroTramite = controladorRegistrarTramiteWeb.registrarTramite();

        numeroTramite = dtoNumeroTramite.getNumeroTramite();
        plazoEntregaDocumentacionTT = dtoNumeroTramite.getPlazoEntregaDocumentacionTT();
        documentaciones = dtoNumeroTramite.getDocumentaciones();
    }
      
    public String confirmarTramite() {
        try {
            cargarNumeroTramite(); 
            return "mostrarNumeroTramite?faces-redirect=true"; 
        } catch (RegistrarTramiteWebException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));            
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
    
    private void resetearEstado() {
        dniCliente = 0;
        nombreCliente = null;
        apellidoCliente = null;
        mailCliente = null;
        confirmaCliente = false;
        codCategoriaSeleccionada = 0;
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0.0;
        numeroTramite = 0;
        documentaciones.clear();
        categoriasGrilla = new ArrayList<>();
        tiposTramiteGrilla = new ArrayList<>();
    }

    private void resetearIngresarDNI() {
        resetearEstado();
    }

    private void resetearConfirmarCliente() {
        confirmaCliente = false;
        codCategoriaSeleccionada = 0;
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0.0;
        numeroTramite = 0;
        documentaciones.clear();
        categoriasGrilla = new ArrayList<>();
        tiposTramiteGrilla = new ArrayList<>();
    }

    private void resetearSeleccionarCategoria() {
        codCategoriaSeleccionada = 0;
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0.0;
        numeroTramite = 0;
        documentaciones.clear();
        tiposTramiteGrilla = new ArrayList<>();
    }

    private void resetearSeleccionarTipoTramite() {
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0.0;
        numeroTramite = 0;
        documentaciones.clear();
    }

    public void verificarEstadoEnIngresarDNI() {
        resetearEstado();
    }
    public void verificarEstadoEnConfirmarCliente() {
        if (nombreCliente == null) {
            resetearEstado();
            redirect("ingresarDNI.jsf", "Debe ingresar su DNI antes de confirmar el cliente.");
        }
    }
    public void verificarEstadoEnSeleccionarCategoria() {
        if (nombreCliente == null) {
            resetearEstado();
            redirect("ingresarDNI.jsf", "Debe ingresar su DNI antes de seleccionar una categoria.");
        } else if (!confirmaCliente) {
            resetearConfirmarCliente();
            redirect("confirmarCliente.jsf", "Debe confirmar que usted es el cliente antes de continuar.");
        }
    }

    public void verificarEstadoEnSeleccionarTipoTramite() {
        if (codCategoriaSeleccionada == 0) {
            resetearSeleccionarCategoria();
            redirect("seleccionarCategoria.jsf", "Debe seleccionar una categoria antes de elegir un tipo de tramite.");
        }
    }

    public void verificarEstadoEnResumen() {
        if (codTipoTramiteSeleccionado == 0) {
            resetearSeleccionarTipoTramite();
            redirect("seleccionarTipoTramite.jsf", "Debe seleccionar un tipo de tramite antes de ver el resumen.");
        }
    } 
    
    public void verificarEstadoEnMostrarNumeroTramite() {
        if (numeroTramite == 0) {
            resetearEstado();
            redirect("ingresarDNI.jsf", "No hay un tramite registrado para mostrar. Por favor, inicie el proceso nuevamente.");
        }
    }

    private void redirect(String pagina, String mensaje) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(pagina);
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {
            Messages.create("Error al redirigir a " + pagina).fatal().add();
        }
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, null));
        }
    }

// Borrar desde aqui - Casos de prueba Testing
   
    private int codigoCategoriaTTPrueba;
    private int codTipoTramitePrueba;


    public int getCodigoCategoriaTTPrueba() {
        return codigoCategoriaTTPrueba;
    }

    public void setCodigoCategoriaTTPrueba(int codigoCategoriaTTPrueba) {
        this.codigoCategoriaTTPrueba = codigoCategoriaTTPrueba;
    }

    public int getCodTipoTramitePrueba() {
        return codTipoTramitePrueba;
    }

    public void setCodTipoTramitePrueba(int codTipoTramitePrueba) {
        this.codTipoTramitePrueba = codTipoTramitePrueba;
    }

    public String probarSeleccionarCategoria() {

        codCategoriaSeleccionada = codigoCategoriaTTPrueba;
        return seleccionarCategoria();
    }

    public String probarSeleccionarTipoTramite() {

        codTipoTramiteSeleccionado = codTipoTramitePrueba;
        return seleccionarTipoTramite();
    }


}
