package RegistrarTramite.dtos;

import entidades.EstadoTramite;
import java.sql.Timestamp;

public class DTOTramite {
    
    private int nroTramite;
    private int dni;
    private int codTipoTramite;
    private Timestamp fechaRecepcionTramite;
    private String nombreTipoTramite;
    private String nombreEstado;
    private Timestamp fechaAnulacion;
    private Timestamp fechaPresentacionTotalDocumentacion;
    private Timestamp fechaInicioTramite;
    private Timestamp fechaFinTramite;
    
    private EstadoTramite estadoTramite;

    public int getNroTramite() {
        return nroTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
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

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    
    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }


    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public Timestamp getFechaPresentacionTotalDocumentacion() {
        return fechaPresentacionTotalDocumentacion;
    }

    public void setFechaPresentacionTotalDocumentacion(Timestamp fechaPresentacionTotalDocumentacion) {
        this.fechaPresentacionTotalDocumentacion = fechaPresentacionTotalDocumentacion;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    
    public EstadoTramite getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite;
    }
    
    
    
    
    
}
