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
    private String nombre;
    private int posX;
    private int posY;
  private List<DTOEstadoDestinoOUT> dtoEstadoDestinoList= new ArrayList<>();
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
