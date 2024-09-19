package RegistrarTramite.beans;

import java.sql.Timestamp;

public class TramiteGrillaUI {

    private int nroTramite;
    private int dni;
    private Timestamp fechaRecepcionTramite;
    private String nombreTipoTramite;
    private int codTipoTramite;
    private String nombreEstado;
    private Timestamp fechaAnulacion;

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramie) {
        this.nroTramite = nroTramie;
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

    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

}
