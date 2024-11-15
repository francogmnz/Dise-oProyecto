/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite;

//import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.DocumentacionDTO;
import ABMTipoTramite.*;
import ABMTipoTramite.dtos.TipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTOIn;
import ABMTipoTramite.dtos.NuevoTipoTramiteDTO;
import ABMTipoTramite.exceptions.TipoTramiteException;
import entidades.CategoriaTipoTramite;
import entidades.Documentacion;
import entidades.TipoTramite;
import entidades.TipoTramiteDocumentacion;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import utils.DTOCriterio;
import utils.FachadaPersistencia;
import utils.fechaHoraActual;

/**
 *
 * @author licciardi
 */
public class ExpertoABMTipoTramite {

    public List<TipoTramiteDTO> buscarTipoTramites(int codTipoTramite, String nombreTipoTramite){
        
        List<DTOCriterio> criterioBuscarTipoTramiteList = new ArrayList<DTOCriterio>();
        
        if (codTipoTramite > 0) {
            DTOCriterio criterioPorCodigoTT = new DTOCriterio();
            criterioPorCodigoTT.setAtributo("codTipoTramite");
            criterioPorCodigoTT.setOperacion("=");
            criterioPorCodigoTT.setValor(codTipoTramite); 
            criterioBuscarTipoTramiteList.add(criterioPorCodigoTT);
        }

        if (nombreTipoTramite.trim().length() > 0) {
            DTOCriterio criterioPorNombreTT = new DTOCriterio();
            criterioPorNombreTT.setAtributo("nombreTipoTramite");
            criterioPorNombreTT.setOperacion("like");
            criterioPorNombreTT.setValor(nombreTipoTramite);
            criterioBuscarTipoTramiteList.add(criterioPorNombreTT);
        }

        List TipoTramiteObjetoList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioBuscarTipoTramiteList);
        List<TipoTramiteDTO> tipoTramiteResultado = new ArrayList<>();

        for (Object x : TipoTramiteObjetoList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            TipoTramiteDTO tipoTramiteDTO = new TipoTramiteDTO();
            tipoTramiteDTO.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            tipoTramiteDTO.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            tipoTramiteDTO.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            tipoTramiteDTO.setDescripcionWebTipoTramite(tipoTramite.getDescripcionWebTipoTramite());
            tipoTramiteDTO.setFechaHoraBajaTipoTramite(tipoTramite.getFechaHoraBajaTipoTramite());
            tipoTramiteDTO.setPlazoEntregaDocumentacionTT(tipoTramite.getPlazoEntregaDocumentacionTT());
            tipoTramiteDTO.setCategoriaTipoTramite(tipoTramite.getCategoriaTipoTramite()); 
            
            List<TipoTramiteDocumentacion> listaTTD = tipoTramite.getTipoTramiteDocumentacion();
            List<DocumentacionDTO> documentacionesDTO = new ArrayList<>();            
           
            for(TipoTramiteDocumentacion ttd: listaTTD){
                if(ttd.getFechaHoraBajaTTD() == null){
                    
                    Documentacion documentacion = ttd.getDocumentacion();
                    DocumentacionDTO docDTO = new DocumentacionDTO();
                    
                    docDTO.setCodDocumentacion(documentacion.getCodDocumentacion());
                    docDTO.setNombreDocumentacion(documentacion.getNombreDocumentacion());
                    
                    documentacionesDTO.add(docDTO);
                    
  
                    
                }
            }
            
            tipoTramiteDTO.setDocumentacionesDTO(documentacionesDTO);
           
            tipoTramiteResultado.add(tipoTramiteDTO);
        }

