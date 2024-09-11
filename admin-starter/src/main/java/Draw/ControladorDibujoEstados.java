/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Draw;

import entidades.EstadoTramite;
import java.util.List;

/**
 *
 * @author Franco
 */
public class ControladorDibujoEstados {
    
        private ExpertoDibujosEstados expertoDibujosEstados = new ExpertoDibujosEstados();

    public List<EstadoTramite> obtenerEstadosTramiteActivos() {
        return expertoDibujosEstados.obtenerEstadosTramiteActivos();
    }
}
