/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMCategoriaTipoTramite;

import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.dtos.ModificarCategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.dtos.ModificarCategoriaTipoTramiteDTOIn;
import ABMCategoriaTipoTramite.dtos.NuevaCategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.exceptions.CategoriaTipoTramiteException;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class ControladorABMCategoriaTipoTramite {
    
    private ExpertoABMCategoriaTipoTramite expertoABMCategoriaTipoTramite = new ExpertoABMCategoriaTipoTramite();

    public List<CategoriaTipoTramiteDTO> buscarCategoriasTipoTramite (int codCategoriaTipoTramite, String nombreCategoriaTipoTramite) {
        return expertoABMCategoriaTipoTramite.buscarCategoriasTipoTramite(codCategoriaTipoTramite, nombreCategoriaTipoTramite);
    }

    public void agregarCategoriaTipoTramite(NuevaCategoriaTipoTramiteDTO nuevaCategoriaTipoTramiteDTO) throws CategoriaTipoTramiteException {
        expertoABMCategoriaTipoTramite.agregarCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO);
    }

    public ModificarCategoriaTipoTramiteDTO buscarCategoriaTipoTramiteAModificar(int codCategoriaTipoTramite) {
        return expertoABMCategoriaTipoTramite.buscarCategoriaTipoTramiteAModificar(codCategoriaTipoTramite);
    }

    public void modificarArticulo(ModificarCategoriaTipoTramiteDTOIn modificarCategoriaTipoTramiteDTOIn) {
        expertoABMCategoriaTipoTramite.modificarCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn);
    }

    public void darDeBajaCategoriaTipoTramite(int codCategoriaTipoTramite) throws CategoriaTipoTramiteException {
        expertoABMCategoriaTipoTramite.darDeBajaCategoriaTipoTramite(codCategoriaTipoTramite);
    }

    
}
