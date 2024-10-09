/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

import java.sql.Timestamp;

public class DTOTipoTramiteVersion {

    private int nroVersion;
    private Timestamp fechaDesdeVersion;
    private Timestamp fechaHastaVersion;
    private Timestamp fechaBajaVersion;

    private String nombreTipoTramite;
    private int codTipoTramite;
    private Timestamp fechaHoraBajaTipoTramite;

    public Timestamp getFechaBajaVersion() {
        return fechaBajaVersion;
    }

    public void setFechaBajaVersion(Timestamp fechaBajaVersion) {
        this.fechaBajaVersion = fechaBajaVersion;
    }

    public Timestamp getFechaHoraBajaTipoTramite() {
        return fechaHoraBajaTipoTramite;
    }

    public void setFechaHoraBajaTipoTramite(Timestamp fechaHoraBajaTipoTramite) {
        this.fechaHoraBajaTipoTramite = fechaHoraBajaTipoTramite;
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
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

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

}
