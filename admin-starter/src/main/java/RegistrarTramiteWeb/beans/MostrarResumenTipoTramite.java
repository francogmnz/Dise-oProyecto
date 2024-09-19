/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTONumeroTramite;
import RegistrarTramiteWeb.dtos.DTOResumen;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author licciardi
 */

@Named("mostrarResumenTipoTramite")
@SessionScoped
public class MostrarResumenTipoTramite implements Serializable {

    private ConfirmarTipoTramiteGrillaUI resumenGrilla;
    private ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb = new ControladorRegistrarTramiteWeb();

    private int codTipoTramiteSeleccionado;
    private DTOResumen dtoResumen;

   

@PostConstruct
public void init() {
    Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
    DTOTipoTramite dtoTipoTramiteSeleccionado = (DTOTipoTramite) flash.get("tipoTramiteSeleccionado");

    if (dtoTipoTramiteSeleccionado != null) {
        try {
            codTipoTramiteSeleccionado = dtoTipoTramiteSeleccionado.getCodTipoTramite();
            dtoResumen = controladorRegistrarTramiteWeb.mostrarResumenTipoTramite(codTipoTramiteSeleccionado);
        } catch (RegistrarTramiteWebException e) {
            
            e.printStackTrace();  
        }
    } else {
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("seleccionarTipoTramite.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    public DTOResumen getDtoResumen() {
        return dtoResumen;
    }

}
  /*  
    public ConfirmarTipoTramiteGrillaUI mostrarResumen(){
        ConfirmarTipoTramiteGrillaUI resumenGrilla = new ConfirmarTipoTramiteGrillaUI();
        resumenGrilla.setDniCliente(dtoResumen.getDniCliente());
        resumenGrilla.setNombreCliente(dtoResumen.getNombreCliente());
        resumenGrilla.setApellidoCliente(dtoResumen.getApellidoCliente());
        resumenGrilla.setMailCliente(dtoResumen.getMailCliente());
        resumenGrilla.setNombreTipoTramite(dtoResumen.getNombreTipoTramite());
        resumenGrilla.setDescripcionTipoTramite(dtoResumen.getDescripcionTipoTramite());
        resumenGrilla.setPlazoEntregaDocumentacionTT(dtoResumen.getPlazoEntregaDocumentacionTT());
        resumenGrilla.setPrecioTramite(dtoResumen.getPrecioTramite());
        
        return resumenGrilla;
   }
    */

    /*
    public void confirmar() {
        try {
            DTONumeroTramite dtoNumeroTramite = controladorRegistrarTramiteWeb.registrarTramite(codTipoTramiteSeleccionado);
            Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
            flash.put("numeroTramite", dtoNumeroTramite.getNumeroTramite());
            FacesContext.getCurrentInstance().getExternalContext().redirect("mostrarNumeroTramite.xhtml");
        } catch (RegistrarTramiteWebException | IOException e) {
            e.printStackTrace();
            // Manejar la excepción adecuadamente
        }
    }

    public void cancelar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/