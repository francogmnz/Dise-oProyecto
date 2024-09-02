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
    
    //Ver si usar Date o Timestamp como en el resto 
   
    private Timestamp fechaAnulacionTramite;
    private Date fechaFinTramite;
    private Date fechaInicioTramite;
    private Date fechaPresentacionTotalDocumentacion;
    private Date fechaRecepcionTramite;
    private int nroTramite;
    private int precioTramite; // double.. respete DC..
    private Cliente cliente;  // Relación ManyToOne
    private List<TramiteEstadoTramite> tramiteEstadoTramite = new ArrayList<>(); // Relacion ManyToOne
    private EstadoTramite estadoTramite; //Relacion ManyToOne
    private Consultor consultor; //Relacion ManyToOne
    private Version version; //Relacion ManyToOne
    private TipoTramite tipoTramite; // Relacion ManyToOne
    private List<TramiteDocumentacion> tramiteDocumentacion = new ArrayList<>();
    
    

    public Tramite() {
    }

    public Timestamp getFechaAnulacionTramite() {
        return fechaAnulacionTramite;
    }

    public void setFechaAnulacionTramite(Timestamp fechaAnulacionTramite) {
        this.fechaAnulacionTramite = fechaAnulacionTramite;
    }

    public Date getFechaFinTramite() {
        return fechaFinTramite;
    }

    public void setFechaFinTramite(Date fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }

    public Date getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public void setFechaInicioTramite(Date fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public Date getFechaPresentacionTotalDocumentacion() {
        return fechaPresentacionTotalDocumentacion;
    }

    public void setFechaPresentacionTotalDocumentacion(Date fechaPresentacionTotalDocumentacion) {
        this.fechaPresentacionTotalDocumentacion = fechaPresentacionTotalDocumentacion;
    }

    public Date getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Date fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public int getPrecioTramite() {
        return precioTramite;
    }

    public void setPrecioTramite(int precioTramite) {
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
