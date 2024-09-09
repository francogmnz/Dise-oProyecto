/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite;

import ABMEstadoTramite.dtos.EstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTOIn;
import ABMEstadoTramite.dtos.NuevoEstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.EstadoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author matis
 */
public class ExpertoABMEstadoTramite {

    public List<EstadoTramiteDTO> buscarEstadosTramite(int codEstadoTramite, String nombreEstadoTramite, String descripcionEstadoTramite) {

        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        if (codEstadoTramite > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("codEstadoTramite");
            criterio1.setOperacion("=");
            criterio1.setValor(codEstadoTramite);
            lCriterio.add(criterio1);
        }

        if (nombreEstadoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("nombreEstadoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(nombreEstadoTramite);
            lCriterio.add(criterio2);
        }

        if (descripcionEstadoTramite.trim().length() > 0) {
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("descripcionEstadoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(descripcionEstadoTramite);
            lCriterio.add(criterio3);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", lCriterio);
        List<EstadoTramiteDTO> estadoTramiteResultados = new ArrayList<>();

        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            EstadoTramiteDTO estadoTramiteDTO = new EstadoTramiteDTO();
            estadoTramiteDTO.setCodEstadoTramite(estadoTramite.getCodEstadoTramite());
            estadoTramiteDTO.setNombreEstadoTramite(estadoTramite.getNombreEstadoTramite());
            estadoTramiteDTO.setDescripcionEstadoTramite(estadoTramite.getDescripcionEstadoTramite());
            estadoTramiteDTO.setFechaHoraBajaEstadoTramite(estadoTramite.getFechaHoraBajaEstadoTramite());
            estadoTramiteDTO.setFechaHoraAltaEstadoTramite(estadoTramite.getFechaHoraAltaEstadoTramite());
            estadoTramiteResultados.add(estadoTramiteDTO);
        }

        return estadoTramiteResultados;

    }

    public void agregarEstadoTramite(NuevoEstadoTramiteDTO nuevoEstadoTramiteDTO) throws EstadoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(nuevoEstadoTramiteDTO.getCodEstadoTramite());

        criterioList.add(dto);

        List lEstadoTramite = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);

        if (lEstadoTramite.size() > 0) {
            throw new EstadoTramiteException("El codigo de EstadoTramite ya existe");
        } else {
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setCodEstadoTramite(nuevoEstadoTramiteDTO.getCodEstadoTramite());
            estadoTramite.setNombreEstadoTramite(nuevoEstadoTramiteDTO.getNombreEstadoTramite());
            estadoTramite.setDescripcionEstadoTramite(nuevoEstadoTramiteDTO.getDescripcionEstadoTramite());
            estadoTramite.setFechaHoraAltaEstadoTramite(new Timestamp(System.currentTimeMillis()));

            FachadaPersistencia.getInstance().guardar(estadoTramite);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public ModificarEstadoTramiteDTO buscarEstadoTramiteAModificar(int codEstadoTramite) throws EstadoTramiteException {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codEstadoTramite);

        criterioList.add(dto);

        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        ModificarEstadoTramiteDTO modificarEstadoTramiteDTO = new ModificarEstadoTramiteDTO();
        modificarEstadoTramiteDTO.setNombreEstadoTramite(estadoTramiteEncontrado.getNombreEstadoTramite());
        modificarEstadoTramiteDTO.setCodEstadoTramite(estadoTramiteEncontrado.getCodEstadoTramite());
        modificarEstadoTramiteDTO.setDescripcionEstadoTramite(estadoTramiteEncontrado.getDescripcionEstadoTramite());

        return modificarEstadoTramiteDTO;
    }

    public void modificarEstadoTramite(ModificarEstadoTramiteDTOIn modificarEstadoTramiteDTOIn) throws EstadoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(modificarEstadoTramiteDTOIn.getCodEstadoTramite());
        criterioList.add(dto);
        
        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        criterioList.clear();

        DTOCriterio dto2 = new DTOCriterio();

        dto2.setAtributo("fechaDesdeVersion");
        dto2.setOperacion(">");
        dto2.setValor(Timestamp.from(Instant.now()));

        criterioList.add(dto2);
         
        DTOCriterio dto4 = new DTOCriterio();

        dto4.setAtributo("fechaBajaVersion");
        dto4.setOperacion("=");
        dto4.setValor(null);

        criterioList.add(dto4);

        List<Object> objetoList =  FachadaPersistencia.getInstance().buscar("Version", criterioList); 
        
        //por cada Version --> getConfigTTET
        for(Object o: objetoList){
          
            Version version = (Version) o;
            List<ConfTipoTramiteEstadoTramite> confTTETList = version.getConfTipoTramiteEstadoTramite();
            
            //por cada confTTET-->getEstadoTramite(Origen y Destino)
            for(ConfTipoTramiteEstadoTramite confTTET : confTTETList){
                
                //verifico si el EstadoTramite no se encuentra en alguna version activa.
               verificarEstadoTramite(confTTET.getEstadoTramiteOrigen(), estadoTramiteEncontrado);
               verificarEstadoTramite(confTTET.getEstadoTramiteDestino(), estadoTramiteEncontrado);
            }
        }

        estadoTramiteEncontrado.setCodEstadoTramite(modificarEstadoTramiteDTOIn.getCodEstadoTramite());
        estadoTramiteEncontrado.setNombreEstadoTramite(modificarEstadoTramiteDTOIn.getNombreEstadoTramite());
        estadoTramiteEncontrado.setDescripcionEstadoTramite(modificarEstadoTramiteDTOIn.getDescripcionEstadoTramite());

        FachadaPersistencia.getInstance().guardar(estadoTramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
    
    public void darDeBajaEstadoTramite(int codEstadoTramite) throws EstadoTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(codEstadoTramite);

        criterioList.add(dto);

        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        criterioList.clear();

        //buscar("Version", "fechaDesdeVersion >"+ fechaActual + "AND fechaBajaVersion ="+ null): list<Object>
        
        DTOCriterio dto2 = new DTOCriterio(); 
        dto2.setAtributo("fechaDesdeVersion");
        dto2.setOperacion(">");
        dto2.setValor(Timestamp.from(Instant.now()));    
        
        criterioList.add(dto2); 

        DTOCriterio dto3 = new DTOCriterio();
        dto3.setAtributo("fechaBajaVersion");
        dto3.setOperacion("=");
        dto3.setValor(null);
        
        criterioList.add(dto3);
        
        List<Object> objetoList =  FachadaPersistencia.getInstance().buscar("Version", criterioList); 
        
        //por cada Version --> getConfigTTET
        for(Object o: objetoList){
          
            Version version = (Version) o;
            List<ConfTipoTramiteEstadoTramite> confTTETList = version.getConfTipoTramiteEstadoTramite();
            
            //por cada confTTET-->getEstadoTramite(Origen y Destino)
            for(ConfTipoTramiteEstadoTramite confTTET : confTTETList){
                
               //verifico si el EstadoTramite no se encuentra en alguna version activa.
               verificarEstadoTramite(confTTET.getEstadoTramiteOrigen(), codEstadoTramite);
               verificarEstadoTramite(confTTET.getEstadoTramiteDestino(), codEstadoTramite);
            }
        }
    
        estadoTramiteEncontrado.setFechaHoraBajaEstadoTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(estadoTramiteEncontrado);
        
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

        //metodo para verificar si el Estado a eliminar es el mismo que estoy buscando con los get
        private void verificarEstadoTramite(List<EstadoTramite> estadoTramites, int codEstadoTramite) throws EstadoTramiteException {
            for (EstadoTramite e : estadoTramites) {
                if (e.getCodEstadoTramite() == codEstadoTramite) {
                    throw new EstadoTramiteException("No se pudo eliminar el Estado debido a que pertenece a una versión actual/posterior");
                }
            }
        }
        
        //metodo para verificar si el Estado a modificar es el mismo que estoy buscando con los get
        private void verificarEstadoTramite(List<EstadoTramite> estadoTramites, EstadoTramite estadoTramiteEncontrado) throws EstadoTramiteException {
            for (EstadoTramite e : estadoTramites) {
                if (e.getCodEstadoTramite() == estadoTramiteEncontrado.getCodEstadoTramite()) {
                    throw new EstadoTramiteException("No se pudo modificar el Estado debido a que pertenece a una versión actual/posterior");
                }
            }
        }
}