/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class DTONumeroTramite {
    
    private int numeroTramite;
    private List<DTODocumentacion> documentaciones = new ArrayList<>();
    private int plazoEntregaDocumentacionTT;

    public int getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(int numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public List<DTODocumentacion> getDocumentaciones() {
        return documentaciones;
    }

    public void setDocumentaciones(List<DTODocumentacion> documentaciones) {
        this.documentaciones = documentaciones;
    }

    public int getPlazoEntregaDocumentacionTT() {
        return plazoEntregaDocumentacionTT;
    }

    public void setPlazoEntregaDocumentacionTT(int plazoEntregaDocumentacionTT) {
        this.plazoEntregaDocumentacionTT = plazoEntregaDocumentacionTT;
    }
      
    public void addDTODocumentacion(DTODocumentacion dtoDocumentacion) {
        documentaciones.add(dtoDocumentacion);
    }
}
