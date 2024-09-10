/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite;

import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.*;
import ABMTipoTramite.dtos.TipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTOIn;
import ABMTipoTramite.dtos.NuevoTipoTramiteDTO;
import ABMTipoTramite.exceptions.TipoTramiteException;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class ControladorABMTipoTramite {
    
    private ExpertoABMTipoTramite expertoABMTipoTramite = new ExpertoABMTipoTramite();

    public List<TipoTramiteDTO> buscarTipoTramites (int codTipoTramite, String nombreTipoTramite) {
        return expertoABMTipoTramite.buscarTipoTramites(codTipoTramite, nombreTipoTramite);
    }
    
    public List<CategoriaTipoTramiteDTO> obtenerCategoriasTipoTramiteActivas() {
        return expertoABMTipoTramite.obtenerCategoriasTipoTramiteActivas();
    }

    public void agregarTipoTramite(NuevoTipoTramiteDTO nuevoTipoTramiteDTO) throws TipoTramiteException {
        expertoABMTipoTramite.agregarTipoTramite(nuevoTipoTramiteDTO);
    }

    public ModificarTipoTramiteDTO buscarTipoTramiteAModificar(int codTipoTramite) {
        return expertoABMTipoTramite.buscarTipoTramiteAModificar(codTipoTramite);
    }

    public void modificarTipoTramite(ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn) {
        expertoABMTipoTramite.modificarTipoTramite(modificarTipoTramiteDTOIn);
    }

    public void darDeBajaTipoTramite(int codTipoTramite) throws TipoTramiteException {
        expertoABMTipoTramite.darDeBajaTipoTramite(codTipoTramite);
    }

    
}
