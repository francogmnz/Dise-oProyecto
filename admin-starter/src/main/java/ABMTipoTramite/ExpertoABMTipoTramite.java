/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMTipoTramite;

import ABMTipoTramite.*;
import ABMTipoTramite.dtos.TipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTO;
import ABMTipoTramite.dtos.ModificarTipoTramiteDTOIn;
import ABMTipoTramite.dtos.NuevoTipoTramiteDTO;
import ABMTipoTramite.exceptions.TipoTramiteException;
import entidades.TipoTramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
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
            criterio1.setValor(codTipoTramite); // valor es un objeto en DTOCritero..
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
        List<TipoTramiteDTO> tipoTramiteResultado = new ArrayList<>();

        for (Object x : objetoList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            TipoTramiteDTO tipoTramiteDTO = new TipoTramiteDTO();
            tipoTramiteDTO.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            tipoTramiteDTO.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            tipoTramiteDTO.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            tipoTramiteDTO.setDescripcionWebTipoTramite(tipoTramite.getDescripcionWebTipoTramite());
            tipoTramiteDTO.setFechaHoraBajaTipoTramite(tipoTramite.getFechaHoraBajaTipoTramite());
            tipoTramiteDTO.setPlazoEntregaDocumentacionTT(tipoTramite.getPlazoEntregaDocumentacionTT());
            tipoTramiteResultado.add(tipoTramiteDTO);
        }

        return tipoTramiteResultado;
    
    }
    


    public void agregarTipoTramite(NuevoTipoTramiteDTO nuevoTipoTramiteDTO) throws TipoTramiteException {
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
            


            FachadaPersistencia.getInstance().guardar(tipoTramite);
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

        return modificarTipoTramiteDTO;
    }

        
        public void modificarTipoTramite(ModificarTipoTramiteDTOIn modificarTipoTramiteDTOIn){
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
   
        
        FachadaPersistencia.getInstance().guardar(tipoTramiteEncontrada);
        FachadaPersistencia.getInstance().finalizarTransaccion();
     
        
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
