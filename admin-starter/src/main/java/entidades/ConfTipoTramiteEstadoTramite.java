package entidades;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "conf_tipo_tramite_estado_tramite")
public class ConfTipoTramiteEstadoTramite extends Entidad {
    
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID de la configuración

    private int contadorConfigTTET;
    private int etapaOrigen;  
    private int etapaDestino;  

//    @ManyToOne
    //@JoinColumn(name = "estado_tramite_origen_id") // Cambia el nombre según tu base de datos
    private EstadoTramite estadoTramiteOrigen; 
/*
    @ManyToMany
    @JoinTable(
        name = "conf_estado_tramite_destino", // Cambia según tu base de datos
        joinColumns = @JoinColumn(name = "conf_tipo_tramite_estado_tramite_id"),
        inverseJoinColumns = @JoinColumn(name = "estado_tramite_id")
    )*/
    private List<EstadoTramite> estadoTramiteDestino = new ArrayList<>(); 

    public ConfTipoTramiteEstadoTramite() {
        // Constructor vacío
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
