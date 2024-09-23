/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMVersion.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Franco
 */
public class DTODatosVersionIn {
    private int codTipoTramite;
    private String descripcionVersion;
    private Date fechaDesdeVersion;
    List<DTOEstadoOrigenIN> dtoEstadoOrigenList=new ArrayList<>();  
    private Date fechaHastaVersion;
    
    
    public List<DTOEstadoOrigenIN> getDtoEstadoOrigenList() {
        return dtoEstadoOrigenList;
    }

    public void setDtoEstadoOrigenList(List<DTOEstadoOrigenIN> dtoEstadoOrigenList) {
        this.dtoEstadoOrigenList = dtoEstadoOrigenList;
    }
    

     public void addDtoEstadoOrigenList(DTOEstadoOrigenIN dtoEstadoOrigen) {
        this.dtoEstadoOrigenList.add(dtoEstadoOrigen);
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
    }

    public Date getFechaDesdeVersion() {
        return fechaDesdeVersion;
    }

    public void setFechaDesdeVersion(Date fechaDesdeVersion) {
        this.fechaDesdeVersion = fechaDesdeVersion;
    }

    public Date getFechaHastaVersion() {
        return fechaHastaVersion;
    }

    public void setFechaHastaVersion(Date fechaHastaVersion) {
        this.fechaHastaVersion = fechaHastaVersion;
    }

}
