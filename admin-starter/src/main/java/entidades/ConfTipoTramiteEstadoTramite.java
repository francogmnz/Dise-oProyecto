/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class ConfTipoTramiteEstadoTramite extends Entidad {
    
    private int contadorConfigTTET;
    private int EtapaOrigen;  // Relación ManyToOne con EstadoTramite (origen) Ver 
    private int EtapaDestino;  // Relación ManyToOne con EstadoTramite (destino)Ver
    private List<EstadoTramite> estadoTramiteOrigen = new ArrayList<>(); // ??
    private List<EstadoTramite> estadoTramiteDestino = new ArrayList<>(); // ??

    public ConfTipoTramiteEstadoTramite() {
    }

    public int getContadorConfigTTET() {
        return contadorConfigTTET;
    }

    public void setContadorConfigTTET(int contadorConfigTTET) {
        this.contadorConfigTTET = contadorConfigTTET;
    }

    public int getEtapaOrigen() {
        return EtapaOrigen;
    }

    public void setEtapaOrigen(int EtapaOrigen) {
        this.EtapaOrigen = EtapaOrigen;
    }

    public int getEtapaDestino() {
        return EtapaDestino;
    }

    public void setEtapaDestino(int EtapaDestino) {
        this.EtapaDestino = EtapaDestino;
    }


    public List<EstadoTramite> getEstadoTramiteOrigen() {
        return estadoTramiteOrigen;
    }

    public void setEstadoTramiteOrigen(List<EstadoTramite> estadoTramiteOrigen) {
        this.estadoTramiteOrigen = estadoTramiteOrigen;
    }

    public List<EstadoTramite> getEstadoTramiteDestino() {
        return estadoTramiteDestino;
    }

    public void setEstadoTramiteDestino(List<EstadoTramite> estadoTramiteDestino) {
        this.estadoTramiteDestino = estadoTramiteDestino;
        
    }

    public void addEstadoTramiteOrigen(EstadoTramite eto) {
        estadoTramiteOrigen.add(eto);   
}
    
    public void addEstadoTramiteDestino(EstadoTramite etd) {
        estadoTramiteDestino.add(etd);   
}

    
}
