/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.beans;

import ABMVersion.dtos.DTODatosVersionH;
import java.util.List;






public class VersionHistoricoGrillaUI {
    
    
    private String nombreTipoTramite;
    private int codTipoTramite;
    List<DTODatosVersionH> datosVersionH;
    
    
    

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public List<DTODatosVersionH> getDatosVersionH() {
        return datosVersionH;
    }

    public void setDatosVersionH(List<DTODatosVersionH> datosVersionH) {
        this.datosVersionH = datosVersionH;
    }
    
    
    
    
    
}
