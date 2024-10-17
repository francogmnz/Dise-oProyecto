
package ABMTipoTramite;

import ABMTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMTipoTramite.dtos.DocumentacionDTO;
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
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMTipoTramite {

    /*
    // Este buscar tipoTramite de esta manera se debe al fetch join 
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
        Map<Integer,TipoTramiteDTO> tipoTramiteMap = new HashMap<>();
        
                
        for (Object x : TipoTramiteObjetoList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            TipoTramiteDTO tipoTramiteDTO = tipoTramiteMap.getOrDefault(tipoTramite.getCodTipoTramite(), new TipoTramiteDTO());

            
            if(!tipoTramiteMap.containsKey(tipoTramite.getCodTipoTramite())){
            tipoTramiteDTO.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            tipoTramiteDTO.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            tipoTramiteDTO.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            tipoTramiteDTO.setDescripcionWebTipoTramite(tipoTramite.getDescripcionWebTipoTramite());
            tipoTramiteDTO.setFechaHoraBajaTipoTramite(tipoTramite.getFechaHoraBajaTipoTramite());
            tipoTramiteDTO.setPlazoEntregaDocumentacionTT(tipoTramite.getPlazoEntregaDocumentacionTT());
            tipoTramiteDTO.setCategoriaTipoTramite(tipoTramite.getCategoriaTipoTramite()); 
            }
            
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
           
            tipoTramiteMap.put(tipoTramite.getCodTipoTramite(), tipoTramiteDTO);
        }

        return new ArrayList<>(tipoTramiteMap.values());
 
    }
    
    */
    
    //Buscar tipoTramite sin fetch Join
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
    

    public List<DocumentacionDTO> obtenerDocumentacionesActivas(){
        List<DTOCriterio> criterioDocumentacionesActivasList = new ArrayList<>();
        DTOCriterio criterioPorFechaHoraBajaDocumentacion = new DTOCriterio();
        criterioPorFechaHoraBajaDocumentacion.setAtributo("fechaHoraBajaDocumentacion");
        criterioPorFechaHoraBajaDocumentacion.setOperacion("=");
        criterioPorFechaHoraBajaDocumentacion.setValor(null);
        
        criterioDocumentacionesActivasList.add(criterioPorFechaHoraBajaDocumentacion);
        
        List documentacionObjetoList = FachadaPersistencia.getInstance().buscar("Documentacion", criterioDocumentacionesActivasList);
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
    
    public List<CategoriaTipoTramiteDTO> obtenerCategoriasTipoTramiteActivas(){
    
        List<DTOCriterio> criterioCategoriasActivasList = new ArrayList<>();
        DTOCriterio criterioPorFechaHoraBajaCategoriaTT = new DTOCriterio();
        
        criterioPorFechaHoraBajaCategoriaTT.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        criterioPorFechaHoraBajaCategoriaTT.setOperacion("=");
        criterioPorFechaHoraBajaCategoriaTT.setValor(null);
        
        criterioCategoriasActivasList.add(criterioPorFechaHoraBajaCategoriaTT);
        
        List categoriaTTList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriasActivasList);
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
        FachadaPersistencia.getInstance().iniciarTransaccion();
            
        Integer codigoParaValidacion = (Integer) nuevoTipoTramiteDTO.getCodCategoriaTipoTramite();
        if(codigoParaValidacion == null) {
            throw new TipoTramiteException("Debe seleccionar una categoría.");
        }

        if (documentacionesSeleccionadasDTO == null || documentacionesSeleccionadasDTO.isEmpty()) {
            throw new TipoTramiteException("Debe seleccionar al menos una documentación.");
        }        

        List<DTOCriterio> criterioAgregarTTList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();

        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(nuevoTipoTramiteDTO.getCodTipoTramite());

        criterioAgregarTTList.add(criterioPorCodigoTT);

        List lTipoTramite = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioAgregarTTList);

        if (lTipoTramite.size() > 0) {
            throw new TipoTramiteException("El codigo TipoTramite ya existe");
        } else {
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
            
            TipoTramiteDocumentacion tipoTramiteDocumentacion = new TipoTramiteDocumentacion();
            tipoTramiteDocumentacion.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
            tipoTramiteDocumentacion.setFechaHoraBajaTTD(null);  
            tipoTramiteDocumentacion.setFechaHastaTTD(null);
            tipoTramiteDocumentacion.setDocumentacion(documentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacion);
            
            listaTipoTramiteDocumentacion.add(tipoTramiteDocumentacion);
            }
            
            tipoTramite.setTipoTramiteDocumentacion(listaTipoTramiteDocumentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramite);

            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }
    
        public ModificarTipoTramiteDTO buscarTipoTramiteAModificar(int codTipoTramite) {
        List<DTOCriterio> criterioBuscarTTModificarList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();

        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(codTipoTramite);

        criterioBuscarTTModificarList.add(criterioPorCodigoTT);

        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioBuscarTTModificarList).get(0);

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
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        Integer codigoParaValidacion = (Integer) modificarTipoTramiteDTOIn.getCodCategoriaTipoTramite();
        if(codigoParaValidacion == null) {
            throw new TipoTramiteException("Debe seleccionar una categoría.");
        }

        if (documentacionesSeleccionadasDTO == null || documentacionesSeleccionadasDTO.isEmpty()) {
            throw new TipoTramiteException("Debe seleccionar al menos una documentación.");
        }           
        
        List<DTOCriterio> criterioModificarTTList = new ArrayList<>();
        DTOCriterio criterioPorCodigoTT = new DTOCriterio();
        
        criterioPorCodigoTT.setAtributo("codTipoTramite");
        criterioPorCodigoTT.setOperacion("=");
        criterioPorCodigoTT.setValor(modificarTipoTramiteDTOIn.getCodTipoTramite());

        criterioModificarTTList.add(criterioPorCodigoTT);

        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioModificarTTList).get(0);
        
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
        
        tipoTramiteEncontrada.setCategoriaTipoTramite(categoriaTipoTramite);

        
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacionRelacionada = tipoTramiteEncontrada.getTipoTramiteDocumentacion();
        
        /*
        List<Integer> codDocumentacionesExistentes = new ArrayList<>();
        for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacionRelacionada){
            if(ttd.getFechaHoraBajaTTD() == null){
                codDocumentacionesExistentes.add(ttd.getDocumentacion().getCodDocumentacion());
            }
        }
        
        List<Integer> codDocumentacionesSeleccionadas = new ArrayList<>();
        for(DocumentacionDTO docDTO: documentacionesSeleccionadasDTO){
            codDocumentacionesSeleccionadas.add(docDTO.getCodDocumentacion());
        }
        
        for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacionRelacionada){
            if(ttd.getFechaHoraBajaTTD() == null){
                Integer codDocExistente = ttd.getDocumentacion().getCodDocumentacion();
                if(!codDocumentacionesSeleccionadas.contains(codDocExistente)){
                    ttd.setFechaHoraBajaTTD(new Timestamp(System.currentTimeMillis()));
                    FachadaPersistencia.getInstance().guardar(ttd);
                }
            }
        
        }
        */
        
        for(TipoTramiteDocumentacion ttdr: tipoTramiteDocumentacionRelacionada){
            ttdr.setFechaHoraBajaTTD(new Timestamp(System.currentTimeMillis()));
            FachadaPersistencia.getInstance().guardar(ttdr);
        }
        
        for(DocumentacionDTO documentacionModificadaDTO: documentacionesSeleccionadasDTO){

        List<DTOCriterio> criterioDocumentacionList = new ArrayList<>();
        DTOCriterio criterioPorCodigoDocumentacion = new DTOCriterio();
        criterioPorCodigoDocumentacion.setAtributo("codDocumentacion");
        criterioPorCodigoDocumentacion.setOperacion("=");
        criterioPorCodigoDocumentacion.setValor(documentacionModificadaDTO.getCodDocumentacion());

        criterioDocumentacionList.add(criterioPorCodigoDocumentacion);

        Documentacion documentacion = (Documentacion) FachadaPersistencia.getInstance().buscar("Documentacion", criterioDocumentacionList).get(0);

        TipoTramiteDocumentacion tipoTramiteDocumentacionModificada = new TipoTramiteDocumentacion();
        tipoTramiteDocumentacionModificada.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
        tipoTramiteDocumentacionModificada.setFechaHoraBajaTTD(null);  
        tipoTramiteDocumentacionModificada.setFechaHastaTTD(null);
        tipoTramiteDocumentacionModificada.setDocumentacion(documentacion);

        FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacionModificada);

        tipoTramiteEncontrada.addTipoTramiteDocumentacion(tipoTramiteDocumentacionModificada);
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
        
        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioBajaTTList).get(0);
        
    
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

        List tramitesRelacionadosActivos = FachadaPersistencia.getInstance().buscar("Tramite", criterioTramiteList);

        
        if (!tramitesRelacionadosActivos.isEmpty()) {
            //FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new TipoTramiteException("No se puede dar de baja el TipoTramite porque hay Trámites activos relacionados.");
        } else{
            List<TipoTramiteDocumentacion> listaTipoTramiteDocumentacionABajar = tipoTramiteEncontrada.getTipoTramiteDocumentacion();
            for(TipoTramiteDocumentacion ttd: listaTipoTramiteDocumentacionABajar){
                ttd.setFechaHoraBajaTTD(new Timestamp(System.currentTimeMillis()));
                FachadaPersistencia.getInstance().guardar(ttd);
            }
        
        tipoTramiteEncontrada.setFechaHoraBajaTipoTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }
    
}
