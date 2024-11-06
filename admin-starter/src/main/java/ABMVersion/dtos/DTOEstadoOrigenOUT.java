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
public class DTOEstadoOrigenOUT {

    private int codEstadoTramite;
    private String nombreEstadoOrigen;
   
    private List<DTOEstadoDestinoOUT> dtoEstadoDestinoList = new ArrayList<>();

    public String getnombreEstadoOrigen() {
        return nombreEstadoOrigen;
    }

    public void setnombreEstadoOrigen(String nombreEstadoOrigen) {
        this.nombreEstadoOrigen = nombreEstadoOrigen;
    }


    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }

    public List<DTOEstadoDestinoOUT> getDtoEstadoDestinoList() {
        return dtoEstadoDestinoList;
    }

    public void setDtoEstadoDestinoList(List<DTOEstadoDestinoOUT> dtoEstadoDestinoList) {
        this.dtoEstadoDestinoList = dtoEstadoDestinoList;
    }

    public void addDtoEstadoDestinoList(DTOEstadoDestinoOUT dtoEstadoDestinoList) {
        this.dtoEstadoDestinoList.add(dtoEstadoDestinoList);
    }

}
