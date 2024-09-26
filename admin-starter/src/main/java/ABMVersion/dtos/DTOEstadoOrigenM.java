/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Franco
 */
public class DTOEstadoOrigenM {
       private String nombreEstadoTramite;
    private int codEstadoTramite;
     private List<DTOEstadoDestinoM> dtoEstadoDestinoM= new ArrayList<>();

   

    public void setDtoEstadoDestinoM(List<DTOEstadoDestinoM> dtoEstadoDestinoM) {
        this.dtoEstadoDestinoM = dtoEstadoDestinoM;
    }
     
    public void addDTOEstadoDestinoM(DTOEstadoDestinoM dtoEstadoDestinoM) {
        this.dtoEstadoDestinoM.add(dtoEstadoDestinoM);
    }

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codEstadoTramite) {
        this.codEstadoTramite = codEstadoTramite;
    }
}
