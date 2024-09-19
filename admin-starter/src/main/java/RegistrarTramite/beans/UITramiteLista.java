package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.DTOTramite;
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

    public void filtrar() {
    }

    // get desde la UI a DTOTramite para los filtros
    /* mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, 
    nombreEstadoTramite): List<DTOTramite> */
    public List<TramiteGrillaUI> mostrarTramites() {

        System.out.println("nroTramiteFiltro:" + nroTramiteFiltro);
        System.out.println("fechaRecepcionTramiteFiltro: " + fechaRecepcionTramiteFiltro);
        System.out.println("dniFiltro: " + dniFiltro);
        System.out.println("codTipoTramiteFiltro:" + codTipoTramiteFiltro);
        System.out.println("nombreEstadoFiltro:" + nombreEstadoFiltro);

        if (fechaRecepcionTramiteFiltro != null) {
            Calendar calFiltro = Calendar.getInstance();
            calFiltro.setTime(fechaRecepcionTramiteFiltro);
            calFiltro.set(Calendar.HOUR_OF_DAY, 0);
            calFiltro.set(Calendar.MINUTE, 0);
            fechaRecepcionTramiteFiltro = calFiltro.getTime();
        }

        List<TramiteGrillaUI> tramiteGrilla = new ArrayList<TramiteGrillaUI>();
        List<DTOTramite> tramiteDTOList = controladorRegistrarTramite.mostrarTramites(nroTramiteFiltro, fechaRecepcionTramiteFiltro, dniFiltro, codTipoTramiteFiltro, nombreEstadoFiltro);

        // Loop por cada DTOTramite
        for (DTOTramite tramiteDTO : tramiteDTOList) {
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
