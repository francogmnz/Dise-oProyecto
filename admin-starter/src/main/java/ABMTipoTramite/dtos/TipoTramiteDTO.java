/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite.dtos;

//import ABMDocumentacion.dtos.DocumentacionDTO;
import ABMTipoTramite.dtos.*;
import ABMTipoTramite.dtos.DocumentacionDTO;
import entidades.CategoriaTipoTramite;
import entidades.TipoTramiteDocumentacion;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class TipoTramiteDTO {
    
    private int codTipoTramite;
    private String descripcionTipoTramite;
    private String descripcionWebTipoTramite;
    private Timestamp fechaHoraBajaTipoTramite;
    private String nombreTipoTramite;
    private int plazoEntregaDocumentacionTT;
    private CategoriaTipoTramite categoriaTipoTramite;
    private List<DocumentacionDTO> documentacionesDTO;


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


    public List<DocumentacionDTO> getDocumentacionesDTO() {
        return documentacionesDTO;
    }

    public void setDocumentacionesDTO(List<DocumentacionDTO> documentacionDTO) {
        this.documentacionesDTO = documentacionDTO;
    }

     
}
