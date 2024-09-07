package RegistrarTramite;

import RegistrarTramite.dtos.ResumenDocumentacionDTO;
import RegistrarTramite.dtos.ResumenTramiteDTO;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.TramiteDTO;
import entidades.CategoriaTipoTramite;
import entidades.Cliente;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import entidades.Tramite;
import entidades.TramiteDocumentacion;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;


public class ExpertoRegistrarTramiteRecepcionista {
    
    //BuscarTramites(con filtros)
    public List<TramiteDTO> buscarTramites(int nroTramite, int dni, 
            Timestamp fechaRecepcionTramite, int codTipoTramite, String nombreEstado){
        
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        
        //filtro para el nroTramite
        if(nroTramite>0){
            DTOCriterio dto1 = new DTOCriterio();
            
            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);
            
            criterioList.add(dto1);
        }
        
        //filtro para la fechaRecepcion
        if(fechaRecepcionTramite != null){
            DTOCriterio dto2 = new DTOCriterio();
            
            dto2.setAtributo("fechaRecepcionTramite");
            dto2.setOperacion("=");
            dto2.setValor(fechaRecepcionTramite);
            
            criterioList.add(dto2);
        }
        
        
        //filtro para traerme los tramites de un cliente
        if(dni > 0){
          DTOCriterio dtoCliente = new DTOCriterio();
  
          dtoCliente.setAtributo("dniCliente");
          dtoCliente.setOperacion("=");
          dtoCliente.setValor(dni);
          criterioList.add(dtoCliente);
          Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);
          
          criterioList.clear();
          
