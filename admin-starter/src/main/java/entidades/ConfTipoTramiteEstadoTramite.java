package entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
//@Table(name = "conf_tipo_tramite_estado_tramite")
public class ConfTipoTramiteEstadoTramite extends Entidad {
   
    private int contadorConfigTTET;
    private int etapaOrigen;  
    private int etapaDestino;  
    private int Xpos;
    private int Ypos;

    private EstadoTramite estadoTramiteOrigen; 

    private List<EstadoTramite> estadoTramiteDestino = new ArrayList<>(); 

    public ConfTipoTramiteEstadoTramite() {
  
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int Xpos) {
        this.Xpos = Xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int Ypos) {
        this.Ypos = Ypos;
    }

    public int getContadorConfigTTET() {
        return contadorConfigTTET;
    }

    public void setContadorConfigTTET(int contadorConfigTTET) {
        this.contadorConfigTTET = contadorConfigTTET;
    }

    public int getEtapaOrigen() {
        return etapaOrigen;
    }

    public void setEtapaOrigen(int etapaOrigen) {
        this.etapaOrigen = etapaOrigen;
    }

    public int getEtapaDestino() {
        return etapaDestino;
    }

    public void setEtapaDestino(int etapaDestino) {
        this.etapaDestino = etapaDestino;
    }

    public EstadoTramite getEstadoTramiteOrigen() {
        return estadoTramiteOrigen;
    }

    public void setEstadoTramiteOrigen(EstadoTramite estadoTramiteOrigen) {
        this.estadoTramiteOrigen = estadoTramiteOrigen;
    }

    public List<EstadoTramite> getEstadoTramiteDestino() {
        return estadoTramiteDestino;
    }

    public void setEstadoTramiteDestino(List<EstadoTramite> estadoTramiteDestino) {
        this.estadoTramiteDestino = estadoTramiteDestino;
    }
    
    public void addEstadoTramiteDestino(EstadoTramite etd) {
        estadoTramiteDestino.add(etd);   
    }
}
