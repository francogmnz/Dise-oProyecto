/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Draw;

import entidades.EstadoTramite;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author Franco
 */
public class ExpertoDibujosEstados {
    
    
    
    
      public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("fechaHoraBajaEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(null);

        criterioList.add(dto);

        List<Object> objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
        List<EstadoTramite> estadosTramiteActivos = new ArrayList<>();

        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            estadosTramiteActivos.add(estadoTramite);
        }

        return estadosTramiteActivos;
    }
}
