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
    private int codEstadoTramite;
    private List<DTOEstadoDestinoIN> dtoEstadoDestinoList= new ArrayList<>();

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }

    public List<DTOEstadoDestinoIN> getDtoEstadoDeastinoList() {
        return dtoEstadoDestinoList;
    }

    public void setDtoEstadoDeastinoList(List<DTOEstadoDestinoIN> dtoEstadoDeastinoList) {
        this.dtoEstadoDestinoList = dtoEstadoDeastinoList;
    }
   
    public void addDtoEstadoDeastinoList(DTOEstadoDestinoIN dtoEstadoDeastino) {
        this.dtoEstadoDestinoList.add(dtoEstadoDeastino);
    } 
    
           
}
