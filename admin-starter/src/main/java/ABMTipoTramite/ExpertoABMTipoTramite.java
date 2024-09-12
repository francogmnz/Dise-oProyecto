/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite;

import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMDocumentacion.dtos.DocumentacionDTO;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author licciardi
 */
public class ExpertoABMTipoTramite {
    
    public List<TipoTramiteDTO> buscarTipoTramites(int codTipoTramite, String nombreTipoTramite){
        
        List<DTOCriterio> primerCriterio = new ArrayList<DTOCriterio>();
        
        if (codTipoTramite > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codTipoTramite");
            criterio1.setOperacion("=");
            criterio1.setValor(codTipoTramite); 
            primerCriterio.add(criterio1);
        }

        if (nombreTipoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombreTipoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(nombreTipoTramite);
            primerCriterio.add(criterio2);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("TipoTramite", primerCriterio);
        //List<TipoTramiteDTO> tipoTramiteResultado = new ArrayList<>();
        Map<Integer,TipoTramiteDTO> tipoTramiteMap = new HashMap<>();
        
                
        for (Object x : objetoList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            TipoTramiteDTO tipoTramiteDTO = tipoTramiteMap.getOrDefault(tipoTramite.getCodTipoTramite(), new TipoTramiteDTO());
            //TipoTramiteDTO tipoTramiteDTO = new TipoTramiteDTO();
            
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
           
            //tipoTramiteResultado.add(tipoTramiteDTO);
            tipoTramiteMap.put(tipoTramite.getCodTipoTramite(), tipoTramiteDTO);
        }

        return new ArrayList<>(tipoTramiteMap.values());
 
    }
    
    public List<DocumentacionDTO> obtenerDocumentacionesActivas(){
        List<DTOCriterio> documentacionCriterioList = new ArrayList<>();
        DTOCriterio documentacionDto = new DTOCriterio();
        documentacionDto.setAtributo("fechaHoraBajaDocumentacion");
        documentacionDto.setOperacion("=");
        documentacionDto.setValor(null);
        
        documentacionCriterioList.add(documentacionDto);
        
        List documentacionObjetoList = FachadaPersistencia.getInstance().buscar("Documentacion", documentacionCriterioList);
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
    
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        dto.setOperacion("=");
        dto.setValor(null);
        
        criterioList.add(dto);
        
        List objetoList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList);
        List<CategoriaTipoTramiteDTO> categoriasTipoTramiteActivas = new ArrayList<>();
        
        for (Object x: objetoList){
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

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codTipoTramite");
        dto.setOperacion("=");
        dto.setValor(nuevoTipoTramiteDTO.getCodTipoTramite());

        criterioList.add(dto);

        List lTipoTramite = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);

        if (lTipoTramite.size() > 0) {
            throw new TipoTramiteException("El codigo TipoTramite ya existe");
        } else {
            TipoTramite tipoTramite = new TipoTramite();
 
            tipoTramite.setCodTipoTramite(nuevoTipoTramiteDTO.getCodTipoTramite());
            tipoTramite.setNombreTipoTramite(nuevoTipoTramiteDTO.getNombreTipoTramite());
            tipoTramite.setDescripcionTipoTramite(nuevoTipoTramiteDTO.getDescripcionTipoTramite());
            tipoTramite.setDescripcionWebTipoTramite(nuevoTipoTramiteDTO.getDescripcionWebTipoTramite());
            tipoTramite.setPlazoEntregaDocumentacionTT(nuevoTipoTramiteDTO.getPlazoEntregaDocumentacionTT());
            
            List<DTOCriterio> categoriaCriterioList = new ArrayList<>();
            DTOCriterio categoriaDto = new DTOCriterio();
        
            categoriaDto.setAtributo("codCategoriaTipoTramite");
            categoriaDto.setOperacion("=");
            categoriaDto.setValor(nuevoTipoTramiteDTO.getCodCategoriaTipoTramite());
        
            categoriaCriterioList.add(categoriaDto);
        
            CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", categoriaCriterioList).get(0);
        
            tipoTramite.setCategoriaTipoTramite(categoriaTipoTramite);
            //FachadaPersistencia.getInstance().guardar(tipoTramite); aca funciona

            List<TipoTramiteDocumentacion> listaTipoTramiteDocumentacion = new ArrayList<>();
            
            
            for(DocumentacionDTO documentacionDTO: documentacionesSeleccionadasDTO){
            
                
            List<DTOCriterio> documentacionCriterioList2 = new ArrayList<>();
            DTOCriterio documentacionDto2 = new DTOCriterio();
            documentacionDto2.setAtributo("codDocumentacion");
            documentacionDto2.setOperacion("=");
            documentacionDto2.setValor(documentacionDTO.getCodDocumentacion());
            
            documentacionCriterioList2.add(documentacionDto2);
            
            Documentacion documentacion = (Documentacion) FachadaPersistencia.getInstance().buscar("Documentacion", documentacionCriterioList2).get(0);
            
            TipoTramiteDocumentacion tipoTramiteDocumentacion = new TipoTramiteDocumentacion();
            tipoTramiteDocumentacion.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
            tipoTramiteDocumentacion.setFechaHoraBajaTTD(null);  // Alta activa
            tipoTramiteDocumentacion.setFechaHastaTTD(null);
            tipoTramiteDocumentacion.setDocumentacion(documentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacion);
            
            listaTipoTramiteDocumentacion.add(tipoTramiteDocumentacion);
            }
            
            tipoTramite.setTipoTramiteDocumentacion(listaTipoTramiteDocumentacion);
            
            FachadaPersistencia.getInstance().guardar(tipoTramite);
            //tipoTramite.setTipoTramiteDocumentacion(listaTipoTramiteDocumentacion); aca funciona
            //FachadaPersistencia.getInstance().guardar(tipoTramite); aca funciona
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }
    
        public ModificarTipoTramiteDTO buscarTipoTramiteAModificar(int codTipoTramite) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codTipoTramite");
        dto.setOperacion("=");
        dto.setValor(codTipoTramite);

        criterioList.add(dto);

        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);

