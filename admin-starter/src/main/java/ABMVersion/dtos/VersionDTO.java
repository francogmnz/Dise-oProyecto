package ABMVersion.dtos;

import java.sql.Timestamp;

/**
 * Data Transfer Object (DTO) para Version.
 */
public class VersionDTO {
  private int nroVersion;
    private String descripcionVersion;
    private Timestamp fechaBajaVersion;
    private Timestamp fechaDesdeVersion;
    private Timestamp fechaHastaVersion;
    private int codTipoTramite;  // Nuevo atributo para el código del tipo de trámite

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

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }
}
