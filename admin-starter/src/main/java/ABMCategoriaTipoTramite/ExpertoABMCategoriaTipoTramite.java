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
import utils.fechaHoraActual;

/**
 *
 * @author licciardi
 */
public class ExpertoABMCategoriaTipoTramite {
    
    public List<CategoriaTipoTramiteDTO> buscarCategoriasTipoTramite(int codCategoriaTipoTramite, String nombreCategoriaTipoTramite){
        
        List<DTOCriterio> primerCriterio = new ArrayList<DTOCriterio>();
        
        if (codCategoriaTipoTramite > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codCategoriaTipoTramite");
            criterio1.setOperacion("=");
            criterio1.setValor(codCategoriaTipoTramite); 
            primerCriterio.add(criterio1);
        }

        if (nombreCategoriaTipoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombreCategoriaTipoTramite");
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
        validarCategoriaTipoTramiteA(nuevaCategoriaTipoTramiteDTO);
        FachadaPersistencia.getInstance().iniciarTransaccion();

        CategoriaTipoTramite categoriaTipoTramite = new CategoriaTipoTramite();
 
        categoriaTipoTramite.setCodCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getCodCategoriaTipoTramite());
        categoriaTipoTramite.setNombreCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getNombreCategoriaTipoTramite());
        categoriaTipoTramite.setDescripcionCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getDescripcionCategoriaTipoTramite());
        categoriaTipoTramite.setDescripcionWebCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO.getDescripcionWebCategoriaTipoTramite());
            


        FachadaPersistencia.getInstance().guardar(categoriaTipoTramite);
        FachadaPersistencia.getInstance().finalizarTransaccion();
        
    }
    
        public ModificarCategoriaTipoTramiteDTO buscarCategoriaTipoTramiteAModificar(int codCategoriaTipoTramite)throws CategoriaTipoTramiteException {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dtoCod = new DTOCriterio();

        dtoCod.setAtributo("codCategoriaTipoTramite");
        dtoCod.setOperacion("=");
        dtoCod.setValor(codCategoriaTipoTramite);

        criterioList.add(dtoCod);

        DTOCriterio dtoFecha = new DTOCriterio();
        
        dtoFecha.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        
        criterioList.add(dtoFecha);
        
        List objectList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList);

        if (objectList.isEmpty()) {
            throw new CategoriaTipoTramiteException("No se puede modificar una CategoriaTipoTramite dada de baja.");
        }

        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) objectList.get(0);

        ModificarCategoriaTipoTramiteDTO modificarCategoriaTipoTramiteDTO = new ModificarCategoriaTipoTramiteDTO();
        modificarCategoriaTipoTramiteDTO.setCodCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getCodCategoriaTipoTramite());
        modificarCategoriaTipoTramiteDTO.setNombreCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getNombreCategoriaTipoTramite());
        modificarCategoriaTipoTramiteDTO.setDescripcionCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getDescripcionCategoriaTipoTramite());
        modificarCategoriaTipoTramiteDTO.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramiteEncontrada.getDescripcionWebCategoriaTipoTramite()); //

        return modificarCategoriaTipoTramiteDTO;
    }

        
        public void modificarCategoriaTipoTramite(ModificarCategoriaTipoTramiteDTOIn modificarCategoriaTipoTramiteDTOIn)throws CategoriaTipoTramiteException {
        validarCategoriaTipoTramiteM(modificarCategoriaTipoTramiteDTOIn);
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dtoCod = new DTOCriterio();
        
        dtoCod.setAtributo("codCategoriaTipoTramite");
        dtoCod.setOperacion("=");
        dtoCod.setValor(modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramiteOriginal());

        criterioList.add(dtoCod);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        
        dtoFecha.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        
        criterioList.add(dtoFecha);        

        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList).get(0);
        
        
        if (modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramite() != modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramiteOriginal()){
            throw new CategoriaTipoTramiteException("El código de la categoria no puede ser modificado.");
        }        
        
        categoriaTipoTramiteEncontrada.setCodCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getCodCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setNombreCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getNombreCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setDescripcionCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getDescripcionCategoriaTipoTramite());
        categoriaTipoTramiteEncontrada.setDescripcionWebCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn.getDescripcionWebCategoriaTipoTramite());
        
        FachadaPersistencia.getInstance().guardar(categoriaTipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
     
        
    }
    
        
        public void darDeBajaCategoriaTipoTramite(int codCategoriaTipoTramite) throws CategoriaTipoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dtoCod = new DTOCriterio();
        
        dtoCod.setAtributo("codCategoriaTipoTramite");
        dtoCod.setOperacion("=");
        dtoCod.setValor(codCategoriaTipoTramite);
        
        criterioList.add(dtoCod);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        
        dtoFecha.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        
        criterioList.add(dtoFecha);
        
        List objectList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList);

        if (objectList.isEmpty()) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new CategoriaTipoTramiteException("La CategoriaTipoTramite seleccionada ya se encuentra dada de baja.");
        }

        CategoriaTipoTramite categoriaTipoTramiteEncontrada = (CategoriaTipoTramite) objectList.get(0);
        
        List<DTOCriterio> tipoTramiteCriterioList = new ArrayList<>();
        
        DTOCriterio criterioCategoriaTipoTramiteRelacionada = new DTOCriterio();
        criterioCategoriaTipoTramiteRelacionada.setAtributo("categoriaTipoTramite");
        criterioCategoriaTipoTramiteRelacionada.setOperacion("=");
        criterioCategoriaTipoTramiteRelacionada.setValor(categoriaTipoTramiteEncontrada);

        tipoTramiteCriterioList.add(criterioCategoriaTipoTramiteRelacionada);  
        
        
        DTOCriterio criterioTipoTramiteActivo = new DTOCriterio();
        criterioTipoTramiteActivo.setAtributo("fechaHoraBajaTipoTramite");
        criterioTipoTramiteActivo.setOperacion("=");
        criterioTipoTramiteActivo.setValor(null);


        tipoTramiteCriterioList.add(criterioTipoTramiteActivo);

        List tipoTramitesRelacionados =  FachadaPersistencia.getInstance().buscar("TipoTramite", tipoTramiteCriterioList);
        
        if(!tipoTramitesRelacionados.isEmpty()){
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new CategoriaTipoTramiteException("No se puede dar de baja la categoria ya que tiene relacionado un tipoTramite Activo");
        } else{

        
        categoriaTipoTramiteEncontrada.setFechaHoraBajaCategoriaTipoTramite(fechaHoraActual.obtenerFechaHoraActual());
        
        FachadaPersistencia.getInstance().guardar(categoriaTipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }
        
    // Metodo para que quitemos espacios en blanco para la validacion.. ver si lo implementamos
    /*
    public static String eliminarEspaciosEnBlanco(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s{2,}", " ");
    }
    */
        
    private void validarCategoriaTipoTramiteA(NuevaCategoriaTipoTramiteDTO categoriaDTO) throws CategoriaTipoTramiteException {

        if (categoriaDTO.getCodCategoriaTipoTramite() <= 0) {
            throw new CategoriaTipoTramiteException("El codigo debe ser un entero mayor a cero.");
        }
        
//        String nombreCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getNombreCategoriaTipoTramite());
//        String descripcionCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getDescripcionCategoriaTipoTramite());
//        String descripcionWebCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getDescripcionWebCategoriaTipoTramite());
//        categoriaDTO.setNombreCategoriaTipoTramite(nombreCategoriaTipoTramite);
//        categoriaDTO.setDescripcionCategoriaTipoTramite(descripcionCategoriaTipoTramite);
//        categoriaDTO.setDescripcionWebCategoriaTipoTramite(descripcionWebCategoriaTipoTramite);
        
        String nombreCategoriaTipoTramite = categoriaDTO.getNombreCategoriaTipoTramite();
        if (nombreCategoriaTipoTramite == null || nombreCategoriaTipoTramite.trim().isEmpty() || nombreCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("El nombreCategoriaTipoTramite debe tener entre 1 y 255 caracteres.");
        }

        String descripcionCategoriaTipoTramite = categoriaDTO.getDescripcionCategoriaTipoTramite();
        if (descripcionCategoriaTipoTramite == null || descripcionCategoriaTipoTramite.trim().isEmpty() || descripcionCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }

        String descripcionWebCategoriaTipoTramite = categoriaDTO.getDescripcionWebCategoriaTipoTramite();
        if (descripcionWebCategoriaTipoTramite == null || descripcionWebCategoriaTipoTramite.trim().isEmpty() || descripcionWebCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("La descripcion web debe tener entre 1 y 255 caracteres.");
        }
        

        List<DTOCriterio> criterioCodigo = new ArrayList<>();
        DTOCriterio dtoCodigo = new DTOCriterio();
        dtoCodigo.setAtributo("codCategoriaTipoTramite");
        dtoCodigo.setOperacion("=");
        dtoCodigo.setValor(categoriaDTO.getCodCategoriaTipoTramite());
        criterioCodigo.add(dtoCodigo);
        
        List lCategoriaCodigo = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCodigo);
        if (!lCategoriaCodigo.isEmpty()) {
            throw new CategoriaTipoTramiteException("El codigo de la categoria introducido ya existe.");
        }
        

        List<DTOCriterio> criterioNombreCategoriaTipoTramite = new ArrayList<>();
        DTOCriterio dtoNombreCategoriaTipoTramite = new DTOCriterio();
        dtoNombreCategoriaTipoTramite.setAtributo("nombreCategoriaTipoTramite");
        dtoNombreCategoriaTipoTramite.setOperacion("=");
        dtoNombreCategoriaTipoTramite.setValor(nombreCategoriaTipoTramite);
        criterioNombreCategoriaTipoTramite.add(dtoNombreCategoriaTipoTramite);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        dtoFecha.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        criterioNombreCategoriaTipoTramite.add(dtoFecha);

        List lCategoriaNombre = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioNombreCategoriaTipoTramite);
        if (!lCategoriaNombre.isEmpty()) {
            throw new CategoriaTipoTramiteException("El nombre de la CategoriaTipoTramite ya existe.");
        }


        List<DTOCriterio> criterioDescripcion = new ArrayList<>();
        DTOCriterio dtoDescripcion = new DTOCriterio();
        dtoDescripcion.setAtributo("descripcionCategoriaTipoTramite");
        dtoDescripcion.setOperacion("=");
        dtoDescripcion.setValor(descripcionCategoriaTipoTramite);
        criterioDescripcion.add(dtoDescripcion);
        criterioDescripcion.add(dtoFecha);


        List lCategoriaDescripcion = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioDescripcion);
        if (!lCategoriaDescripcion.isEmpty()) {
            throw new CategoriaTipoTramiteException("La descripcion de la Categoria introducida ya existe.");
        }


        List<DTOCriterio> criterioDescripcionWeb = new ArrayList<>();
        DTOCriterio dtoDescripcionWeb = new DTOCriterio();
        dtoDescripcionWeb.setAtributo("descripcionWebCategoriaTipoTramite");
        dtoDescripcionWeb.setOperacion("=");
        dtoDescripcionWeb.setValor(descripcionWebCategoriaTipoTramite);
        criterioDescripcionWeb.add(dtoDescripcionWeb);
        criterioDescripcionWeb.add(dtoFecha);


        List lCategoriaDescripcionWeb = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioDescripcionWeb);
        if (!lCategoriaDescripcionWeb.isEmpty()) {
            throw new CategoriaTipoTramiteException("La descripcion web de la Categoria introducida ya existe.");
        }
    }           
        
    private void validarCategoriaTipoTramiteM(ModificarCategoriaTipoTramiteDTOIn categoriaDTO) throws CategoriaTipoTramiteException {

//        String nombreCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getNombreCategoriaTipoTramite());
//        String descripcionCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getDescripcionCategoriaTipoTramite());
//        String descripcionWebCategoriaTipoTramite = eliminarEspaciosEnBlanco(categoriaDTO.getDescripcionWebCategoriaTipoTramite());
//        categoriaDTO.setNombreCategoriaTipoTramite(nombreCategoriaTipoTramite);
//        categoriaDTO.setDescripcionCategoriaTipoTramite(descripcionCategoriaTipoTramite);
//        categoriaDTO.setDescripcionWebCategoriaTipoTramite(descripcionWebCategoriaTipoTramite);
        
        String nombreCategoriaTipoTramite = categoriaDTO.getNombreCategoriaTipoTramite();
        if (nombreCategoriaTipoTramite == null || nombreCategoriaTipoTramite.trim().isEmpty() || nombreCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("El nombreCategoriaTipoTramite debe tener entre 1 y 255 caracteres.");
        }

        String descripcionCategoriaTipoTramite = categoriaDTO.getDescripcionCategoriaTipoTramite();
        if (descripcionCategoriaTipoTramite == null || descripcionCategoriaTipoTramite.trim().isEmpty() || descripcionCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }

        String descripcionWebCategoriaTipoTramite = categoriaDTO.getDescripcionWebCategoriaTipoTramite();
        if (descripcionWebCategoriaTipoTramite == null || descripcionWebCategoriaTipoTramite.trim().isEmpty() || descripcionWebCategoriaTipoTramite.length() > 255) {
            throw new CategoriaTipoTramiteException("La descripcion web debe tener entre 1 y 255 caracteres.");
        }


        List<DTOCriterio> criterioNombreCategoriaTipoTramite = new ArrayList<>();
        DTOCriterio dtoNombreCategoriaTipoTramite = new DTOCriterio();
        dtoNombreCategoriaTipoTramite.setAtributo("nombreCategoriaTipoTramite");
        dtoNombreCategoriaTipoTramite.setOperacion("=");
        dtoNombreCategoriaTipoTramite.setValor(nombreCategoriaTipoTramite);
        criterioNombreCategoriaTipoTramite.add(dtoNombreCategoriaTipoTramite);

        DTOCriterio dtoDistintoCodCTT = new DTOCriterio();
        dtoDistintoCodCTT.setAtributo("codCategoriaTipoTramite");
        dtoDistintoCodCTT.setOperacion("<>");
        dtoDistintoCodCTT.setValor(categoriaDTO.getCodCategoriaTipoTramite());
        criterioNombreCategoriaTipoTramite.add(dtoDistintoCodCTT);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        dtoFecha.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        criterioNombreCategoriaTipoTramite.add(dtoFecha);

        List lCategoriaNombre = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioNombreCategoriaTipoTramite);
        if (!lCategoriaNombre.isEmpty()) {
            throw new CategoriaTipoTramiteException("El nombre de la CategoriaTipoTramite ya existe.");
        }


        List<DTOCriterio> criterioDescripcion = new ArrayList<>();
        DTOCriterio dtoDescripcion = new DTOCriterio();
        dtoDescripcion.setAtributo("descripcionCategoriaTipoTramite");
        dtoDescripcion.setOperacion("=");
        dtoDescripcion.setValor(descripcionCategoriaTipoTramite);
        criterioDescripcion.add(dtoDescripcion);

        criterioDescripcion.add(dtoDistintoCodCTT);
        criterioDescripcion.add(dtoFecha);
        
        List lCategoriaDescripcion = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioDescripcion);
        if (!lCategoriaDescripcion.isEmpty()) {
            throw new CategoriaTipoTramiteException("La descripcion de la Categoria introducida ya existe.");
        }


        List<DTOCriterio> criterioDescripcionWeb = new ArrayList<>();
        DTOCriterio dtoDescripcionWeb = new DTOCriterio();
        dtoDescripcionWeb.setAtributo("descripcionWebCategoriaTipoTramite");
        dtoDescripcionWeb.setOperacion("=");
        dtoDescripcionWeb.setValor(descripcionWebCategoriaTipoTramite);
        criterioDescripcionWeb.add(dtoDescripcionWeb);

        criterioDescripcionWeb.add(dtoDistintoCodCTT);
        criterioDescripcionWeb.add(dtoFecha);
        
        List lCategoriaDescripcionWeb = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioDescripcionWeb);
        if (!lCategoriaDescripcionWeb.isEmpty()) {
            throw new CategoriaTipoTramiteException("La descripcion web de la Categoria introducida ya existe.");
        }
    }        
}
