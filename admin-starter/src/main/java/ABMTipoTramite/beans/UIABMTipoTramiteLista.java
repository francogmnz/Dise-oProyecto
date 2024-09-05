/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite.beans;

import ABMTipoTramite.beans.*;
import ABMTipoTramite.ControladorABMTipoTramite;
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

            tipoTramitesGrilla.add(tipoTramiteGrillaUI);
        }
        return tipoTramitesGrilla;
    }

    public String irAgregarTipoTramite() {
        BeansUtils.guardarUrlAnterior();
        return "abmTipoTramite?faces-redirect=true&codTipoTramite=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    
    public String irModificarTipoTramite(int codTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "abmTipoTramite?faces-redirect=true&codTipoTramite=" + codTipoTramite; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaTipoTramite(int codTipoTramite){
        try {
            controladorABMTipoTramite.darDeBajaTipoTramite(codTipoTramite);
            Messages.create("Anulado").detail("Anulado").add();;
                    
        } catch (TipoTramiteException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }
    
}
