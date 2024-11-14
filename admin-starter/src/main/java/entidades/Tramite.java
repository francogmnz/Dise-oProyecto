/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class Tramite extends Entidad {
    
     
   
    private Timestamp fechaAnulacionTramite;
    private Timestamp fechaFinTramite;
    private Timestamp fechaInicioTramite;
    private Timestamp fechaPresentacionTotalDocumentacion;
    private Timestamp fechaRecepcionTramite;
    private int nroTramite;
    private double precioTramite; 
    private Cliente cliente;  
    private List<TramiteEstadoTramite> tramiteEstadoTramite = new ArrayList<>(); 
    private EstadoTramite estadoTramite; 
    private Consultor consultor;
    private Version version; 
    private TipoTramite tipoTramite; 
    private List<TramiteDocumentacion> tramiteDocumentacion = new ArrayList<>();
    
    

    public Tramite() {
    }

    public Timestamp getFechaAnulacionTramite() {
        return fechaAnulacionTramite;
    }

    public void setFechaAnulacionTramite(Timestamp fechaAnulacionTramite) {
        this.fechaAnulacionTramite = fechaAnulacionTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public Timestamp getFechaPresentacionTotalDocumentacion() {
        return fechaPresentacionTotalDocumentacion;
    }

    public void setFechaPresentacionTotalDocumentacion(Timestamp fechaPresentacionTotalDocumentacion) {
        this.fechaPresentacionTotalDocumentacion = fechaPresentacionTotalDocumentacion;
    }

    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public double getPrecioTramite() {
        return precioTramite;
    }

    public void setPrecioTramite(double precioTramite) {
        this.precioTramite = precioTramite;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<TramiteEstadoTramite> getTramiteEstadoTramite() {
        return tramiteEstadoTramite;
    }

    public void setTramiteEstadoTramite(List<TramiteEstadoTramite> tramiteEstadoTramite) {
        this.tramiteEstadoTramite = tramiteEstadoTramite;
    }
    
    public void addTramiteEstadoTramite(TramiteEstadoTramite tet) {
        tramiteEstadoTramite.add(tet);   
}   
    

    public EstadoTramite getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

    public Consultor getConsultor() {
        return consultor;
    }

    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public List<TramiteDocumentacion> getTramiteDocumentacion() {
        return tramiteDocumentacion;
    }

    public void setTramiteDocumentacion(List<TramiteDocumentacion> tramiteDocumentacion) {
        this.tramiteDocumentacion = tramiteDocumentacion;
    }

    public void addTramiteDocumentacion(TramiteDocumentacion td) {
        tramiteDocumentacion.add(td);   
}   
    
    
}
