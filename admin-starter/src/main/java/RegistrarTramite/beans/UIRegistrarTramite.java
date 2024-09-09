package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import utils.FachadaPersistencia;


@Named("uitramite")
@ViewScoped
public class UIRegistrarTramite implements Serializable{
    
    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();
    
    private Boolean insert;
    private int nroTramite;
    private int dni;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacion;
    private String nombreTipoTramiteSeleccionado;
    private String nombreEstadoSeleccionado;
    
    private List<TipoTramite> tiposTramiteDisponibles;
    private List<EstadoTramite> estadosTramiteDisponibles;

    public String getNombreTipoTramiteSeleccionado() {
        return nombreTipoTramiteSeleccionado;
    }

    public void setNombreTipoTramiteSeleccionado(String nombreTipoTramiteSeleccionado) {
        this.nombreTipoTramiteSeleccionado = nombreTipoTramiteSeleccionado;
    }

    public String getNombreEstadoSeleccionado() {
        return nombreEstadoSeleccionado;
    }

    public void setNombreEstadoSeleccionado(String nombreEstadoSeleccionado) {
        this.nombreEstadoSeleccionado = nombreEstadoSeleccionado;
    }
    
    public boolean isInsert() {
        return insert;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public List<TipoTramite> getTiposTramiteDisponibles() {
        if(tiposTramiteDisponibles == null){
            cargarTiposTramiteDisponibles();
        }
        return tiposTramiteDisponibles;
    }

    public void cargarTiposTramiteDisponibles() {
       List<Object> resultado = FachadaPersistencia.getInstance().buscar("TipoTramite", new ArrayList<>());
       tiposTramiteDisponibles = resultado.stream()
               .map(obj -> (TipoTramite) obj)
               .collect(Collectors.toList());
    }

    public List<EstadoTramite> getEstadoTramiteDisponibles() {
        if(estadosTramiteDisponibles == null){
            cargarEstadosTramiteDisponibles();
        }
        return estadosTramiteDisponibles;
    }

    public void cargarEstadosTramiteDisponibles() {
       List<Object> resultado = FachadaPersistencia.getInstance().buscar("EstadoTramite", new ArrayList<>());
       estadosTramiteDisponibles = resultado.stream()
               .map(obj -> (EstadoTramite) obj)
               .collect(Collectors.toList());
    }
    
    
    
    
    
    
    
}
