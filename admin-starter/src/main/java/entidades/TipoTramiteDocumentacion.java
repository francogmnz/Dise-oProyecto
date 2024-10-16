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

public class TipoTramiteDocumentacion extends Entidad {
    
    private Timestamp fechaDesdeTTD;
    private Timestamp fechaHastaTTD;
    private Timestamp fechaHoraBajaTTD;
    private Documentacion documentacion;  

    public TipoTramiteDocumentacion() {
    }

    public Timestamp getFechaDesdeTTD() {
        return fechaDesdeTTD;
    }

    public void setFechaDesdeTTD(Timestamp fechaDesdeTTD) {
        this.fechaDesdeTTD = fechaDesdeTTD;
    }

    public Timestamp getFechaHastaTTD() {
        return fechaHastaTTD;
    }

    public void setFechaHastaTTD(Timestamp fechaHastaTTD) {
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
