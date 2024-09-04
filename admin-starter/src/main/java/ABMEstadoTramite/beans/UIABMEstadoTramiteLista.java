/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite.beans;

import ABMEstado.beans.EstadoGrillaUI;
import ABMEstadoTramite.ControladorABMEstadoTramite;
import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
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

    public void filtrar() {

    }

    public List<EstadoTramiteGrillaUI> buscarEstadoTramites() {
        System.out.println(codigoFiltro);
        System.out.println(nombreFiltro);

        List<EstadoTramiteGrillaUI> estadosTramiteGrilla = new ArrayList<>();
        List<EstadoTramiteDTO> estadosTramiteDTO = controladorABMEstadoTramite.buscarEstadosTramite(codigoFiltro, nombreFiltro);
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
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }

}
