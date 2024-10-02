/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMCategoriaTipoTramite.dtos;

import java.sql.Timestamp;

/**
 *
 * @author licciardi
 */
public class CategoriaTipoTramiteDTO {
    
    private int codCategoriaTipoTramite;
    private String descripcionCategoriaTipoTramite;
    private String descripcionWebCategoriaTipoTramite;
    private Timestamp fechaHoraBajaCategoriaTipoTramite;
    private String nombreCategoriaTipoTramite;

    public int getCodCategoriaTipoTramite() {
        return codCategoriaTipoTramite;
    }

    public void setCodCategoriaTipoTramite(int codCategoriaTipoTramite) {
        this.codCategoriaTipoTramite = codCategoriaTipoTramite;
    }

    public String getDescripcionCategoriaTipoTramite() {
        return descripcionCategoriaTipoTramite;
    }

    public void setDescripcionCategoriaTipoTramite(String descripcionCategoriaTipoTramite) {
        this.descripcionCategoriaTipoTramite = descripcionCategoriaTipoTramite;
    }

    public String getDescripcionWebCategoriaTipoTramite() {
        return descripcionWebCategoriaTipoTramite;
    }

    public void setDescripcionWebCategoriaTipoTramite(String descripcionWebCategoriaTipoTramite) {
        this.descripcionWebCategoriaTipoTramite = descripcionWebCategoriaTipoTramite;
    }

    public Timestamp getFechaHoraBajaCategoriaTipoTramite() {
        return fechaHoraBajaCategoriaTipoTramite;
    }

    public void setFechaHoraBajaCategoriaTipoTramite(Timestamp fechaHoraBajaCategoriaTipoTramite) {
        this.fechaHoraBajaCategoriaTipoTramite = fechaHoraBajaCategoriaTipoTramite;
    }

    public String getNombreCategoriaTipoTramite() {
        return nombreCategoriaTipoTramite;
    }

    public void setNombreCategoriaTipoTramite(String nombreCategoriaTipoTramite) {
        this.nombreCategoriaTipoTramite = nombreCategoriaTipoTramite;
    }
    
    
    
}
