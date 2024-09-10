/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMDocumentacion.dtos;

import java.sql.Timestamp;

/**
 *
 * @author licciardi
 */
public class ModificarDocumentacionDTO {
    
    private int codDocumentacion;
    private String descripcionDocumentacion;
    private Timestamp fechaHoraBajaDocumentacion;
    private String nombreDocumentacion;

    public int getCodDocumentacion() {
        return codDocumentacion;
    }

    public void setCodDocumentacion(int codDocumentacion) {
        this.codDocumentacion = codDocumentacion;
    }

    public String getDescripcionDocumentacion() {
        return descripcionDocumentacion;
    }

    public void setDescripcionDocumentacion(String descripcionDocumentacion) {
        this.descripcionDocumentacion = descripcionDocumentacion;
    }

    public Timestamp getFechaHoraBajaDocumentacion() {
        return fechaHoraBajaDocumentacion;
    }

    public void setFechaHoraBajaDocumentacion(Timestamp fechaHoraBajaDocumentacion) {
        this.fechaHoraBajaDocumentacion = fechaHoraBajaDocumentacion;
    }

    public String getNombreDocumentacion() {
        return nombreDocumentacion;
    }

    public void setNombreDocumentacion(String nombreDocumentacion) {
        this.nombreDocumentacion = nombreDocumentacion;
    }
    
    
    
}
