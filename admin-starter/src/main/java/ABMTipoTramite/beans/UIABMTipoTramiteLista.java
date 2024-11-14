/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite.beans;

//import ABMDocumentacion.dtos.DocumentacionDTO;
import ABMTipoTramite.dtos.DocumentacionDTO;
import ABMTipoTramite.beans.*;
import ABMTipoTramite.ControladorABMTipoTramite;
import ABMTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.TipoTramiteDTO;
import ABMTipoTramite.exceptions.TipoTramiteException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

/**
 *
 * @author licciardi
 */
@Named("uiabmTipoTramiteLista")
@ViewScoped
public class UIABMTipoTramiteLista implements Serializable {

    private ControladorABMTipoTramite controladorABMTipoTramite = new ControladorABMTipoTramite();
    private int codTipoTramiteFiltro=0;
    private String nombreTipoTramiteFiltro="";
    private String criterio = "";

    public ControladorABMTipoTramite getControladorABMTipoTramite() {
        return controladorABMTipoTramite;
    }

    public void setControladorABMTipoTramite(ControladorABMTipoTramite controladorABMTipoTramite) {
        this.controladorABMTipoTramite = controladorABMTipoTramite;
    }

    public int getCodTipoTramiteFiltro() {
        return codTipoTramiteFiltro;
    }

    public void setCodTipoTramiteFiltro(int codTipoTramiteFiltro) {
        this.codTipoTramiteFiltro = codTipoTramiteFiltro;
    }

    public String getNombreTipoTramiteFiltro() {
        return nombreTipoTramiteFiltro;
    }

    public void setNombreTipoTramiteFiltro(String nombreTipoTramiteFiltro) {
        this.nombreTipoTramiteFiltro = nombreTipoTramiteFiltro;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
    
    
    public void filtrar()
    {

    }

    public List<TipoTramiteGrillaUI> buscarTipoTramites(){
        System.out.println(codTipoTramiteFiltro);
        System.out.println(nombreTipoTramiteFiltro);
        List<TipoTramiteGrillaUI> tipoTramitesGrilla = new ArrayList<>();
        List<TipoTramiteDTO> tipoTramitesDTO = controladorABMTipoTramite.buscarTipoTramites(codTipoTramiteFiltro, nombreTipoTramiteFiltro);
        for (TipoTramiteDTO tipoTramiteDTO : tipoTramitesDTO) {
            TipoTramiteGrillaUI tipoTramiteGrillaUI = new TipoTramiteGrillaUI();
            tipoTramiteGrillaUI.setCodTipoTramite(tipoTramiteDTO.getCodTipoTramite());
            tipoTramiteGrillaUI.setNombreTipoTramite(tipoTramiteDTO.getNombreTipoTramite());
            tipoTramiteGrillaUI.setDescripcionTipoTramite(tipoTramiteDTO.getDescripcionTipoTramite());
            tipoTramiteGrillaUI.setDescripcionWebTipoTramite(tipoTramiteDTO.getDescripcionWebTipoTramite());
            tipoTramiteGrillaUI.setFechaHoraBajaTipoTramite(tipoTramiteDTO.getFechaHoraBajaTipoTramite());
            tipoTramiteGrillaUI.setPlazoEntregaDocumentacionTT(tipoTramiteDTO.getPlazoEntregaDocumentacionTT());
            tipoTramiteGrillaUI.setNombreCategoriaTipoTramite(tipoTramiteDTO.getCategoriaTipoTramite().getNombreCategoriaTipoTramite());
            
            
            
            tipoTramiteGrillaUI.setDocumentaciones(tipoTramiteDTO.getDocumentacionesDTO());

            tipoTramitesGrilla.add(tipoTramiteGrillaUI);
        }
        return filtrarTipoTramites(tipoTramitesGrilla);
    }

//    public String irAgregarTipoTramite() {
//        BeansUtils.guardarUrlAnterior();
//        return "abmTipoTramite?faces-redirect=true&codTipoTramite=0"; // Usa '?faces-redirect=true' para hacer una redirección
//    }

    public String irAgregarTipoTramite() {
    try {

        //List<CategoriaTipoTramiteDTO> categoriasActivas = controladorABMTipoTramite.obtenerCategoriasTipoTramiteActivas();
        List<CategoriaTipoTramiteDTO> categorias = controladorABMTipoTramite.obtenerCategoriasTipoTramite();
        
        //List<DocumentacionDTO> documentacionesActivas = controladorABMTipoTramite.obtenerDocumentacionesActivas();
        List<DocumentacionDTO> documentaciones = controladorABMTipoTramite.obtenerDocumentaciones();
        
        if (categorias.isEmpty() || documentaciones.isEmpty()) {
            Messages.create("Error").error().detail("No es posible agregar un nuevo Tipo de Trámite porque no hay categorías o documentaciones disponibles.").add();
            return null; 
        } else {
            BeansUtils.guardarUrlAnterior();
            return "abmTipoTramite?faces-redirect=true&codTipoTramite=0";
        }
    } catch (TipoTramiteException e) {
        Messages.create("Error").error().detail(e.getMessage()).add();
        return null; 
    }
}

    
    public String irModificarTipoTramite(int codTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "abmTipoTramite?faces-redirect=true&codTipoTramite=" + codTipoTramite; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaTipoTramite(int codTipoTramite){
        try {
            controladorABMTipoTramite.darDeBajaTipoTramite(codTipoTramite);
            Messages.create("Exito").detail("Tipo Tramite dado de baja correctamente.").add();
                    
        } catch (TipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
        }
    }
 
    
    public boolean isAnulada(TipoTramiteGrillaUI tipoTramiteEnviado) {
        if (tipoTramiteEnviado.getFechaHoraBajaTipoTramite() != null) {
            return true;
        } else {
            return false;
        }
    }    
    
    public List<TipoTramiteGrillaUI> filtrarTipoTramites(List<TipoTramiteGrillaUI> tipoTramitesGrilla) {
        List<TipoTramiteGrillaUI> tipoTramitesActivos = new ArrayList<>();
        List<TipoTramiteGrillaUI> tipoTramitesTInactivos = new ArrayList<>();
        for (TipoTramiteGrillaUI tipoTramite : tipoTramitesGrilla) {
            if (tipoTramite.getFechaHoraBajaTipoTramite() == null) {
                tipoTramitesActivos.add(tipoTramite);
            } else {
                tipoTramitesTInactivos.add(tipoTramite);
            }
        }
        switch (criterio) {
            case "tipoTramitesActivos":                
                return tipoTramitesActivos;
            case "tipoTramitesTInactivos":
                return tipoTramitesTInactivos;
            default:
                return tipoTramitesGrilla;
        }
    }      
    
}
