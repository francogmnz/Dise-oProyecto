package RegistrarTramite.dtos;

import java.sql.Timestamp;

public class TramiteDTO {
    
    private int nroTramie;
    private int dni;
    private Timestamp fechaRecepcionTramite;
    private int codTipoTramite;
    private String nombreEstado;
    private Timestamp fechaAnulacion;

    public int getNroTramie() {
        return nroTramie;
    }

    public void setNroTramie(int nroTramie) {
        this.nroTramie = nroTramie;
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
