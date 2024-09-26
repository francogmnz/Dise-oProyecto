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
public class DTOConfiguracionTETT {

    private List<DTOEstadoDestinoM> dtoEstadoDestinoM= new ArrayList<>();
        private List<DTOEstadoOrigenM> dtoEstadoOrigenM= new ArrayList<>();

    public List<DTOEstadoDestinoM> getDtoEstadoDestinoM() {
        return dtoEstadoDestinoM;
    }

    public void setDtoEstadoDestinoM(List<DTOEstadoDestinoM> dtoEstadoDestinoM) {
        this.dtoEstadoDestinoM = dtoEstadoDestinoM;
    }

    public List<DTOEstadoOrigenM> getDtoEstadoOrigenM() {
        return dtoEstadoOrigenM;
    }

    public void setDtoEstadoOrigenM(List<DTOEstadoOrigenM> dtoEstadoOrigenM) {
        this.dtoEstadoOrigenM = dtoEstadoOrigenM;
    }

    
}
