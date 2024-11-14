/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite.beans;

import ABMEstadoTramite.ControladorABMEstadoTramite;
import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

/**
 *
 * @author matis
 */
@Named("uiabmEstadoTramiteLista")
@ViewScoped
public class UIABMEstadoTramiteLista implements Serializable {

    private ControladorABMEstadoTramite controladorABMEstadoTramite = new ControladorABMEstadoTramite();
    private int codigoFiltro = 0;
    private String nombreFiltro = "";
    private String descripcionFiltro = "";

    public ControladorABMEstadoTramite getControladorABMEstadoTramite() {
        return controladorABMEstadoTramite;
    }

    public void setControladorABMEstadoTramite(ControladorABMEstadoTramite controladorABMEstadoTramite) {
        this.controladorABMEstadoTramite = controladorABMEstadoTramite;
    }

    public int getCodigoFiltro() {
        return codigoFiltro;
    }

    public void setCodigoFiltro(int codigoFiltro) {
        this.codigoFiltro = codigoFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public String getDescripcionFiltro() {
        return descripcionFiltro;
    }

    public void setDescripcionFiltro(String descripcionFiltro) {
        this.descripcionFiltro = descripcionFiltro;
    }
    
    public void filtrar() {

    }

    public List<EstadoTramiteGrillaUI> buscarEstadosTramite() {
        System.out.println(codigoFiltro);
        System.out.println(nombreFiltro);
        System.out.println(descripcionFiltro);

        List<EstadoTramiteGrillaUI> estadosTramiteGrilla = new ArrayList<>();
        List<EstadoTramiteDTO> estadosTramiteDTO = controladorABMEstadoTramite.buscarEstadosTramite(codigoFiltro, nombreFiltro, descripcionFiltro);
        for (EstadoTramiteDTO estadoTramiteDTO : estadosTramiteDTO) {
            EstadoTramiteGrillaUI estadoTramiteGrillaUI = new EstadoTramiteGrillaUI();
            estadoTramiteGrillaUI.setCodEstadoTramite(estadoTramiteDTO.getCodEstadoTramite());
            estadoTramiteGrillaUI.setNombreEstadoTramite(estadoTramiteDTO.getNombreEstadoTramite());
            estadoTramiteGrillaUI.setDescripcionEstadoTramite(estadoTramiteDTO.getDescripcionEstadoTramite());
            estadoTramiteGrillaUI.setFechaHoraAltaEstadoTramite(estadoTramiteDTO.getFechaHoraAltaEstadoTramite());
            estadoTramiteGrillaUI.setFechaHoraBajaEstadoTramite(estadoTramiteDTO.getFechaHoraBajaEstadoTramite());
            estadosTramiteGrilla.add(estadoTramiteGrillaUI);
        }
        return estadosTramiteGrilla;
    }

    public String irAgregarEstadoTramite() {
        BeansUtils.guardarUrlAnterior();
        return "abmEstadoTramite?faces-redirect=true&codEstadoTramite=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public String irModificarEstadoTramite(int codEstadoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "abmEstadoTramite?faces-redirect=true&codEstadoTramite=" + codEstadoTramite; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaEstadoTramite(int codEstadoTramite) {
        try {
            controladorABMEstadoTramite.darDeBajaEstadoTramite(codEstadoTramite);
            Messages.create("Anulado").detail("Anulado").add();;

        } catch (EstadoTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
        }
    }

}