        return tipoTramiteResultado;
    
    }
    

    public List<DocumentacionDTO> obtenerDocumentacionesActivas() throws TipoTramiteException{
        List<DTOCriterio> criterioDocumentacionesActivasList = new ArrayList<>();
        DTOCriterio criterioPorFechaHoraBajaDocumentacion = new DTOCriterio();
        criterioPorFechaHoraBajaDocumentacion.setAtributo("fechaHoraBajaDocumentacion");
        criterioPorFechaHoraBajaDocumentacion.setOperacion("=");
        criterioPorFechaHoraBajaDocumentacion.setValor(null);
        
        criterioDocumentacionesActivasList.add(criterioPorFechaHoraBajaDocumentacion);
        
        List documentacionObjetoList = FachadaPersistencia.getInstance().buscar("Documentacion", criterioDocumentacionesActivasList);
        
        if (documentacionObjetoList.isEmpty()) {
        throw new TipoTramiteException("No hay documentacion disponible para la seleccion.");
        }    
        
        List<DocumentacionDTO> documentacionesActivas = new ArrayList<>();
        
        for(Object x: documentacionObjetoList){
        Documentacion documentacion = (Documentacion) x;
        DocumentacionDTO documentacionDTO = new DocumentacionDTO();
        documentacionDTO.setCodDocumentacion(documentacion.getCodDocumentacion());
        documentacionDTO.setNombreDocumentacion(documentacion.getNombreDocumentacion());
        documentacionDTO.setDescripcionDocumentacion(documentacion.getDescripcionDocumentacion());
        //documentacionDTO.setFechaHoraBajaDocumentacion(documentacion.getFechaHoraBajaDocumentacion());
        
        documentacionesActivas.add(documentacionDTO);
        
        }    
        return documentacionesActivas;   
    }
    
    public List<CategoriaTipoTramiteDTO> obtenerCategoriasTipoTramiteActivas() throws TipoTramiteException{
    
        List<DTOCriterio> criterioCategoriasActivasList = new ArrayList<>();
        DTOCriterio criterioPorFechaHoraBajaCategoriaTT = new DTOCriterio();
        
        criterioPorFechaHoraBajaCategoriaTT.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        criterioPorFechaHoraBajaCategoriaTT.setOperacion("=");
        criterioPorFechaHoraBajaCategoriaTT.setValor(null);
        
        criterioCategoriasActivasList.add(criterioPorFechaHoraBajaCategoriaTT);
        
        List categoriaTTList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriasActivasList);
        
        if (categoriaTTList.isEmpty()) {
        throw new TipoTramiteException("No hay categorias disponibles para la seleccion.");
        }
    
        List<CategoriaTipoTramiteDTO> categoriasTipoTramiteActivas = new ArrayList<>();
        
        for (Object x: categoriaTTList){
        CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) x;
        CategoriaTipoTramiteDTO categoriaTipoTramiteDTO = new CategoriaTipoTramiteDTO();
        categoriaTipoTramiteDTO.setCodCategoriaTipoTramite(categoriaTipoTramite.getCodCategoriaTipoTramite());
        categoriaTipoTramiteDTO.setNombreCategoriaTipoTramite(categoriaTipoTramite.getNombreCategoriaTipoTramite());
        categoriaTipoTramiteDTO.setDescripcionCategoriaTipoTramite(categoriaTipoTramite.getDescripcionCategoriaTipoTramite());
        categoriaTipoTramiteDTO.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramite.getDescripcionWebCategoriaTipoTramite());
        //categoriaTipoTramiteDTO.setFechaHoraBajaCategoriaTipoTramite(categoriaTipoTramite.getFechaHoraBajaCategoriaTipoTramite()); no se si ponerlo ya que todas van a ser null, lo mismo con los otros datos xq aca unicamente voy a mostrar el nombre y codigo
        
        categoriasTipoTramiteActivas.add(categoriaTipoTramiteDTO);
        

        }
        return categoriasTipoTramiteActivas;
    }
    
    public void agregarTipoTramite(NuevoTipoTramiteDTO nuevoTipoTramiteDTO, List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {
        
        validarTipoTramiteA(nuevoTipoTramiteDTO, documentacionesSeleccionadasDTO);
        
        FachadaPersistencia.getInstance().iniciarTransaccion();
        

            TipoTramite tipoTramite = new TipoTramite();
 
            tipoTramite.setCodTipoTramite(nuevoTipoTramiteDTO.getCodTipoTramite());
            tipoTramite.setNombreTipoTramite(nuevoTipoTramiteDTO.getNombreTipoTramite());
            tipoTramite.setDescripcionTipoTramite(nuevoTipoTramiteDTO.getDescripcionTipoTramite());
            tipoTramite.setDescripcionWebTipoTramite(nuevoTipoTramiteDTO.getDescripcionWebTipoTramite());
            tipoTramite.setPlazoEntregaDocumentacionTT(nuevoTipoTramiteDTO.getPlazoEntregaDocumentacionTT());
            
            List<DTOCriterio> criterioCategoriaList = new ArrayList<>();
            DTOCriterio criterioPorCodigoCTT = new DTOCriterio();
        
            criterioPorCodigoCTT.setAtributo("codCategoriaTipoTramite");
            criterioPorCodigoCTT.setOperacion("=");
            criterioPorCodigoCTT.setValor(nuevoTipoTramiteDTO.getCodCategoriaTipoTramite());
        
            criterioCategoriaList.add(criterioPorCodigoCTT);
        
            CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriaList).get(0);
        
            if (categoriaTipoTramite.getFechaHoraBajaCategoriaTipoTramite() != null) {
                throw new TipoTramiteException("La categoría seleccionada está inactiva.");
            }    
            
            tipoTramite.setCategoriaTipoTramite(categoriaTipoTramite);
            

            List<TipoTramiteDocumentacion> listaTipoTramiteDocumentacion = new ArrayList<>();
            
            
            for(DocumentacionDTO documentacionDTO: documentacionesSeleccionadasDTO){
            
                
            List<DTOCriterio> criterioDocumentacionList = new ArrayList<>();
            DTOCriterio criterioCodigoDocumentacion = new DTOCriterio();
            criterioCodigoDocumentacion.setAtributo("codDocumentacion");
            criterioCodigoDocumentacion.setOperacion("=");
            criterioCodigoDocumentacion.setValor(documentacionDTO.getCodDocumentacion());
            
            criterioDocumentacionList.add(criterioCodigoDocumentacion);
            
            Documentacion documentacion = (Documentacion) FachadaPersistencia.getInstance().buscar("Documentacion", criterioDocumentacionList).get(0);
            
            if (documentacion.getFechaHoraBajaDocumentacion() != null) {
                throw new TipoTramiteException("La documentación seleccionada está inactiva.");
            }        
            
            Timestamp fechaActual = fechaHoraActual.obtenerFechaHoraActual();
            
            TipoTramiteDocumentacion tipoTramiteDocumentacion = new TipoTramiteDocumentacion();
            tipoTramiteDocumentacion.setFechaDesdeTTD(fechaActual);
            tipoTramiteDocumentacion.setFechaHoraBajaTTD(null); 
            //Ver si dejamos esto como fechaHastaTTD antes estaba en null-
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.MONTH, 6);
            Timestamp fechaHastaTTD = new Timestamp(calendar.getTimeInMillis());     
            //
            tipoTramiteDocumentacion.setFechaHastaTTD(fechaHastaTTD);
            tipoTramiteDocumentacion.setDocumentacion(documentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacion);
            
            listaTipoTramiteDocumentacion.add(tipoTramiteDocumentacion);
            }
            
            tipoTramite.setTipoTramiteDocumentacion(listaTipoTramiteDocumentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramite);

            FachadaPersistencia.getInstance().finalizarTransaccion();
        
    }
    
        public ModificarTipoTramiteDTO buscarTipoTramiteAModificar(int codTipoTramite)throws TipoTramiteException{
        List<DTOCriterio> criterioBuscarTTModificarList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();

        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(codTipoTramite);

        criterioBuscarTTModificarList.add(criterioPorCodigoTT);

        DTOCriterio criterioFechaBajaTT = new DTOCriterio();
        criterioFechaBajaTT.setAtributo("fechaHoraBajaTipoTramite");
        criterioFechaBajaTT.setOperacion("=");
        criterioFechaBajaTT.setValor(null);

        criterioBuscarTTModificarList.add(criterioFechaBajaTT);      
        
        List objectList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioBuscarTTModificarList);

        if (objectList.isEmpty()) {
            throw new TipoTramiteException("No se puede modificar un TipoTramite dado de baja.");
        }

        TipoTramite tipoTramiteEncontrada = (TipoTramite) objectList.get(0);

        ModificarTipoTramiteDTO modificarTipoTramiteDTO = new ModificarTipoTramiteDTO();
        modificarTipoTramiteDTO.setCodTipoTramite(tipoTramiteEncontrada.getCodTipoTramite());
        modificarTipoTramiteDTO.setNombreTipoTramite(tipoTramiteEncontrada.getNombreTipoTramite());
        modificarTipoTramiteDTO.setDescripcionTipoTramite(tipoTramiteEncontrada.getDescripcionTipoTramite());
        modificarTipoTramiteDTO.setDescripcionWebTipoTramite(tipoTramiteEncontrada.getDescripcionWebTipoTramite()); //
        modificarTipoTramiteDTO.setPlazoEntregaDocumentacionTT(tipoTramiteEncontrada.getPlazoEntregaDocumentacionTT());
        
        if(tipoTramiteEncontrada.getCategoriaTipoTramite() != null){
        modificarTipoTramiteDTO.setCodCategoriaTipoTramite(tipoTramiteEncontrada.getCategoriaTipoTramite().getCodCategoriaTipoTramite());
        }
        
        List<DocumentacionDTO> documentacionesRelacionadasDTO = new ArrayList<>();
        for(TipoTramiteDocumentacion ttd: tipoTramiteEncontrada.getTipoTramiteDocumentacion()){
            if(ttd.getFechaHoraBajaTTD() == null){
                DocumentacionDTO docDTO = new DocumentacionDTO();
                docDTO.setCodDocumentacion(ttd.getDocumentacion().getCodDocumentacion());
                docDTO.setNombreDocumentacion(ttd.getDocumentacion().getNombreDocumentacion());
                documentacionesRelacionadasDTO.add(docDTO);                
            }
        }
        
        modificarTipoTramiteDTO.setDocumentacionesDTO(documentacionesRelacionadasDTO);
        
        return modificarTipoTramiteDTO;
    }

        
        public void modificarTipoTramite(ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn,List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {
        
        validarTipoTramiteM(modificarTipoTramiteDTOIn, documentacionesSeleccionadasDTO);
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        Integer codigoParaValidacion = (Integer) modificarTipoTramiteDTOIn.getCodCategoriaTipoTramite();
        if(codigoParaValidacion == null) {
            throw new TipoTramiteException("Debe seleccionar una categoria.");
        }

        if (documentacionesSeleccionadasDTO == null || documentacionesSeleccionadasDTO.isEmpty()) {
            throw new TipoTramiteException("Debe seleccionar al menos una documentacion.");
        }           
        
        List<DTOCriterio> criterioModificarTTList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();
        
        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(modificarTipoTramiteDTOIn.getCodTipoTramiteOriginal());

        criterioModificarTTList.add(criterioPorCodigoTT);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        
        dtoFecha.setAtributo("fechaHoraBajaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        
        criterioModificarTTList.add(dtoFecha);           

        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioModificarTTList).get(0);
        
        if (modificarTipoTramiteDTOIn.getCodTipoTramite() != modificarTipoTramiteDTOIn.getCodTipoTramiteOriginal()){
            throw new TipoTramiteException("El código del tipo de trámite no puede ser modificado.");
        }
        
        tipoTramiteEncontrada.setCodTipoTramite(modificarTipoTramiteDTOIn.getCodTipoTramite());
        tipoTramiteEncontrada.setNombreTipoTramite(modificarTipoTramiteDTOIn.getNombreTipoTramite());
        tipoTramiteEncontrada.setDescripcionTipoTramite(modificarTipoTramiteDTOIn.getDescripcionTipoTramite());
        tipoTramiteEncontrada.setDescripcionWebTipoTramite(modificarTipoTramiteDTOIn.getDescripcionWebTipoTramite());
        tipoTramiteEncontrada.setPlazoEntregaDocumentacionTT(modificarTipoTramiteDTOIn.getPlazoEntregaDocumentacionTT());
        
        List<DTOCriterio> criterioCategoriaList = new ArrayList<>();
        DTOCriterio criterioPorCodigoCTT = new DTOCriterio();
        
        criterioPorCodigoCTT.setAtributo("codCategoriaTipoTramite");
        criterioPorCodigoCTT.setOperacion("=");
        criterioPorCodigoCTT.setValor(modificarTipoTramiteDTOIn.getCodCategoriaTipoTramite());
        
        criterioCategoriaList.add(criterioPorCodigoCTT);
        
        CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriaList).get(0);
        
        if (categoriaTipoTramite.getFechaHoraBajaCategoriaTipoTramite() != null) {
            throw new TipoTramiteException("La categoría seleccionada está inactiva.");
        }     
        tipoTramiteEncontrada.setCategoriaTipoTramite(categoriaTipoTramite);

        
    List<TipoTramiteDocumentacion> tipoTramiteDocumentacionActivas = new ArrayList<>();
    for (TipoTramiteDocumentacion ttd : tipoTramiteEncontrada.getTipoTramiteDocumentacion()) {
        if (ttd.getFechaHoraBajaTTD() == null) {
            tipoTramiteDocumentacionActivas.add(ttd);
        }
    }

    for (TipoTramiteDocumentacion ttd : tipoTramiteDocumentacionActivas) {
        boolean sigueSeleccionada = false;
        for (DocumentacionDTO docDTO : documentacionesSeleccionadasDTO) {
            if (ttd.getDocumentacion().getCodDocumentacion() == docDTO.getCodDocumentacion()) {
                sigueSeleccionada = true;
                break;
            }
        }
        if (!sigueSeleccionada) {
            ttd.setFechaHoraBajaTTD(fechaHoraActual.obtenerFechaHoraActual());
            FachadaPersistencia.getInstance().guardar(ttd);
        }
    }         
      
    for (DocumentacionDTO documentacionModificadaDTO : documentacionesSeleccionadasDTO) {
        boolean yaExiste = false;
        for (TipoTramiteDocumentacion ttd : tipoTramiteDocumentacionActivas) {
            if (ttd.getDocumentacion().getCodDocumentacion() == documentacionModificadaDTO.getCodDocumentacion()) {
                yaExiste = true;
                break;
            }
        }
        if(!yaExiste){
            List<DTOCriterio> criterioDocumentacionList = new ArrayList<>();
            DTOCriterio criterioPorCodigoDocumentacion = new DTOCriterio();
            criterioPorCodigoDocumentacion.setAtributo("codDocumentacion");
            criterioPorCodigoDocumentacion.setOperacion("=");
            criterioPorCodigoDocumentacion.setValor(documentacionModificadaDTO.getCodDocumentacion());

            criterioDocumentacionList.add(criterioPorCodigoDocumentacion);

            Documentacion documentacion = (Documentacion) FachadaPersistencia.getInstance().buscar("Documentacion", criterioDocumentacionList).get(0);

            if (documentacion.getFechaHoraBajaDocumentacion() != null) {
                throw new TipoTramiteException("La documentación seleccionada está inactiva.");
            }        

            Timestamp fechaActual = fechaHoraActual.obtenerFechaHoraActual();

            TipoTramiteDocumentacion tipoTramiteDocumentacionModificada = new TipoTramiteDocumentacion();
            tipoTramiteDocumentacionModificada.setFechaDesdeTTD(fechaActual);
            tipoTramiteDocumentacionModificada.setFechaHoraBajaTTD(null);
            // Lo mismo que en el agregar - ver que hacemos - estana en null
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaActual);
            calendar.add(Calendar.MONTH, 6);
            Timestamp fechaHastaTTD = new Timestamp(calendar.getTimeInMillis());
            //
            tipoTramiteDocumentacionModificada.setFechaHastaTTD(fechaHastaTTD);
            tipoTramiteDocumentacionModificada.setDocumentacion(documentacion);

            FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacionModificada);

            tipoTramiteEncontrada.addTipoTramiteDocumentacion(tipoTramiteDocumentacionModificada);
            }
        }



        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();        
             
    }
    
        
        public void darDeBajaTipoTramite(int codTipoTramite) throws TipoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioBajaTTList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();
        
        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(codTipoTramite);
        
        criterioBajaTTList.add(criterioPorCodigoTT);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        
        dtoFecha.setAtributo("fechaHoraBajaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        
        criterioBajaTTList.add(dtoFecha);           
        
        List objectList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioBajaTTList);

        if (objectList.isEmpty()) {
            throw new TipoTramiteException("El TipoTramite seleccionado ya se encuentra dado de baja.");
        }

        TipoTramite tipoTramiteEncontrada = (TipoTramite) objectList.get(0);
        
        List<DTOCriterio> criterioTramiteList = new ArrayList<>();
        DTOCriterio criterioTipoTramiteRelacionado = new DTOCriterio();
        criterioTipoTramiteRelacionado.setAtributo("tipoTramite");
        criterioTipoTramiteRelacionado.setOperacion("=");
        criterioTipoTramiteRelacionado.setValor(tipoTramiteEncontrada);
        
        criterioTramiteList.add(criterioTipoTramiteRelacionado);

        DTOCriterio criterioTramiteActivo = new DTOCriterio();
        criterioTramiteActivo.setAtributo("fechaFinTramite");
        criterioTramiteActivo.setOperacion("=");
        criterioTramiteActivo.setValor(null);
        
        criterioTramiteList.add(criterioTramiteActivo);
        
        DTOCriterio criterioTramiteAnulado = new DTOCriterio();
        criterioTramiteAnulado.setAtributo("fechaAnulacionTramite");
        criterioTramiteAnulado.setOperacion("=");
        criterioTramiteAnulado.setValor(null);
        
        criterioTramiteList.add(criterioTramiteAnulado);        

        List tramitesRelacionadosActivos = FachadaPersistencia.getInstance().buscar("Tramite", criterioTramiteList);

        
        if (!tramitesRelacionadosActivos.isEmpty()) {
            throw new TipoTramiteException("No se puede dar de baja el TipoTramite porque hay Tramites activos relacionados.");
        } else{
            List<TipoTramiteDocumentacion> listaTipoTramiteDocumentacionABajar = tipoTramiteEncontrada.getTipoTramiteDocumentacion();
            for(TipoTramiteDocumentacion ttd: listaTipoTramiteDocumentacionABajar){
                ttd.setFechaHoraBajaTTD(fechaHoraActual.obtenerFechaHoraActual());
                FachadaPersistencia.getInstance().guardar(ttd);
            }
        
        tipoTramiteEncontrada.setFechaHoraBajaTipoTramite(fechaHoraActual.obtenerFechaHoraActual());
        
        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
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
        
    private void validarTipoTramiteA(NuevoTipoTramiteDTO tipoTramiteDTO, List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {

        if (tipoTramiteDTO.getCodTipoTramite() <= 0) {
            throw new TipoTramiteException("El codigo debe ser un entero mayor a cero.");
        }

//        String nombreTipoTramite = eliminarEspaciosEnBlanco(tipoTramiteDTO.getNombreTipoTramite());
//        String descripcionTT = eliminarEspaciosEnBlanco(tipoTramiteDTO.getDescripcionTipoTramite());
//        String descripcionWebTT = eliminarEspaciosEnBlanco(tipoTramiteDTO.getDescripcionWebTipoTramite());
//        tipoTramiteDTO.setNombreTipoTramite(nombreTipoTramite);
//        tipoTramiteDTO.setDescripcionTipoTramite(descripcionTT);
//        tipoTramiteDTO.setDescripcionWebTipoTramite(descripcionWebTT);      
         
        String nombreTipoTramite = tipoTramiteDTO.getNombreTipoTramite();
        if (nombreTipoTramite == null || nombreTipoTramite.trim().isEmpty() || nombreTipoTramite.length() > 255) {
            throw new TipoTramiteException("El nombre debe tener entre 1 y 255 caracteres.");
        }

        String descripcionTT = tipoTramiteDTO.getDescripcionTipoTramite();
        if (descripcionTT == null || descripcionTT.trim().isEmpty() || descripcionTT.length() > 255) {
            throw new TipoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }

        String descripcionWebTT = tipoTramiteDTO.getDescripcionWebTipoTramite();
        if (descripcionWebTT == null || descripcionWebTT.trim().isEmpty() || descripcionWebTT.length() > 255) {
            throw new TipoTramiteException("La descripcion web debe tener entre 1 y 255 caracteres.");
        }

        if (tipoTramiteDTO.getPlazoEntregaDocumentacionTT() <= 0) {
            throw new TipoTramiteException("El plazo de entrega debe ser un entero mayor a cero.");
        }
        
        List<CategoriaTipoTramiteDTO> categoriasActivas = obtenerCategoriasTipoTramiteActivas();
        if (categoriasActivas.isEmpty()) {
            throw new TipoTramiteException("No hay categorías disponibles para la selección.");
        }    

        if (tipoTramiteDTO.getCodCategoriaTipoTramite() <= 0) {
            throw new TipoTramiteException("Debe seleccionar una categoria.");
        }
    
        List<DocumentacionDTO> documentacionesActivas = obtenerDocumentacionesActivas();
        if (documentacionesActivas.isEmpty()) {
            throw new TipoTramiteException("No hay documentación disponible para la selección.");
        }    

        if (documentacionesSeleccionadasDTO == null || documentacionesSeleccionadasDTO.isEmpty()) {
            throw new TipoTramiteException("Debe seleccionar al menos una documentacion.");
        }

        List<DTOCriterio> criterioCodigoTT = new ArrayList<>();
        DTOCriterio dtoCodigoTT = new DTOCriterio();
        dtoCodigoTT.setAtributo("codTipoTramite");
        dtoCodigoTT.setOperacion("=");
        dtoCodigoTT.setValor(tipoTramiteDTO.getCodTipoTramite());
        criterioCodigoTT.add(dtoCodigoTT);

        List lTipoTramiteCodigo = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioCodigoTT);
        if (!lTipoTramiteCodigo.isEmpty()) {
            throw new TipoTramiteException("El codigo del tipo de tramite ya existe.");
        }

        List<DTOCriterio> criterioNombreTT = new ArrayList<>();
        DTOCriterio dtoNombreTT = new DTOCriterio();
        dtoNombreTT.setAtributo("nombreTipoTramite");
        dtoNombreTT.setOperacion("=");
        dtoNombreTT.setValor(nombreTipoTramite);
        criterioNombreTT.add(dtoNombreTT);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        dtoFecha.setAtributo("fechaHoraBajaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        criterioNombreTT.add(dtoFecha);
        
        List lTipoTramiteNombre = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioNombreTT);
        if (!lTipoTramiteNombre.isEmpty()) {
            throw new TipoTramiteException("El nombre del tipo de tramite ya existe.");
        }


        List<DTOCriterio> criterioDescripcionTT = new ArrayList<>();
        DTOCriterio dtoDescripcionTT = new DTOCriterio();
        dtoDescripcionTT.setAtributo("descripcionTipoTramite");
        dtoDescripcionTT.setOperacion("=");
        dtoDescripcionTT.setValor(descripcionTT);
        criterioDescripcionTT.add(dtoDescripcionTT);
        criterioDescripcionTT.add(dtoFecha);

        List lTipoTramiteDescripcion = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioDescripcionTT);
        if (!lTipoTramiteDescripcion.isEmpty()) {
            throw new TipoTramiteException("La descripcion del tipo de tramite ya existe.");
        }

        List<DTOCriterio> criterioDescripcionWebTT = new ArrayList<>();
        DTOCriterio dtoDescripcionWebTT = new DTOCriterio();
        dtoDescripcionWebTT.setAtributo("descripcionWebTipoTramite");
        dtoDescripcionWebTT.setOperacion("=");
        dtoDescripcionWebTT.setValor(descripcionWebTT);
        criterioDescripcionWebTT.add(dtoDescripcionWebTT);
        criterioDescripcionWebTT.add(dtoFecha);

        List lTipoTramiteDescripcionWeb = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioDescripcionWebTT);
        if (!lTipoTramiteDescripcionWeb.isEmpty()) {
            throw new TipoTramiteException("La descripcion web del tipo de tramite ya existe.");
        }
    }
        
    private void validarTipoTramiteM(ModificarTipoTramiteDTOIn tipoTramiteDTO, List<DocumentacionDTO> documentacionesSeleccionadasDTO) throws TipoTramiteException {


//        String nombreTipoTramite = eliminarEspaciosEnBlanco(tipoTramiteDTO.getNombreTipoTramite());
//        String descripcionTT = eliminarEspaciosEnBlanco(tipoTramiteDTO.getDescripcionTipoTramite());
//        String descripcionWebTT = eliminarEspaciosEnBlanco(tipoTramiteDTO.getDescripcionWebTipoTramite());
//        tipoTramiteDTO.setNombreTipoTramite(nombreTipoTramite);
//        tipoTramiteDTO.setDescripcionTipoTramite(descripcionTT);
//        tipoTramiteDTO.setDescripcionWebTipoTramite(descripcionWebTT);        
        
        String nombreTipoTramite = tipoTramiteDTO.getNombreTipoTramite();
        if (nombreTipoTramite == null || nombreTipoTramite.trim().isEmpty() || nombreTipoTramite.length() > 255) {
            throw new TipoTramiteException("El nombreTipoTramite debe tener entre 1 y 255 caracteres.");
        }


        String descripcionTT = tipoTramiteDTO.getDescripcionTipoTramite();
        if (descripcionTT == null || descripcionTT.trim().isEmpty() || descripcionTT.length() > 255) {
            throw new TipoTramiteException("La descripcion debe tener entre 1 y 255 caracteres.");
        }


        String descripcionWebTT = tipoTramiteDTO.getDescripcionWebTipoTramite();
        if (descripcionWebTT == null || descripcionWebTT.trim().isEmpty() || descripcionWebTT.length() > 255) {
            throw new TipoTramiteException("La descripcion web debe tener entre 1 y 255 caracteres.");
        }


        if (tipoTramiteDTO.getPlazoEntregaDocumentacionTT() <= 0) {
            throw new TipoTramiteException("El plazo de entrega debe ser un entero mayor a cero.");
        }


        if (tipoTramiteDTO.getCodCategoriaTipoTramite() <= 0) {
            throw new TipoTramiteException("Debe seleccionar una categoria.");
        }


        if (documentacionesSeleccionadasDTO == null || documentacionesSeleccionadasDTO.isEmpty()) {
            throw new TipoTramiteException("Debe seleccionar al menos una documentacion.");
        }

        List<DTOCriterio> criterioNombreTT = new ArrayList<>();
        DTOCriterio dtoNombreTT = new DTOCriterio();
        dtoNombreTT.setAtributo("nombreTipoTramite");
        dtoNombreTT.setOperacion("=");
        dtoNombreTT.setValor(nombreTipoTramite);
        criterioNombreTT.add(dtoNombreTT);

        DTOCriterio dtoDistintoCodTT = new DTOCriterio();
        dtoDistintoCodTT.setAtributo("codTipoTramite");
        dtoDistintoCodTT.setOperacion("<>");
        dtoDistintoCodTT.setValor(tipoTramiteDTO.getCodTipoTramite());
        criterioNombreTT.add(dtoDistintoCodTT);
        
        DTOCriterio dtoFecha = new DTOCriterio();
        dtoFecha.setAtributo("fechaHoraBajaTipoTramite");
        dtoFecha.setOperacion("=");
        dtoFecha.setValor(null);
        criterioNombreTT.add(dtoFecha);

        List lTipoTramiteNombre = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioNombreTT);
        if (!lTipoTramiteNombre.isEmpty()) {
            throw new TipoTramiteException("El nombre del tipo de tramite ya existe.");
        }

        List<DTOCriterio> criterioDescripcionTT = new ArrayList<>();
        DTOCriterio dtoDescripcionTT = new DTOCriterio();
        dtoDescripcionTT.setAtributo("descripcionTipoTramite");
        dtoDescripcionTT.setOperacion("=");
        dtoDescripcionTT.setValor(descripcionTT);
        criterioDescripcionTT.add(dtoDescripcionTT);
        criterioDescripcionTT.add(dtoDistintoCodTT);
        criterioDescripcionTT.add(dtoFecha);

        List lTipoTramiteDescripcion = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioDescripcionTT);
        if (!lTipoTramiteDescripcion.isEmpty()) {
            throw new TipoTramiteException("La descripcion del tipo de tramite ya existe.");
        }

        List<DTOCriterio> criterioDescripcionWebTT = new ArrayList<>();
        DTOCriterio dtoDescripcionWebTT = new DTOCriterio();
        dtoDescripcionWebTT.setAtributo("descripcionWebTipoTramite");
        dtoDescripcionWebTT.setOperacion("=");
        dtoDescripcionWebTT.setValor(descripcionWebTT);
        criterioDescripcionWebTT.add(dtoDescripcionWebTT);
        criterioDescripcionWebTT.add(dtoDistintoCodTT);
        criterioDescripcionWebTT.add(dtoFecha);

        List lTipoTramiteDescripcionWeb = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioDescripcionWebTT);
        if (!lTipoTramiteDescripcionWeb.isEmpty()) {
            throw new TipoTramiteException("La descripcion web del tipo de tramite ya existe.");
        }
    }        
}
