/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Version extends Entidad {

    private int nroVersion;
    private String descripcionVersion;
    private Timestamp fechaBajaVersion;
    private Timestamp fechaDesdeVersion;
    private Timestamp fechaHastaVersion;
    private String dibujo;

    public String getDibujo() {
        return dibujo;
    }

    public void setDibujo(String dibujo) {
        this.dibujo = dibujo;
    }
    private TipoTramite tipoTramite;
    private List<ConfTipoTramiteEstadoTramite> confTipoTramiteEstadoTramite = new ArrayList<>();  // Relación OneToMany con ConfTipoTramiteEstadoTramite

    public Version() {
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public Timestamp getFechaBajaVersion() {
        return fechaBajaVersion;
    }

    public void setFechaBajaVersion(Timestamp fechaBajaVersion) {
        this.fechaBajaVersion = fechaBajaVersion;
    }

    public Timestamp getFechaDesdeVersion() {
        return fechaDesdeVersion;
    }

    public void setFechaDesdeVersion(Timestamp fechaDesdeVersion) {
        this.fechaDesdeVersion = fechaDesdeVersion;
    }

    public Timestamp getFechaHastaVersion() {
        return fechaHastaVersion;
    }

    public void setFechaHastaVersion(Timestamp fechaHastaVersion) {
        this.fechaHastaVersion = fechaHastaVersion;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    public List<ConfTipoTramiteEstadoTramite> getConfTipoTramiteEstadoTramite() {
        return confTipoTramiteEstadoTramite;
    }

    public void setConfTipoTramiteEstadoTramite(List<ConfTipoTramiteEstadoTramite> confTipoTramiteEstadoTramite) {
        this.confTipoTramiteEstadoTramite = confTipoTramiteEstadoTramite;
    }

    public void addConfTipoTramiteEstadoTramite(ConfTipoTramiteEstadoTramite cttet) {
        confTipoTramiteEstadoTramite.add(cttet);
    }

}
