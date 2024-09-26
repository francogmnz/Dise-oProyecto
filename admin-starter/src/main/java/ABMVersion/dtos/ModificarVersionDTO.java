package ABMVersion.dtos;

import Version.beans.NodoIU;
import java.sql.Timestamp;
import java.util.List;

public class ModificarVersionDTO {
    private String descripcionVersion;
    private int codTipoTramite;
    private List<NodoIU> nodos;
    private int numVersion;
    private Timestamp fechaBajaVersion;
    private Timestamp fechaDesdeVersion;
    private Timestamp fechaHastaVersion;

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

    public int getNumVersion() {
        return numVersion;
    }

    public void setNumVersion(int numVersion) {
        this.numVersion = numVersion;
    }

    // Getters y Setters
    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public List<NodoIU> getNodos() {
        return nodos;
    }

    public void setNodos(List<NodoIU> nodos) {
        this.nodos = nodos;
    }
}
