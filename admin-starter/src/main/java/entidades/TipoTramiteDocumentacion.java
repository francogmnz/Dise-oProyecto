/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author licciardi
 */

//Extiendo entidad?
class TipoTramiteDocumentacion extends Entidad {
    
    private Date fechaDesdeTTD;
    private Date fechaHastaTTD;
    private Timestamp fechaHoraBajaTTD;
    private Documentacion documentacion;  // Relación ManyToOne con Documentacion

    public TipoTramiteDocumentacion() {
    }

    public Date getFechaDesdeTTD() {
        return fechaDesdeTTD;
    }

    public void setFechaDesdeTTD(Date fechaDesdeTTD) {
        this.fechaDesdeTTD = fechaDesdeTTD;
    }

    public Date getFechaHastaTTD() {
        return fechaHastaTTD;
    }

    public void setFechaHastaTTD(Date fechaHastaTTD) {
        this.fechaHastaTTD = fechaHastaTTD;
    }

    public Timestamp getFechaHoraBajaTTD() {
        return fechaHoraBajaTTD;
    }

    public void setFechaHoraBajaTTD(Timestamp fechaHoraBajaTTD) {
        this.fechaHoraBajaTTD = fechaHoraBajaTTD;
    }

    public Documentacion getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(Documentacion documentacion) {
        this.documentacion = documentacion;
    }
    
    
}
