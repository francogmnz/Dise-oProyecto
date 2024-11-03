/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite;

//import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.CategoriaTipoTramiteDTO;
//import ABMDocumentacion.dtos.DocumentacionDTO;
import ABMTipoTramite.*;
import ABMTipoTramite.dtos.DocumentacionDTO;
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
    
    public List<DocumentacionDTO> obtenerDocumentacionesActivas() throws TipoTramiteException {
        return expertoABMTipoTramite.obtenerDocumentacionesActivas();
    }
    public List<CategoriaTipoTramiteDTO> obtenerCategoriasTipoTramiteActivas() throws TipoTramiteException {
        return expertoABMTipoTramite.obtenerCategoriasTipoTramiteActivas();
    }

    public void agregarTipoTramite(NuevoTipoTramiteDTO nuevoTipoTramiteDTO,List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {
        expertoABMTipoTramite.agregarTipoTramite(nuevoTipoTramiteDTO,documentacionesSeleccionadasDTO);
    }

    public ModificarTipoTramiteDTO buscarTipoTramiteAModificar(int codTipoTramite) {
        return expertoABMTipoTramite.buscarTipoTramiteAModificar(codTipoTramite);
    }

    public void modificarTipoTramite(ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn, List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {
        expertoABMTipoTramite.modificarTipoTramite(modificarTipoTramiteDTOIn, documentacionesSeleccionadasDTO);
    }

    public void darDeBajaTipoTramite(int codTipoTramite) throws TipoTramiteException {
        expertoABMTipoTramite.darDeBajaTipoTramite(codTipoTramite);
    }

    
}
