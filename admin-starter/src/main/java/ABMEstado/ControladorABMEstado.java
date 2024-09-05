package ABMEstado;

import ABMEstado.dtos.EstadoDTO;
import ABMEstado.dtos.ModificarEstadoDTO;
import ABMEstado.dtos.ModificarEstadoDTOIn;
import ABMEstado.dtos.NuevoEstadoDTO;
import ABMEstado.exceptions.EstadoException;

import java.util.List;

public class ControladorABMEstado {
    private ExpertoABMEstado expertoABMEstado = new ExpertoABMEstado();
    public List<EstadoDTO> buscarEstados(int codigo, String nombre){
        return expertoABMEstado.buscarEstados(codigo,nombre);
    }

    public void agregarEstado(NuevoEstadoDTO nuevoEstadoDTO) throws EstadoException{
        expertoABMEstado.agregarEstado(nuevoEstadoDTO);
    }

    public void modificarEstado(ModificarEstadoDTOIn modificarEstadoDTOIn){
        expertoABMEstado.modificarEstado(modificarEstadoDTOIn);
    }

    public ModificarEstadoDTO buscarEstadoAModificar(int codigo){
        return expertoABMEstado.buscarEstadoAModificar(codigo);
    }

    public void darDeBajaEstado(int codigo) throws EstadoException {
        expertoABMEstado.darDeBajaEstado(codigo);
    }
    
}
