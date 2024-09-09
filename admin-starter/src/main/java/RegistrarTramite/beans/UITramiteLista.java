/*package RegistrarTramite.beans;


import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.TramiteDTO;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import jdk.jfr.Name;

@Name("tramitelista")
@ViewScoped
public class UITramiteLista implements Serializable{
    
    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();
    private int nroTramiteFiltro =0;
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
    
    public List<TramiteGrillaUI> buscarTramites(){
        System.err.println(nroTramiteFiltro);
        System.err.println(nombreEstadoFiltro);
        System.err.println(nombreTipoTramiteFiltro);
        System.err.println(fechaRecepcionTramiteFiltro);
        System.err.println(dniFiltro);
        
        List<TramiteGrillaUI> tramiteGrilla = new ArrayList<TramiteGrillaUI>();
        List<TramiteDTO> tramiteDTOList = controladorRegistrarTramite.buscarTramites(nroTramiteFiltro, dniFiltro, fechaRecepcionTramiteFiltro, dniFiltro, nombreEstadoFiltro);
        
        for(TramiteDTO tramiteDTO: tramiteDTOList){
            TramiteGrillaUI tramiteGrillaUI = new TramiteGrillaUI();
            tramiteGrillaUI.setNroTramite(tramiteDTO.getNroTramite());
            tramiteGrillaUI.setDni(tramiteDTO.getDni());
            tramiteGrillaUI.setNombreEstado(tramiteDTO.getNombreEstado());
            tramiteGrillaUI.setNombreTipoTramite(tramiteDTO.getNombreTipoTramite());
            tramiteGrillaUI.setFechaAnulacion(tramiteDTO.getFechaAnulacion());
            tramiteGrilla.add(tramiteGrillaUI);
        }
        
        
        return tramiteGrilla;
    }
    
    
    
}*/
