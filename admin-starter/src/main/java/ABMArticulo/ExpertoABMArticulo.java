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
import entidades.Articulo;
import entidades.Estado;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Components;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author matis
 */
public class ExpertoABMArticulo {

    public List<ArticuloDTO> buscarArticulos(int codigo, String nombre) {

        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        if (codigo > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codigo");
            criterio1.setOperacion("=");
            criterio1.setValor(codigo);
            lCriterio.add(criterio1);
        }

        if (nombre.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombre");
            criterio2.setOperacion("like");
            criterio2.setValor(nombre);
            lCriterio.add(criterio2);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("Articulo", lCriterio);
        List<ArticuloDTO> articulosResultado = new ArrayList<>();

        for (Object x : objetoList) {
            Articulo articulo = (Articulo) x;
            ArticuloDTO articuloDTO = new ArticuloDTO();
            articuloDTO.setCodigo(articulo.getCodigo());
            articuloDTO.setNombre(articulo.getNombre());
            articuloDTO.setEstado(articulo.getEstado());
            articuloDTO.setFechaBaja(articulo.getFechaBaja());
            articuloDTO.setFechaAlta(articulo.getFechaAlta());
            articulosResultado.add(articuloDTO);
        }

        return articulosResultado;

    }

    public void agregarArticulo(NuevoArticuloDTO nuevoArticuloDTO) throws ArticuloException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(nuevoArticuloDTO.getCodigo());

        criterioList.add(dto);

        List lArticulo = FachadaPersistencia.getInstance().buscar("Articulo", criterioList);

        if (lArticulo.size() > 0) {
            throw new ArticuloException("El codigo de articulo ya existe");
        } else {
            Articulo articulo = new Articulo();
            articulo.setCodigo(nuevoArticuloDTO.getCodigo());
            articulo.setNombre(nuevoArticuloDTO.getNombre());
            articulo.setEstado(nuevoArticuloDTO.getEstado());
            articulo.setFechaAlta(new Timestamp(System.currentTimeMillis()));

            FachadaPersistencia.getInstance().guardar(articulo);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public ModificarArticuloDTO buscarArticuloAModificar(int codigo) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        Articulo articuloEncontrado = (Articulo) FachadaPersistencia.getInstance().buscar("Articulo", criterioList).get(0);

        ModificarArticuloDTO modificarArticuloDTO = new ModificarArticuloDTO();
        modificarArticuloDTO.setNombre(articuloEncontrado.getNombre());
        modificarArticuloDTO.setCodigo(articuloEncontrado.getCodigo());
        modificarArticuloDTO.setEstado(articuloEncontrado.getEstado());

        return modificarArticuloDTO;
    }

    public void modificarArticulo(ModificarArticuloDTOIn modificarArticuloDTOIn){
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(modificarArticuloDTOIn.getCodigo());
        
        Articulo articuloEncontrado = (Articulo) FachadaPersistencia.getInstance().buscar("Articulo", criterioList).get(0);
        
        articuloEncontrado.setCodigo(modificarArticuloDTOIn.getCodigo());
        articuloEncontrado.setNombre(modificarArticuloDTOIn.getNombre());
        articuloEncontrado.setEstado(modificarArticuloDTOIn.getEstado());
        
        FachadaPersistencia.getInstance().guardar(articuloEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
    public void darDeBajaArticulo(int codigo) throws ArticuloException {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(codigo);
        
        criterioList.add(dto);
        
        Articulo articuloEncontrado = (Articulo) FachadaPersistencia.getInstance().buscar("Articulo", criterioList).get(0);
        
        articuloEncontrado.setFechaBaja(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(articuloEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

}

