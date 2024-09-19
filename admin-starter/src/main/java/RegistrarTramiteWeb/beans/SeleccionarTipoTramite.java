/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author licciardi
 */


@Named("seleccionarTipoTramite")
@SessionScoped
public class SeleccionarTipoTramite implements Serializable {


    private ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb = new ControladorRegistrarTramiteWeb();

    
    @PostConstruct
    public void init() {
        Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        dtoCategoriaSeleccionada = (DTOCategoriaTipoTramite) flash.get("categoriaSeleccionada");
        if (dtoCategoriaSeleccionada != null) {
            codCategoriaSeleccionada = dtoCategoriaSeleccionada.getCodCategoriaTipoTramite();
        } else {
            // manejo el error
        }
    }    

    public SeleccionarTipoTramite() {
    }

    DTOCategoriaTipoTramite dtoCategoriaSeleccionada = (DTOCategoriaTipoTramite) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("categoriaSeleccionada");
    int codCategoriaSeleccionada = dtoCategoriaSeleccionada.getCodCategoriaTipoTramite();
    public List<TipoTramiteGrillaUI> listarTipoTramites(){
    List<TipoTramiteGrillaUI> tipoTramiteGrilla = new ArrayList<>();
    List<DTOTipoTramite> dtoTipoTramites = controladorRegistrarTramiteWeb.listarTipoTramites(codCategoriaSeleccionada);
    
    for(DTOTipoTramite dtoTT: dtoTipoTramites){
        TipoTramiteGrillaUI tipoTramiteGrillaUI = new TipoTramiteGrillaUI();
        tipoTramiteGrillaUI.setCodTipoTramite(dtoTT.getCodTipoTramite());
        tipoTramiteGrillaUI.setNombreTipoTramite(dtoTT.getNombreTipoTramite());
        tipoTramiteGrillaUI.setDescripcionTipoTramite(dtoTT.getDescripcionTipoTramite());
        tipoTramiteGrillaUI.setDescripcionWebTipoTramite(dtoTT.getDescripcionWebTipoTramite());
        tipoTramiteGrillaUI.setDocumentaciones(dtoTT.getDocumentaciones());
        
        tipoTramiteGrilla.add(tipoTramiteGrillaUI);
    
    }
    
    return tipoTramiteGrilla;
    
    }
    


    // Método para seleccionar el trámite
    public void seleccionarTipoTramite(TipoTramiteGrillaUI tramite) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("codTipoTramiteSeleccionado", tramite.getCodTipoTramite());
            FacesContext.getCurrentInstance().getExternalContext().redirect("confirmarTipoTramiteTramite.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para cancelar la selección
    public void cancelar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}