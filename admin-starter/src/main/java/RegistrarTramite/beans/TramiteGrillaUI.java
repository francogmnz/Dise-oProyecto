package RegistrarTramite.beans;

import java.sql.Timestamp;

public class TramiteGrillaUI {

    private int nroTramite;
    private Timestamp fechaInicioTramite;
    private Timestamp fechaPresentacionTotalDocumentacion;
    private Timestamp fechaFinTramite;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacionTramite;
    private String nombreTipoTramite;
    private int codTipoTramite;
    private String nombreEstado;
    private int dni;

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public Timestamp getFechaPresentacionTotalDocumentacion() {
        return fechaPresentacionTotalDocumentacion;
    }

    public void setFechaPresentacionTotalDocumentacion(Timestamp fechaPresentacionTotalDocumentacion) {
        this.fechaPresentacionTotalDocumentacion = fechaPresentacionTotalDocumentacion;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }
    
    

    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public Timestamp getFechaAnulacionTramite() {
        return fechaAnulacionTramite;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacionTramite) {
        this.fechaAnulacionTramite = fechaAnulacionTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }



}
