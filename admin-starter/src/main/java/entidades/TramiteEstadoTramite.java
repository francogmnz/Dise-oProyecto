/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;

/**
 *
 * @author licciardi
 */
public class TramiteEstadoTramite extends Entidad {
    
    private Timestamp fechaHoraAltaTET;
    private Timestamp fechaHoraBajaTET;
    private EstadoTramite estadoTramite;  
private int contadorTET;

    public int getContadorTET() {
        return contadorTET;
    }

    public void setContadorTET(int contadorTET) {
        this.contadorTET = contadorTET;
    }
    public TramiteEstadoTramite() {
    }

    public Timestamp getFechaHoraAltaTET() {
        return fechaHoraAltaTET;
    }

    public void setFechaHoraAltaTET(Timestamp fechaHoraAltaTET) {
        this.fechaHoraAltaTET = fechaHoraAltaTET;
    }

    public Timestamp getFechaHoraBajaTET() {
        return fechaHoraBajaTET;
    }

    public void setFechaHoraBajaTET(Timestamp fechaHoraBajaTET) {
        this.fechaHoraBajaTET = fechaHoraBajaTET;
    }


    public EstadoTramite getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite;
    }
    
    
    
    
    
}
