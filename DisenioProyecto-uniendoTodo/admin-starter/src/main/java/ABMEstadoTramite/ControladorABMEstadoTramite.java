/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite;

import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTOIn;
import ABMEstadoTramite.dtos.NuevoEstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import java.util.List;

/**
 *
 * @author matis
 */
public class ControladorABMEstadoTramite {
    
    private ExpertoABMEstadoTramite expertoABMEstadoTramite = new ExpertoABMEstadoTramite();

    public List<EstadoTramiteDTO> buscarEstadosTramite(int codEstadoTramite, String nombreEstadoTramite, String descripcionEstadoTramite) {
        return expertoABMEstadoTramite.buscarEstadosTramite(codEstadoTramite, nombreEstadoTramite, descripcionEstadoTramite);
    }

    public void agregarEstadoTramite(NuevoEstadoTramiteDTO nuevoEstadoTramiteDTO) throws EstadoTramiteException {
        expertoABMEstadoTramite.agregarEstadoTramite(nuevoEstadoTramiteDTO);
    }

    public ModificarEstadoTramiteDTO buscarEstadoTramiteAModificar(int codEstadoTramite) throws EstadoTramiteException {
        return expertoABMEstadoTramite.buscarEstadoTramiteAModificar(codEstadoTramite);
    }

    public void modificarEstadoTramite(ModificarEstadoTramiteDTOIn modificarEstadoTramiteDTOIn) throws EstadoTramiteException {
        expertoABMEstadoTramite.modificarEstadoTramite(modificarEstadoTramiteDTOIn);
    }

    public void darDeBajaEstadoTramite(int codEstadoTramite) throws EstadoTramiteException {
        expertoABMEstadoTramite.darDeBajaEstadoTramite(codEstadoTramite);
    }
    
    
}