          dtoCliente.setAtributo("cliente");
          dtoCliente.setOperacion("=");
          dtoCliente.setValor(clienteEncontrado);
        }
        
        //filtro para traerme todos los Tramites de un TipoTramite asociado
        if(codTipoTramite > 0){
          DTOCriterio dtoTipoTramite = new DTOCriterio();
  
          dtoTipoTramite.setAtributo("codTipoTramite");
          dtoTipoTramite.setOperacion("=");
          dtoTipoTramite.setValor(codTipoTramite);
          criterioList.add(dtoTipoTramite);
          TipoTramite tipoTramiteEncontrado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);
          
          criterioList.clear();
          
          dtoTipoTramite.setAtributo("tipoTramite");
          dtoTipoTramite.setOperacion("=");
          dtoTipoTramite.setValor(tipoTramiteEncontrado);
        }
        
        //filtro para traerme todos los Tramites relacionados a un Estado asociado
        if(nombreEstado.trim().length() > 0){
          DTOCriterio dtoEstado = new DTOCriterio();
  
          dtoEstado.setAtributo("nombreEstadoTramite");
          dtoEstado.setOperacion("like");
          dtoEstado.setValor(nombreEstado);
          criterioList.add(dtoEstado);
          EstadoTramite estadoEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("Estado", criterioList).get(0);
          
          criterioList.clear();
          
          dtoEstado.setAtributo("estadoTramite");
          dtoEstado.setOperacion("=");
          dtoEstado.setValor(estadoEncontrado);
          
        }
        
        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
        List<TramiteDTO> tramiteResultados = new ArrayList<>();
        
        for(Object t: objetoList){
            Tramite tramite = (Tramite) t;
            TramiteDTO tramiteDTO = new TramiteDTO();
            tramiteDTO.setNroTramie(tramite.getNroTramite());
            tramiteDTO.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite());
            tramiteDTO.setDni(tramite.getCliente().getDniCliente());
            tramiteDTO.setCodTipoTramite(tramite.getTipoTramite().getCodTipoTramite());
            tramiteDTO.setNombreEstado(tramite.getEstadoTramite().getNombreEstadoTramite());
            tramiteDTO.setFechaAnulacion(tramite.getFechaAnulacionTramite());
            tramiteResultados.add(tramiteDTO);
        }
        
        
        return tramiteResultados;
    }
    
    //Mostrar resumenTramite
    public ResumenTramiteDTO mostrarResumenTramite(int nroTramite){
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        
        if(nroTramite>0){
            DTOCriterio dto1 = new DTOCriterio();
            
            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);
            
            criterioList.add(dto1);
        }
        
        Tramite tramiteEncontrado = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
        ResumenTramiteDTO resumenDTO = new ResumenTramiteDTO();
        resumenDTO.setNroTramite(tramiteEncontrado.getNroTramite());
        resumenDTO.setFechaRecepcionTramite(tramiteEncontrado.getFechaRecepcionTramite());
        resumenDTO.setPlazoDocumentacion(tramiteEncontrado.getTipoTramite().getPlazoEntregaDocumentacionTT());
        resumenDTO.setCodTipoTramite(tramiteEncontrado.getTipoTramite().getCodTipoTramite());
        resumenDTO.setNombreTipoTramite(tramiteEncontrado.getTipoTramite().getNombreTipoTramite());
        resumenDTO.setNombreEstado(tramiteEncontrado.getEstadoTramite().getNombreEstadoTramite());
        resumenDTO.setPrecioTramite(tramiteEncontrado.getPrecioTramite());
        resumenDTO.setDniCliente(tramiteEncontrado.getCliente().getDniCliente());
        resumenDTO.setNombreCliente(tramiteEncontrado.getCliente().getNombreCliente());
        resumenDTO.setApellidoCliente(tramiteEncontrado.getCliente().getApellidoCliente());
        resumenDTO.setMailCliente(tramiteEncontrado.getCliente().getMailCliente());
        
        List<ResumenDocumentacionDTO> resumenDocList = new ArrayList<>();
        for(TramiteDocumentacion doc: tramiteEncontrado.getTramiteDocumentacion()){
            ResumenDocumentacionDTO resumenDoc = new ResumenDocumentacionDTO();
            resumenDoc.setNombreDocumentacion(doc.getDocumentacion().getNombreDocumentacion());
            resumenDoc.setFechaEntregaDoc(doc.getFechaEntregaTD());
            resumenDocList.add(resumenDoc);
        }
        
        resumenDTO.setResumenDoc(resumenDocList);
        
        return resumenDTO;  
    }
    
    public void anularTramite(int nroTramite){
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        
        if(nroTramite>0){
            DTOCriterio dto1 = new DTOCriterio();
            
            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);
            
            criterioList.add(dto1);
        }
        
        Tramite tramiteEncontrado = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
        tramiteEncontrado.setFechaAnulacionTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(tramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
    public List<TipoTramiteResumenDTO> buscarTipoTramite(String nomTipoTramite, String nomCategoria, String descTipoTramite){
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        
        if(nomTipoTramite.trim().length()>0){
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("nombreTipoTramite");
            criterio1.setOperacion("like");
            criterio1.setValor(nomTipoTramite);
            criterioList.add(criterio1);
        }
        
        if(descTipoTramite.trim().length()>0){
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("descripcionTipoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(descTipoTramite);
            criterioList.add(criterio2);
        }
        
        if(nomCategoria.trim().length()>0){
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("nombreCategoriaTipoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(nomCategoria);
            criterioList.add(criterio3);
            
            CategoriaTipoTramite categoriaEncontrada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList).get(0);
            
            criterioList.clear();
            
            DTOCriterio criterio4 = new DTOCriterio();
            criterio4.setAtributo("categoriaTipoTramite");
            criterio4.setOperacion("=");
            criterio4.setValor(categoriaEncontrada);
            criterioList.add(criterio4);
        }
        
        List objecList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
        List<TipoTramiteResumenDTO> tipoTramiteResultados = new ArrayList<>();
        
        for(Object x: objecList){
            TipoTramite tipoTramite = (TipoTramite) x;
            TipoTramiteResumenDTO resumenTipoTramite = new TipoTramiteResumenDTO();
            resumenTipoTramite.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            resumenTipoTramite.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            resumenTipoTramite.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            resumenTipoTramite.setNombreCategoriaTipoTramite(tipoTramite.getCategoriaTipoTramite().getNombreCategoriaTipoTramite());
            tipoTramiteResultados.add(resumenTipoTramite);
        }
        
        return tipoTramiteResultados;
    
    }
    
    
}
