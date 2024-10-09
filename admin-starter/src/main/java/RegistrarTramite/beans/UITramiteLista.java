package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOTipoTramite;
import RegistrarTramite.dtos.DTOTramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.BeansUtils;

@Named("uitramiteLista")
@ViewScoped
public class UITramiteLista implements Serializable {

    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    // filtros de la lista de tramites
    private int nroTramiteFiltro = 0;
    private int dniFiltro = 0;
    private Date fechaRecepcionTramiteFiltro = null;
    private String nombreEstadoFiltro = "";
     private String criterio = "";

    // filtros del TipoTramite
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

    // filtros TipoTramite
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

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public void filtrar() {
    }

    // loop por cada DTOTramite desde la UI para mostrar los Tramites filtrados
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
            tramiteGrillaUI.setCodTipoTramite(tramiteDTO.getCodTipoTramite());
            tramiteGrillaUI.setFechaRecepcionTramite(tramiteDTO.getFechaRecepcionTramite());

            tramiteGrilla.add(tramiteGrillaUI);
        }
        
        ordenarLista(tramiteGrilla);

        return tramiteGrilla;
    }
    
        // Método de ordenamiento que implementaste
    public List<TramiteGrillaUI> ordenarLista(List<TramiteGrillaUI> tGrilla) {
        switch (criterio) {
            case "codAsc":
                tGrilla.sort((t1, t2) -> {
                    Integer nroTramite1 = t1.getNroTramite();
                    Integer nroTramite2 = t2.getNroTramite();

                    if (nroTramite1 == null && nroTramite2 == null) {
                        return 0;
                    }
                    if (nroTramite1 == null) {
                        return -1;
                    }
                    if (nroTramite2 == null) {
                        return 1;
                    }
                    return Integer.compare(nroTramite1, nroTramite2);
                });
                break;

            case "codDesc":
                tGrilla.sort((t1, t2) -> {
                    Integer nroTramite1 = t1.getNroTramite();
                    Integer nroTramite2 = t2.getNroTramite();

                    if (nroTramite1 == null && nroTramite2 == null) {
                        return 0;
                    }
                    if (nroTramite1 == null) {
                        return 1;
                    }
                    if (nroTramite2 == null) {
                        return -1;
                    }
                    return Integer.compare(nroTramite2, nroTramite1);
                });
                break;
            
        }

        return tGrilla;
        }
      
    // Boton agregar Tramite
    public String irRegistrarTramite() {
        BeansUtils.guardarUrlAnterior();
        return "Tramite?faces-redirect=true&nroTramite=0";
    }

    // Redirigir a la página de resumen con el número de trámite como parámetro
    public String mostrarResumenTramite(int nroTramite) {
        return "ResumenTramite?faces-redirect=true&nroTramite=" + nroTramite;
    }

    public void anularTramite(int nroTramite) {
    }

    // loop por cada DTOTipoTramite desde la UI para mostrar los TipoTramite filtrados
    public List<FiltrosTipoTramiteGrillaUI> buscarTipoTramite() {
        System.out.println(codTipoTramiteFiltro);
        System.out.println(nombreTipoTramiteFiltro);
        System.out.println(nombreCategoriaTipoTramiteFiltro);
        System.out.println(descripcionTipoTramiteFiltro);

        List<FiltrosTipoTramiteGrillaUI> ttGrilla = new ArrayList<FiltrosTipoTramiteGrillaUI>();
        List<DTOTipoTramite> DTOttList = controladorRegistrarTramite.buscarTipoTramite(codTipoTramiteFiltro, nombreTipoTramiteFiltro, nombreCategoriaTipoTramiteFiltro, descripcionTipoTramiteFiltro);

        // loop por cada DTOTipoTramite
        for (DTOTipoTramite dtoTT : DTOttList) {
            FiltrosTipoTramiteGrillaUI ttGrillaUI = new FiltrosTipoTramiteGrillaUI();
            ttGrillaUI.setCodTipoTramite(dtoTT.getCodTipoTramite());
            ttGrillaUI.setNombreTipoTramite(dtoTT.getNombreTipoTramite());
            ttGrillaUI.setNombreCategoriaTipoTramite(dtoTT.getNombreCategoriaTipoTramite());
            ttGrillaUI.setDescripcionTipoTramite(dtoTT.getDescripcionTipoTramite());
            ttGrilla.add(ttGrillaUI);
        }
        return ttGrilla;
    }

    public String irModificarTramite(int nroTramite) {
        BeansUtils.guardarUrlAnterior();
        return "tramite?faces-redirect=true&codigo=" + nroTramite;
    }

}
