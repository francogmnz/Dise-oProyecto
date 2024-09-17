package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.TramiteDTO;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.EstadoTramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.FachadaPersistencia;

@Named("uitramiteLista")
@ViewScoped
public class UITramiteLista implements Serializable {

    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    private int nroTramiteFiltro = 0;
    private int dniFiltro = 0;
    private Date fechaRecepcionTramiteFiltro = null;
    private String nombreEstadoFiltro = "";

    // parte del TipoTramite
    private int codTipoTramiteFiltro = 0;
    private String nombreTipoTramiteFiltro = "";
    private String nombreCategoriaTipoTramiteFiltro = "";
    private String descripcionTipoTramiteFiltro = "";

    private String nombreEstadoSeleccionado;

    public ControladorRegistrarTramite getControladorRegistrarTramite() {
        return controladorRegistrarTramite;
    }

    public void setControladorRegistrarTramite(ControladorRegistrarTramite controladorRegistrarTramite) {
        this.controladorRegistrarTramite = controladorRegistrarTramite;
    }

    public int getNroTramiteFiltro() {
        return nroTramiteFiltro;
    }

    public void setNroTramiteFiltro(int nroTramiteFiltro) {
        this.nroTramiteFiltro = nroTramiteFiltro;
    }

    public int getDniFiltro() {
        return dniFiltro;
    }

    public void setDniFiltro(int dniFiltro) {
        this.dniFiltro = dniFiltro;
    }

    public Date getFechaRecepcionTramiteFiltro() {
        return fechaRecepcionTramiteFiltro;
    }

    public void setFechaRecepcionTramiteFiltro(Date fechaRecepcionTramiteFiltro) {
        this.fechaRecepcionTramiteFiltro = fechaRecepcionTramiteFiltro;
    }

    public String getNombreEstadoFiltro() {
        return nombreEstadoFiltro;
    }

    public void setNombreEstadoFiltro(String nombreEstadoFiltro) {
        this.nombreEstadoFiltro = nombreEstadoFiltro;
    }

    // parte TipoTramite
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

    public String getNombreCategoriaTipoTramiteFiltro() {
        return nombreCategoriaTipoTramiteFiltro;
    }

    public void setNombreCategoriaTipoTramiteFiltro(String nombreCategoriaTipoTramiteFiltro) {
        this.nombreCategoriaTipoTramiteFiltro = nombreCategoriaTipoTramiteFiltro;
    }

    public String getDescripcionTipoTramiteFiltro() {
        return descripcionTipoTramiteFiltro;
    }

    public void setDescripcionTipoTramiteFiltro(String descripcionTipoTramiteFiltro) {
        this.descripcionTipoTramiteFiltro = descripcionTipoTramiteFiltro;
    }

    public String getNombreEstadoSeleccionado() {
        return nombreEstadoSeleccionado;
    }

    public void setNombreEstadoSeleccionado(String nombreEstadoSeleccionado) {
        this.nombreEstadoSeleccionado = nombreEstadoSeleccionado;
    }

    public void filtrar() {
    }

    public List<TramiteGrillaUI> buscarTramites() {

        System.out.println(nroTramiteFiltro);
        System.out.println(nombreEstadoFiltro);
        System.out.println(nombreTipoTramiteFiltro);
        System.out.println(fechaRecepcionTramiteFiltro);
        System.out.println(dniFiltro);

        if (fechaRecepcionTramiteFiltro != null) {
            Calendar calFiltro = Calendar.getInstance();
            calFiltro.setTime(fechaRecepcionTramiteFiltro);
            calFiltro.set(Calendar.HOUR_OF_DAY, 0);
            calFiltro.set(Calendar.MINUTE, 0);
            fechaRecepcionTramiteFiltro = calFiltro.getTime();
        }

        List<TramiteGrillaUI> tramiteGrilla = new ArrayList<TramiteGrillaUI>();
        List<TramiteDTO> tramiteDTOList = controladorRegistrarTramite.buscarTramites(nroTramiteFiltro, dniFiltro, fechaRecepcionTramiteFiltro, dniFiltro, nombreEstadoFiltro);

        for (TramiteDTO tramiteDTO : tramiteDTOList) {
            TramiteGrillaUI tramiteGrillaUI = new TramiteGrillaUI();
            tramiteGrillaUI.setNroTramite(tramiteDTO.getNroTramite());
            tramiteGrillaUI.setDni(tramiteDTO.getDni());

            tramiteGrillaUI.setNombreEstado(tramiteDTO.getNombreEstado());
            tramiteGrillaUI.setNombreTipoTramite(tramiteDTO.getNombreTipoTramite());
            tramiteGrillaUI.setFechaRecepcionTramite(tramiteDTO.getFechaRecepcionTramite());
            tramiteGrilla.add(tramiteGrillaUI);
        } 

        return tramiteGrilla;
    }

    public List<FiltrosTipoTramiteGrillaUI> buscarTipoTramite() {
        System.out.println(codTipoTramiteFiltro);
        System.out.println(nombreTipoTramiteFiltro);
        System.out.println(nombreCategoriaTipoTramiteFiltro);
        System.out.println(descripcionTipoTramiteFiltro);

        List<FiltrosTipoTramiteGrillaUI> ttGrilla = new ArrayList<FiltrosTipoTramiteGrillaUI>();
        List<TipoTramiteResumenDTO> ttDTOList = controladorRegistrarTramite.buscarTipoTramite(codTipoTramiteFiltro, nombreTipoTramiteFiltro, nombreCategoriaTipoTramiteFiltro, descripcionTipoTramiteFiltro);

        for (TipoTramiteResumenDTO tipoTramiteResumenDTO : ttDTOList) {
            FiltrosTipoTramiteGrillaUI ttGrillaUI = new FiltrosTipoTramiteGrillaUI();
            ttGrillaUI.setCodTipoTramite(tipoTramiteResumenDTO.getCodTipoTramite());
            ttGrillaUI.setNombreTipoTramite(tipoTramiteResumenDTO.getNombreTipoTramite());
            ttGrillaUI.setNombreCategoriaTipoTramite(tipoTramiteResumenDTO.getNombreCategoriaTipoTramite());
            ttGrillaUI.setDescripcionTipoTramite(tipoTramiteResumenDTO.getDescripcionTipoTramite());
            ttGrilla.add(ttGrillaUI);

        }
        return ttGrilla;
    }

    public String irRegistrarTramite() {
        BeansUtils.guardarUrlAnterior();
        return "Tramite?faces-redirect=true&nroTramite=0";
    }

    public String irModificarTramite(int nroTramite) {
        BeansUtils.guardarUrlAnterior();
        return "tramite?faces-redirect=true&codigo=" + nroTramite;
    }

    public void anularTramite(int nroTramite) {
        controladorRegistrarTramite.anularTramite(nroTramite);
        Messages.create("Anulado").detail("Anulado").add();
    }

}
