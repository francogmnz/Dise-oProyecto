/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite.dtos;

import java.sql.Timestamp;

/**
 *
 * @author matis
 */
public class EstadoTramiteDTO {

    private int codEstadoTramite;
    private String nombreEstadoTramite;
    private String descripcionEstadoTramite;
    private Timestamp fechaHoraBajaEstadoTramite;
    private Timestamp fechaHoraAltaEstadoTramite;

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codEstadoTramite) {
        this.codEstadoTramite = codEstadoTramite;
    }

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public String getDescripcionEstadoTramite() {
        return descripcionEstadoTramite;
    }

    public void setDescripcionEstadoTramite(String descripcionEstadoTramite) {
        this.descripcionEstadoTramite = descripcionEstadoTramite;
    }

    public Timestamp getFechaHoraBajaEstadoTramite() {
        return fechaHoraBajaEstadoTramite;
    }

    public void setFechaHoraBajaEstadoTramite(Timestamp fechaHoraBajaEstadoTramite) {
        this.fechaHoraBajaEstadoTramite = fechaHoraBajaEstadoTramite;
    }

    public Timestamp getFechaHoraAltaEstadoTramite() {
        return fechaHoraAltaEstadoTramite;
    }

    public void setFechaHoraAltaEstadoTramite(Timestamp fechaHoraAltaEstadoTramite) {
        this.fechaHoraAltaEstadoTramite = fechaHoraAltaEstadoTramite;
    }
    
    

}
