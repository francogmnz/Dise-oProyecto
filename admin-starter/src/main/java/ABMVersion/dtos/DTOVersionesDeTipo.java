/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

import java.sql.Timestamp;

/**
 *
 * @author Franco
 */
public class DTOVersionesDeTipo {
     private int codTipoTramite;         // Código del Tipo de Trámite
    private String nombreTipoTramite;   // Nombre del Tipo de Trámite
    private int nroVersion;             // Código de la Versión

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
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

    public Timestamp getFechaBajaTipoTramite() {
        return fechaBajaTipoTramite;
    }

    public void setFechaBajaTipoTramite(Timestamp fechaBajaTipoTramite) {
        this.fechaBajaTipoTramite = fechaBajaTipoTramite;
    }
    private Timestamp fechaDesdeVersion;   // Fecha y Hora Desde de la Versión
    private Timestamp fechaBajaTipoTramite; // Fecha y Hora Baja del Tipo de Trámite

}