        ModificarTipoTramiteDTO modificarTipoTramiteDTO = new ModificarTipoTramiteDTO();
        modificarTipoTramiteDTO.setCodTipoTramite(tipoTramiteEncontrada.getCodTipoTramite());
        modificarTipoTramiteDTO.setNombreTipoTramite(tipoTramiteEncontrada.getNombreTipoTramite());
        modificarTipoTramiteDTO.setDescripcionTipoTramite(tipoTramiteEncontrada.getDescripcionTipoTramite());
        modificarTipoTramiteDTO.setDescripcionWebTipoTramite(tipoTramiteEncontrada.getDescripcionWebTipoTramite()); //
        modificarTipoTramiteDTO.setPlazoEntregaDocumentacionTT(tipoTramiteEncontrada.getPlazoEntregaDocumentacionTT());
        
        if(tipoTramiteEncontrada.getCategoriaTipoTramite() != null){
        modificarTipoTramiteDTO.setCodCategoriaTipoTramite(tipoTramiteEncontrada.getCategoriaTipoTramite().getCodCategoriaTipoTramite());
        }
        
        return modificarTipoTramiteDTO;
    }

        
        public void modificarTipoTramite(ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn,List<DocumentacionDTO> documentacionesSeleccionadasDTO){
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codTipoTramite");
        dto.setOperacion("=");
        dto.setValor(modificarTipoTramiteDTOIn.getCodTipoTramite());

        criterioList.add(dto);

        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);
        
        tipoTramiteEncontrada.setCodTipoTramite(modificarTipoTramiteDTOIn.getCodTipoTramite());
        tipoTramiteEncontrada.setNombreTipoTramite(modificarTipoTramiteDTOIn.getNombreTipoTramite());
        tipoTramiteEncontrada.setDescripcionTipoTramite(modificarTipoTramiteDTOIn.getDescripcionTipoTramite());
        tipoTramiteEncontrada.setDescripcionWebTipoTramite(modificarTipoTramiteDTOIn.getDescripcionWebTipoTramite());
        tipoTramiteEncontrada.setPlazoEntregaDocumentacionTT(modificarTipoTramiteDTOIn.getPlazoEntregaDocumentacionTT());
        
        List<DTOCriterio> categoriaCriterioList2 = new ArrayList<>();
        DTOCriterio categoriaDto2 = new DTOCriterio();
        
        categoriaDto2.setAtributo("codCategoriaTipoTramite");
        categoriaDto2.setOperacion("=");
        categoriaDto2.setValor(modificarTipoTramiteDTOIn.getCodCategoriaTipoTramite());
        
        categoriaCriterioList2.add(categoriaDto2);
        
        CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", categoriaCriterioList2).get(0);
        
        tipoTramiteEncontrada.setCategoriaTipoTramite(categoriaTipoTramite);

