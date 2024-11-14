package ABMDocumentacion;

import ABMDocumentacion.dtos.ModificarDocumentacionDTO;
import ABMDocumentacion.dtos.ModificarDocumentacionDTOIn;
import ABMDocumentacion.dtos.NuevoDocumentacionDTO;
import ABMDocumentacion.exceptions.DocumentacionException;
import ABMTipoTramite.dtos.DocumentacionDTO;

import java.util.List;

public class ControladorABMDocumentacion {
    
    private ExpertoABMDocumentacion expertoABMDocumentacion = new ExpertoABMDocumentacion();
    public List<DocumentacionDTO> buscarDocumentaciones(int codDocumentacion, String nombreDocumentacion, String descripcionDocumentacion){
        return expertoABMDocumentacion.buscarDocumentaciones(codDocumentacion,nombreDocumentacion,descripcionDocumentacion);
    }

    public void agregarDocumentacion(NuevoDocumentacionDTO nuevoDocumentacionDTO) throws DocumentacionException{
        expertoABMDocumentacion.agregarDocumentacion(nuevoDocumentacionDTO);
    }

    public void modificarDocumentacion(ModificarDocumentacionDTOIn modificarDocumentacionDTOIn) throws DocumentacionException{
        expertoABMDocumentacion.modificarDocumentacion(modificarDocumentacionDTOIn);
    }

    public ModificarDocumentacionDTO buscarDocumentacionAModificar(int codDocumentacion) throws DocumentacionException{
        return expertoABMDocumentacion.buscarDocumentacionAModificar(codDocumentacion);
    }

    public void darDeBajaDocumentacion(int codDocumentacion) throws DocumentacionException {
        expertoABMDocumentacion.darDeBajaDocumentacion(codDocumentacion);
    }
    
}
