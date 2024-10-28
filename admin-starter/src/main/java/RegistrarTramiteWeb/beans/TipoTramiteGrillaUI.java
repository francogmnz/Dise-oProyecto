/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.dtos.DTODocumentacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class TipoTramiteGrillaUI {
    
    private int codTipoTramite;
    private String nombreTipoTramite;
    private String descripcionTipoTramite;
    private String descripcionWebTipoTramite;
    private List<DTODocumentacion> documentaciones;

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


    public List<DTODocumentacion> getDocumentaciones() {
        return documentaciones;
    }

    public void setDocumentaciones(List<DTODocumentacion> documentaciones) {
        this.documentaciones = documentaciones;
    }
    
    public void addDocumentacion(DTODocumentacion documentacion) {
        this.documentaciones.add(documentacion);
    }    
    
}
