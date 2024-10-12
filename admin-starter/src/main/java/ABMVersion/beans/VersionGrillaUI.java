package ABMVersion.beans;

import entidades.TipoTramite;
import java.sql.Timestamp;

public class VersionGrillaUI {

    private int nroVersion;
    private Timestamp fechaBajaVersion;
    private Timestamp fechaDesdeVersion;
    private Timestamp fechaHastaVersion;
    private int codTipoTramite;
    private String nombreTipoTramite;
    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
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

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

  
}
