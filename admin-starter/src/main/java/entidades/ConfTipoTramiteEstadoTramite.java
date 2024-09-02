/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.List;

/**
 *
 * @author licciardi
 */
public class ConfTipoTramiteEstadoTramite extends Entidad {
    
    private int contadorConfigTTET;
    private EstadoTramite origen;  // Relación ManyToOne con EstadoTramite (origen) Ver 
    private EstadoTramite destino;  // Relación ManyToOne con EstadoTramite (destino)Ver
    private List<EstadoTramite> estadoTramiteOrigen; // ??
    private List<EstadoTramite> estadoTramiteDestino; // ??

    public ConfTipoTramiteEstadoTramite() {
    }

    public int getContadorConfigTTET() {
        return contadorConfigTTET;
    }

    public void setContadorConfigTTET(int contadorConfigTTET) {
        this.contadorConfigTTET = contadorConfigTTET;
    }

    public EstadoTramite getOrigen() {
        return origen;
    }

    public void setOrigen(EstadoTramite origen) {
        this.origen = origen;
    }

    public EstadoTramite getDestino() {
        return destino;
    }

    public void setDestino(EstadoTramite destino) {
        this.destino = destino;
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


    
}
