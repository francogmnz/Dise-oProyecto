
package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TipoTramite extends Entidad {
    
    private int codTipoTramite;
    private String descripcionTipoTramite;
    private String descripcionWebTipoTramite;
    private Timestamp fechaHoraBajaTipoTramite;
    private String nombreTipoTramite;
    private int plazoEntregaDocumentacionTT;
    private CategoriaTipoTramite categoriaTipoTramite;  // Relación ManyToOne con CategoriaTipoTramite
    private List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = new ArrayList<>();  // Relación OneToMany con TipoTramiteDocumentacion

    public TipoTramite() {
    }

  public TipoTramite(int codTipoTramite, String nombreTipoTramite) {
    this.codTipoTramite = codTipoTramite;
    this.nombreTipoTramite = nombreTipoTramite;
}


    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getDescripcionTipoTramite() {
        return descripcionTipoTramite;
    }

    public void setDescripcionTipoTramite(String descripcionTipoTramite) {
        this.descripcionTipoTramite = descripcionTipoTramite;
    }

    public String getDescripcionWebTipoTramite() {
        return descripcionWebTipoTramite;
    }

    public void setDescripcionWebTipoTramite(String descripcionWebTipoTramite) {
        this.descripcionWebTipoTramite = descripcionWebTipoTramite;
    }

    
    public Timestamp getFechaHoraBajaTipoTramite() {
        return fechaHoraBajaTipoTramite;
    }

    public void setFechaHoraBajaTipoTramite(Timestamp fechaHoraBajaTipoTramite) {
        this.fechaHoraBajaTipoTramite = fechaHoraBajaTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public int getPlazoEntregaDocumentacionTT() {
        return plazoEntregaDocumentacionTT;
    }

    public void setPlazoEntregaDocumentacionTT(int plazoEntregaDocumentacionTT) {
        this.plazoEntregaDocumentacionTT = plazoEntregaDocumentacionTT;
    }

    public CategoriaTipoTramite getCategoriaTipoTramite() {
        return categoriaTipoTramite;
    }

    public void setCategoriaTipoTramite(CategoriaTipoTramite categoriaTipoTramite) {
        this.categoriaTipoTramite = categoriaTipoTramite;
    }

    public List<TipoTramiteDocumentacion> getTipoTramiteDocumentacion() {
        return tipoTramiteDocumentacion;
    }

    public void setTipoTramiteDocumentacion(List<TipoTramiteDocumentacion> tipoTramiteDocumentacion) {
        this.tipoTramiteDocumentacion = tipoTramiteDocumentacion;
        
    }
    
    public void addTipoTramiteDocumentacion(TipoTramiteDocumentacion ttd) {
        tipoTramiteDocumentacion.add(ttd);   
    }

    
}
