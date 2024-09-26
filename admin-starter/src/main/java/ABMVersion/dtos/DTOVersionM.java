package ABMVersion.dtos;
import ABMVersion.dtos.DTOConfiguracionTETT;
import ABMVersion.dtos.DTOEstado;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DTOVersionM {
    private int codTipoTramite;
    private String descripcionVersion;
    private int nroVersion;
    private Timestamp fechaHastaVersion;
    private Timestamp fechaDesdeVersion;

    // Listas para manejar los DTOs
    private List<DTOConfiguracionTETT> dtoConfiguracionTETT = new ArrayList<>();
    private List<DTOEstado> dtoEstado = new ArrayList<>();

    // Métodos Getter y Setter...

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
    }

    public Timestamp getFechaHastaVersion() {
        return fechaHastaVersion;
    }

    public void setFechaHastaVersion(Timestamp fechaHastaVersion) {
        this.fechaHastaVersion = fechaHastaVersion;
    }

    public Timestamp getFechaDesdeVersion() {
        return fechaDesdeVersion;
    }

    public void setFechaDesdeVersion(Timestamp fechaDesdeVersion) {
        this.fechaDesdeVersion = fechaDesdeVersion;
    }

    // Métodos para agregar DTOs a las listas
   public void addDtoConfiguracionTETT(DTOConfiguracionTETT dtoEstadoDeastino) {
        this.dtoConfiguracionTETT.add(dtoEstadoDeastino);
    } 
    public void addDTOEstado(DTOEstado dtoEstado) {
        this.dtoEstado.add(dtoEstado);
    } 
}
