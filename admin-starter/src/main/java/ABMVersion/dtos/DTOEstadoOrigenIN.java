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
    private List<DTOEstadoDestinoIN> dtoEstadoDestinoList = new ArrayList<>();

    // Nuevos campos para las posiciones X e Y
    private int Xpos; // Nueva posición X
    private int Ypos;

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codigoEstadoTramite) {
        this.codEstadoTramite = codigoEstadoTramite;
    }

    public List<DTOEstadoDestinoIN> getDtoEstadoDestinoList() {
        return dtoEstadoDestinoList;
    }

    public void setDtoEstadoDestinoList(List<DTOEstadoDestinoIN> dtoEstadoDestinoList) {
        this.dtoEstadoDestinoList = dtoEstadoDestinoList;
    }

    public void addDtoEstadoDestinoList(DTOEstadoDestinoIN dtoEstadoDestino) {
        this.dtoEstadoDestinoList.add(dtoEstadoDestino);
    }

    public int getXpos() {
        return Xpos;
    }

    public void setXpos(int Xpos) {
        this.Xpos = Xpos;
    }

    public int getYpos() {
        return Ypos;
    }

    public void setYpos(int Ypos) {
        this.Ypos = Ypos;
    }

  
}
