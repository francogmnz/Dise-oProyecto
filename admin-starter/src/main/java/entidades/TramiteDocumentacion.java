/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.util.Date;

/**
 *
 * @author licciardi
 */
public class TramiteDocumentacion extends Entidad {
    
    
    private String archivoTD; // ver xq es tipo file 
    private int codTD;
    private Date fechaEntregaTD;
    private Documentacion documentacion;

    public TramiteDocumentacion() {
    }

    public String getArchivoTD() {
        return archivoTD;
    }

    public void setArchivoTD(String archivoTD) {
        this.archivoTD = archivoTD;
    }

    public int getCodTD() {
        return codTD;
    }

    public void setCodTD(int codTD) {
        this.codTD = codTD;
    }

    public Date getFechaEntregaTD() {
        return fechaEntregaTD;
    }

    public void setFechaEntregaTD(Date fechaEntregaTD) {
        this.fechaEntregaTD = fechaEntregaTD;
    }

    public Documentacion getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(Documentacion documentacion) {
        this.documentacion = documentacion;
    }
    
    
    
}
