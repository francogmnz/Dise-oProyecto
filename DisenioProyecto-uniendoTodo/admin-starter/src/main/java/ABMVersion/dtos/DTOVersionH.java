/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RRtechg
 */
public class DTOVersionH {
    List<DTODatosVersionH> dtoDatosVersionH = new ArrayList<>();
    private int codTipoTramite;
    private String nombreTipoTramite;

    public List<DTODatosVersionH> getDtoDatosVersionH() {
        return dtoDatosVersionH;
    }

    public void setDtoDatosVersionH(List<DTODatosVersionH> dtoDatosVersionH) {
        this.dtoDatosVersionH = dtoDatosVersionH;
    }

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
    
    
}
