/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMArticulo;

import ABMArticulo.dtos.ArticuloDTO;
import ABMArticulo.dtos.ModificarArticuloDTO;
import ABMArticulo.dtos.ModificarArticuloDTOIn;
import ABMArticulo.dtos.NuevoArticuloDTO;
import ABMArticulo.exceptions.ArticuloException;
import entidades.Estado;
import java.util.List;

/**
 *
 * @author matis
 */
public class ControladorABMArticulo {
    
    private ExpertoABMArticulo expertoABMArticulo = new ExpertoABMArticulo();

    public List<ArticuloDTO> buscarArticulos (int codigo, String nombre) {
        return expertoABMArticulo.buscarArticulos(codigo, nombre);
    }

    public void agregarArticulo(NuevoArticuloDTO nuevoArticuloDTO) throws ArticuloException {
        expertoABMArticulo.agregarArticulo(nuevoArticuloDTO);
    }

    public ModificarArticuloDTO buscarArticuloAModificar(int codigo) {
        return expertoABMArticulo.buscarArticuloAModificar(codigo);
    }

    public void modificarArticulo(ModificarArticuloDTOIn modificarArticuloDTOIn) {
        expertoABMArticulo.modificarArticulo(modificarArticuloDTOIn);
    }

    public void darDeBajaArticulo(int codigo) throws ArticuloException {
        expertoABMArticulo.darDeBajaArticulo(codigo);
    }


    
    
    
}
