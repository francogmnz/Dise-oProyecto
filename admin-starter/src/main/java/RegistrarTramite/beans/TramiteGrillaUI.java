package RegistrarTramite.beans;

import java.sql.Timestamp;

public class TramiteGrillaUI {

    private int nroTramite;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacion;
    private Timestamp fechaInicioTramite;
    private Timestamp fechaFinTramite;
    private Timestamp fechaPresentacionTotalDocumentacion;
    private String nombreTipoTramite;
    private int codTipoTramite;
    private String nombreEstado;
    private String nombreApellidoCliente;
    private int dni;

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
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

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }

    public Timestamp getFechaPresentacionTotalDocumentacion() {
        return fechaPresentacionTotalDocumentacion;
    }

    public void setFechaPresentacionTotalDocumentacion(Timestamp fechaPresentacionTotalDocumentacion) {
        this.fechaPresentacionTotalDocumentacion = fechaPresentacionTotalDocumentacion;
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

    public String getNombreApellidoCliente() {
        return nombreApellidoCliente;
    }

    public void setNombreApellidoCliente(String nombreApellidoCliente) {
        this.nombreApellidoCliente = nombreApellidoCliente;
    }
        
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }



}
