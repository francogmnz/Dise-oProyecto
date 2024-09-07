package RegistrarTramite.dtos;

import java.sql.Timestamp;


public class ResumenDocumentacionDTO {
    private String nombreDocumentacion;
    private Timestamp fechaEntregaDoc;
    
    
    public String getNombreDocumentacion() {
        return nombreDocumentacion;
    }

    public void setNombreDocumentacion(String nombreDocumentacion) {
        this.nombreDocumentacion = nombreDocumentacion;
    }

    public Timestamp getFechaEntregaDoc() {
        return fechaEntregaDoc;
    }

    public void setFechaEntregaDoc(Timestamp fechaEntregaDoc) {
        this.fechaEntregaDoc = fechaEntregaDoc;
    }
    
}
