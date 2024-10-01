/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite.beans;

import ABMTipoTramite.beans.*;
import ABMVersion.dtos.VersionDTO;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author licciardi
 */

//Grilla que muestro del ABMTipoTramite
public class TipoTramiteGrillaUI {
    
    private int codTipoTramite;
    private String descripcionTipoTramite;
    private String descripcionWebTipoTramite;
    private Timestamp fechaHoraBajaTipoTramite;
    private String nombreTipoTramite;
    private int plazoEntregaDocumentacionTT;
    private String nombreCategoriaTipoTramite;
   private  List<VersionDTO> versiones;

    public List<VersionDTO> getVersiones() {
        return versiones;
    }

    public void setVersiones(List<VersionDTO> versiones) {
        this.versiones = versiones;
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

    public String getNombreCategoriaTipoTramite() {
        return nombreCategoriaTipoTramite;
    }

    public void setNombreCategoriaTipoTramite(String nombreCategoriaTipoTramite) {
        this.nombreCategoriaTipoTramite = nombreCategoriaTipoTramite;
    }

 

    
}
