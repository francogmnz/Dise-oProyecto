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
import entidades.CategoriaTipoTramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author licciardi
 */
public class ExpertoABMCategoriaTipoTramite {
    
    public List<CategoriaTipoTramiteDTO> buscarCategoriasTipoTramite(int codCategoriaTipoTramite, String nombreCategoriaTipoTramite){
        
        List<DTOCriterio> primerCriterio = new ArrayList<DTOCriterio>();
        
        if (codCategoriaTipoTramite > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codigo");
            criterio1.setOperacion("=");
            criterio1.setValor(codCategoriaTipoTramite); // valor es un objeto en DTOCritero..
            primerCriterio.add(criterio1);
        }

        if (nombreCategoriaTipoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombre");
            criterio2.setOperacion("like");
            criterio2.setValor(nombreCategoriaTipoTramite);
            primerCriterio.add(criterio2);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", primerCriterio);
        List<CategoriaTipoTramiteDTO> categoriaTipoTramiteResultado = new ArrayList<>();

        for (Object x : objetoList) {
            CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) x;
            CategoriaTipoTramiteDTO categoriaTipoTramiteDTO = new CategoriaTipoTramiteDTO();
            categoriaTipoTramiteDTO.setCodCategoriaTipoTramite(categoriaTipoTramite.getCodCategoriaTipoTramite());
            categoriaTipoTramiteDTO.setNombreCategoriaTipoTramite(categoriaTipoTramite.getNombreCategoriaTipoTramite());
            categoriaTipoTramiteDTO.setDescripcionCategoriaTipoTramite(categoriaTipoTramite.getDescripcionCategoriaTipoTramite());
            categoriaTipoTramiteDTO.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramite.getDescripcionWebCategoriaTipoTramite());
            categoriaTipoTramiteDTO.setFechaHoraBajaCategoriaTipoTramite(categoriaTipoTramite.getFechaHoraBajaCategoriaTipoTramite());
            categoriaTipoTramiteResultado.add(categoriaTipoTramiteDTO);
        }

        return categoriaTipoTramiteResultado;
    
    }
    


    public void agregarCategoriaTipoTramite(NuevaCategoriaTipoTramiteDTO nuevaCategoriaTipoTramiteDTO) throws CategoriaTipoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(nuevaCategoriaTipoTramiteDTO.getCodCategoriaTipoTramite());

        criterioList.add(dto);

        List lCategoriaTipoTramite = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList);

        if (lCategoriaTipoTramite.size() > 0) {
            throw new CategoriaTipoTramiteException("El codigo de la categoriaTipoTramite ya existe");
        } else {
            CategoriaTipoTramite categoriaTipoTramite = new CategoriaTipoTramite();
 
            categoriaTipoTramite.setCodCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getCodCategoriaTipoTramite());
            categoriaTipoTramite.setNombreCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getNombreCategoriaTipoTramite());
            categoriaTipoTramite.setDescripcionCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getDescripcionCategoriaTipoTramite());
            categoriaTipoTramite.setDescripcionWebCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getDescripcionWebCategoriaTipoTramite());
            


            FachadaPersistencia.getInstance().guardar(categoriaTipoTramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }
    
        public ModificarCategoriaTipoTramiteDTO buscarCategoriaTipoTramiteAModificar(int codigo) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(codigo);

        criterioList.add(dto);

        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList).get(0);

        ModificarCategoriaTipoTramiteDTO modificarCategoriaTipoTramiteDTO = new ModificarCategoriaTipoTramiteDTO();
        modificarCategoriaTipoTramiteDTO.setNombreCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getNombreCategoriaTipoTramite());
        modificarCategoriaTipoTramiteDTO.setDescripcionCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getDescripcionCategoriaTipoTramite());
        modificarCategoriaTipoTramiteDTO.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getDescripcionWebCategoriaTipoTramite()); //

        return modificarCategoriaTipoTramiteDTO;
    }

        
        public void modificarCategoriaTipoTramite(ModificarCategoriaTipoTramiteDTOIn modificarCategoriaTipoTramiteDTOIn){
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramite());
        
        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList).get(0);
        
        categoriaTipoTramiteEncontrada.setCodCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setNombreCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getNombreCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setDescripcionCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getDescripcionCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setDescripcionWebCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getDescripcionWebCategoriaTipoTramite());
        
        FachadaPersistencia.getInstance().guardar(categoriaTipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
          
    }
    
        
        public void darDeBajaCategoriaTipoTramite(int codigo) throws CategoriaTipoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codigo");
        dto.setOperacion("=");
        dto.setValor(codigo);
        
        criterioList.add(dto);
        
        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList).get(0);
        
        categoriaTipoTramiteEncontrada.setFechaHoraBajaCategoriaTipoTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(categoriaTipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
}