/*        
        // Aca doy de baja las relacionadas seleccionadas que fueron deseleccionadas
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacionRelacionada = tipoTramiteEncontrada.getTipoTramiteDocumentacion();
        
        List<Integer> codDocumentacionesSeleccionadas = new ArrayList<>();
        
        for(DocumentacionDTO docSeleccionada: documentacionesSeleccionadasDTO){
            codDocumentacionesSeleccionadas.add(docSeleccionada.getCodDocumentacion());
        }
        
        for (TipoTramiteDocumentacion ttdr : tipoTramiteDocumentacionRelacionada) {
            if (!codDocumentacionesSeleccionadas.contains(ttdr.getDocumentacion().getCodDocumentacion())) {

                ttdr.setFechaHoraBajaTTD(new Timestamp(System.currentTimeMillis()));
                FachadaPersistencia.getInstance().guardar(ttdr);
            }
        }
*/
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacionRelacionada = tipoTramiteEncontrada.getTipoTramiteDocumentacion();
        for(TipoTramiteDocumentacion ttdr: tipoTramiteDocumentacionRelacionada){
            ttdr.setFechaHoraBajaTTD(new Timestamp(System.currentTimeMillis()));
            FachadaPersistencia.getInstance().guardar(ttdr);
        }
         
        // Nueva parte
        List<TipoTramiteDocumentacion> nuevaListaTipoTramiteDocumentacion = new ArrayList<>();


        for(DocumentacionDTO documentacionModificadaDTO: documentacionesSeleccionadasDTO){


        List<DTOCriterio> documentacionCriterioList3 = new ArrayList<>();
        DTOCriterio documentacionDto3 = new DTOCriterio();
        documentacionDto3.setAtributo("codDocumentacion");
        documentacionDto3.setOperacion("=");
        documentacionDto3.setValor(documentacionModificadaDTO.getCodDocumentacion());

        documentacionCriterioList3.add(documentacionDto3);

        Documentacion documentacion = (Documentacion) FachadaPersistencia.getInstance().buscar("Documentacion", documentacionCriterioList3).get(0);

        TipoTramiteDocumentacion tipoTramiteDocumentacionModificada = new TipoTramiteDocumentacion();
        tipoTramiteDocumentacionModificada.setFechaDesdeTTD(new Timestamp(System.currentTimeMillis()));
        tipoTramiteDocumentacionModificada.setFechaHoraBajaTTD(null);  // Alta activa
        tipoTramiteDocumentacionModificada.setFechaHastaTTD(null);
        tipoTramiteDocumentacionModificada.setDocumentacion(documentacion);

        FachadaPersistencia.getInstance().guardar(tipoTramiteDocumentacionModificada);

        tipoTramiteEncontrada.addTipoTramiteDocumentacion(tipoTramiteDocumentacionModificada);
        }

       // tipoTramiteEncontrada.setTipoTramiteDocumentacion(nuevaListaTipoTramiteDocumentacion);

        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        //tipoTramite.setTipoTramiteDocumentacion(listaTipoTramiteDocumentacion); aca funciona
        //FachadaPersistencia.getInstance().guardar(tipoTramite); aca funciona
        FachadaPersistencia.getInstance().finalizarTransaccion();        
        
        
/*        
        List<DTOCriterio> documentacionCriterioList3 = new ArrayList<>();
        DTOCriterio documentacionDto3 = new DTOCriterio();
        
        documentacionDto3.setAtributo(atributo);
  
        
        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
     
*/       
    }
    
        
        public void darDeBajaTipoTramite(int codTipoTramite) throws TipoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        
        dto.setAtributo("codTipoTramite");
        dto.setOperacion("=");
        dto.setValor(codTipoTramite);
        
        criterioList.add(dto);
        
        TipoTramite tipoTramiteEncontrada = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);
        
        tipoTramiteEncontrada.setFechaHoraBajaTipoTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
}
