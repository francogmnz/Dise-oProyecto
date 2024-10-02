package ABMVersion.dtos;
import ABMVersion.dtos.DTOEstado;
import ABMVersion.dtos.DTOEstadoOrigenOUT;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DTOVersionM {
    private int codTipoTramite;
    private String descripcionVersion;
    private int nroVersion;
    private Timestamp fechaHastaVersion;
    private Timestamp fechaDesdeVersion;
    private String dibujo;
private List<DTOEstado> dtoEstado = new ArrayList<>();


   public List<DTOEstado> getDtoEstado() {
        return dtoEstado;
    }

    public void setDtoEstado(List<DTOEstado> dtoEstado) {
        this.dtoEstado = dtoEstado;
    }
    
    public void addDTOEstado(DTOEstado dtoEstado) {
        this.dtoEstado.add(dtoEstado);
    } 
    public String getDibujo() {
        return dibujo;
    }

    public void setDibujo(String dibujo) {
        this.dibujo = dibujo;
    }

    
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


}
