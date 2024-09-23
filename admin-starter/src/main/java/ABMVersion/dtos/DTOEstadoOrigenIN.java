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
public class DTOEstadoOrigenIN {
    private int codigoEstadoTramite;
    private List<DTOEstadoDestinoIN> dtoEstadoDeastinoList= new ArrayList<>();

    public int getCodigoEstadoTramite() {
        return codigoEstadoTramite;
    }

    public void setCodigoEstadoTramite(int codigoEstadoTramite) {
        this.codigoEstadoTramite = codigoEstadoTramite;
    }

    public List<DTOEstadoDestinoIN> getDtoEstadoDeastinoList() {
        return dtoEstadoDeastinoList;
    }

    public void setDtoEstadoDeastinoList(List<DTOEstadoDestinoIN> dtoEstadoDeastinoList) {
        this.dtoEstadoDeastinoList = dtoEstadoDeastinoList;
    }
   
    public void addDtoEstadoDeastinoList(DTOEstadoDestinoIN dtoEstadoDeastino) {
        this.dtoEstadoDeastinoList.add(dtoEstadoDeastino);
    } 
           
}
