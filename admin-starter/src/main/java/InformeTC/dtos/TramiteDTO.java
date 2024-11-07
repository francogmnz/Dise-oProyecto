/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package InformeTC.dtos;

import entidades.Consultor;
import entidades.TipoTramite;
import entidades.Tramite;
import java.sql.Timestamp;


public class TramiteDTO {
    
    private int nroTramite;
    private double precioTramite;
    private Timestamp fechaFinTramite;
    private Timestamp fechaInicioTramite;
    private Timestamp fechaAnulacionTramite;
    private Consultor consultor;
    private TipoTramite tipoTramite;

    public int getNroTramite() {
        return nroTramite;
    }

    public double getPrecioTramite() {
        return precioTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }
    
    public Timestamp getFechaAnulacionTramite(){
        return fechaAnulacionTramite;
    }
    
    public void setAnulacionTramite(Timestamp fechaAnulacionTramite){
        this.fechaAnulacionTramite = fechaAnulacionTramite;
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

    public void setPrecioTramite(double precioTramite) {
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
