/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CancelarTramites.dtos;

import entidades.Consultor;
import entidades.TipoTramite;
import entidades.Tramite;
import java.sql.Timestamp;

/**
 *
 * @author leolu
 */
public class TramiteDTO {
    
    private int nroTramite;
    private int precioTramite;
    private Timestamp fechaFinTramite;
    private Timestamp fechaInicioTramite;
    private Consultor consultor;
    private TipoTramite tipoTramite;

    public int getNroTramite() {
        return nroTramite;
    }

    public int getPrecioTramite() {
        return precioTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public Consultor getConsultor() {
        return consultor;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public void setPrecioTramite(int precioTramite) {
        this.precioTramite = precioTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }
    
    
}
