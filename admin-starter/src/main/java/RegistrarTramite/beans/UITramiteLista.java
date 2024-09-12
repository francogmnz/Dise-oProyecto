package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.TramiteDTO;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List; 
import org.omnifaces.util.Messages;
import utils.BeansUtils;

@Named("uitramiteLista")
@ViewScoped
public class UITramiteLista implements Serializable {

    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();
    
    private int nroTramiteFiltro = 0;
    private int dniFiltro = 0;
    private Timestamp fechaRecepcionTramiteFiltro = null;
    private String nombreTipoTramiteFiltro = "";
    private String nombreEstadoFiltro = "";

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

    public Timestamp getFechaRecepcionTramiteFiltro() {
        return fechaRecepcionTramiteFiltro;
    }

    public void setFechaRecepcionTramiteFiltro(Timestamp fechaRecepcionTramiteFiltro) {
        this.fechaRecepcionTramiteFiltro = fechaRecepcionTramiteFiltro;
    }

    public String getNombreTipoTramiteFiltro() {
        return nombreTipoTramiteFiltro;
    }

    public void setNombreTipoTramiteFiltro(String nombreTipoTramiteFiltro) {
        this.nombreTipoTramiteFiltro = nombreTipoTramiteFiltro;
    }

    public String getNombreEstadoFiltro() {
        return nombreEstadoFiltro;
    }

    public void setNombreEstadoFiltro(String nombreEstadoFiltro) {
        this.nombreEstadoFiltro = nombreEstadoFiltro;
    }

    public void filtrar() {

    }

    public List<TramiteGrillaUI> buscarTramites() {
        System.out.println(nroTramiteFiltro);
        System.out.println(nombreEstadoFiltro);
        System.out.println(nombreTipoTramiteFiltro);
        System.out.println(fechaRecepcionTramiteFiltro);
        System.out.println(dniFiltro);

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

        public String irRegistrarTramite() {
        BeansUtils.guardarUrlAnterior();
        return "tramite?faces-redirect=true&codigo=0";
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