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
    private int precioTramite;

    
    private int numeroTramite;
    private List<DTODocumentacion> documentaciones = new ArrayList<>();

     
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

    public int getPrecioTramite() {
        return precioTramite;
    }

    public void setPrecioTramite(int precioTramite) {
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
 
    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    
    public String ingresarDNI() {
        try {
            DTOCliente clienteBuscado = controladorRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
            mensajeError = null;
            if (clienteBuscado != null) {
                dniCliente = clienteBuscado.getDniCliente();
                nombreCliente = clienteBuscado.getNombreCliente();
                apellidoCliente = clienteBuscado.getApellidoCliente();
                mailCliente = clienteBuscado.getMailCliente();
                                       
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

    public String volverIngresarDNI() {
        resetearIngresarDNI();
        return "ingresarDNI?faces-redirect=true";
    }    
    
    public String confirmarCliente() {
        if (!confirmaCliente){
            mensajeError = "Debe confirmar que usted es el cliente mostrado.";
            return null;    
        }
        mensajeError = null;
        listarCategoriasTipoTramite();
        return "seleccionarCategoria?faces-redirect=true"; //?faces-redirect=true
    }

    public String volverConfirmarCliente() {
        resetearConfirmarCliente();
        return "confirmarCliente?faces-redirect=true";
    }    
    

    
    public List<CategoriaGrillaUI> listarCategoriasTipoTramite() {
        List<CategoriaGrillaUI> categoriasGrilla = new ArrayList<>();
        try {
            List<DTOCategoriaTipoTramite> categoriasTipoTramiteDTO = controladorRegistrarTramiteWeb.listarCategoriasTipoTramite();
            for (DTOCategoriaTipoTramite DTOCategoriaTT : categoriasTipoTramiteDTO) {
                
                CategoriaGrillaUI categoriaUI = new CategoriaGrillaUI();
                categoriaUI.setCodCategoriaTipoTramite(DTOCategoriaTT.getCodCategoriaTipoTramite());
                categoriaUI.setNombreCategoriaTipoTramite(DTOCategoriaTT.getNombreCategoriaTipoTramite());
                categoriaUI.setDescripcionCategoriaTipoTramite(DTOCategoriaTT.getDescripcionCategoriaTipoTramite());
                categoriaUI.setDescripcionWebCategoriaTipoTramite(DTOCategoriaTT.getDescripcionWebCategoriaTipoTramite());

                categoriasGrilla.add(categoriaUI);
            }
            mensajeError = null;
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            categoriasGrilla = new ArrayList<>();
        }
        return categoriasGrilla;
    }
    
    public String seleccionarCategoria() {
        if (codCategoriaSeleccionada != 0) {
            listarTipoTramites();
            return "seleccionarTipoTramite?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar una categoría.";
            return null;
        }
    }  
    
    public String volverSeleccionarCategoria() {
        resetearSeleccionarCategoria();
        return "seleccionarCategoria?faces-redirect=true";
    }    
    

    
    
    public List<TipoTramiteGrillaUI> listarTipoTramites() {
        List<TipoTramiteGrillaUI> tiposTramiteGrilla = new ArrayList<>();
        try {
            List<DTOTipoTramite> tipoTramitesDTO = controladorRegistrarTramiteWeb.listarTipoTramites(codCategoriaSeleccionada);
            for (DTOTipoTramite tipoTramiteDTO : tipoTramitesDTO) {
                TipoTramiteGrillaUI tipoTramiteGrilla = new TipoTramiteGrillaUI();
                tipoTramiteGrilla.setCodTipoTramite(tipoTramiteDTO.getCodTipoTramite());
                tipoTramiteGrilla.setNombreTipoTramite(tipoTramiteDTO.getNombreTipoTramite());
                tipoTramiteGrilla.setDescripcionTipoTramite(tipoTramiteDTO.getDescripcionTipoTramite());
                tipoTramiteGrilla.setDescripcionWebTipoTramite(tipoTramiteDTO.getDescripcionWebTipoTramite());
                tipoTramiteGrilla.setDocumentaciones(tipoTramiteDTO.getDocumentaciones());

                tiposTramiteGrilla.add(tipoTramiteGrilla);
                
            }
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            tiposTramiteGrilla = null;
        }
        return tiposTramiteGrilla;
    }
    
    public String seleccionarTipoTramite() {
        if (codTipoTramiteSeleccionado != 0) {
            irAResumen();
            return "mostrarResumen?faces-redirect=true"; //?faces-redirect=true
        } else {
            mensajeError = "Debe seleccionar un tipo de trámite.";
            return null;
        }
    }
    
    public String volverSeleccionarTipoTramite() {
        resetearSeleccionarTipoTramite();
        return "seleccionarTipoTramite?faces-redirect=true";
    }   
    
 
    public void irAResumen() {
        try {
            DTOResumen dtoResumen = controladorRegistrarTramiteWeb.mostrarResumenTipoTramite(codTipoTramiteSeleccionado);
            
            dniCliente = dtoResumen.getDniCliente();
            nombreCliente = dtoResumen.getNombreCliente();
            apellidoCliente = dtoResumen.getApellidoCliente();
            mailCliente = dtoResumen.getMailCliente();
            
            nombreTipoTramite = dtoResumen.getNombreTipoTramite();
            descripcionTipoTramite = dtoResumen.getDescripcionTipoTramite();
            plazoEntregaDocumentacionTT = dtoResumen.getPlazoEntregaDocumentacionTT();
            
            precioTramite = dtoResumen.getPrecioTramite();
            

            
        } catch (RegistrarTramiteWebException e) {
            mensajeError = e.getMessage();
            //resumenUI = null;
        }
    }    


    
    
    public String confirmarTramite() {
        try {
            DTONumeroTramite dtoNumeroTramite = controladorRegistrarTramiteWeb.registrarTramite();

            numeroTramite = dtoNumeroTramite.getNumeroTramite();
            plazoEntregaDocumentacionTT = dtoNumeroTramite.getPlazoEntregaDocumentacionTT();
            documentaciones =dtoNumeroTramite.getDocumentaciones();
 
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
        nombreCliente = null;
        apellidoCliente = null;
        mailCliente = null;
        codCategoriaSeleccionada = 0;
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0;
        numeroTramite = 0;
        documentaciones = null;
        confirmaCliente = false;
        mensajeError = null;
    }

    private void resetearIngresarDNI() {
        dniCliente = 0;
        nombreCliente = null;
        apellidoCliente = null;
        mailCliente = null;
        mensajeError = null;
    }

    private void resetearConfirmarCliente() {
        confirmaCliente = false;
        codCategoriaSeleccionada = 0;
        codTipoTramiteSeleccionado = 0;
        mensajeError = null;

        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0;
    }

    private void resetearSeleccionarCategoria() {
        codTipoTramiteSeleccionado = 0;
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0;
        mensajeError = null;
        documentaciones = null; 
    }

    private void resetearSeleccionarTipoTramite() {
        nombreTipoTramite = null;
        descripcionTipoTramite = null;
        plazoEntregaDocumentacionTT = 0;
        precioTramite = 0;
        mensajeError = null;
    }

}
