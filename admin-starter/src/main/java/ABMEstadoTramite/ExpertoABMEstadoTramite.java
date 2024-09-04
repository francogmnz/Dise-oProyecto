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
import entidades.EstadoTramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author matis
 */
public class ExpertoABMEstadoTramite {

    public List<EstadoTramiteDTO> buscarEstadosTramite(int codEstadoTramite, String nombreEstadoTramite) {

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
            criterio2.setAtributo("nombrEstadoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(nombreEstadoTramite);
            lCriterio.add(criterio2);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", lCriterio);
        List<EstadoTramiteDTO> estadoTramiteResultados = new ArrayList<>();

        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            EstadoTramiteDTO estadoTramiteDTO = new EstadoTramiteDTO();
            estadoTramiteDTO.setCodEstadoTramite(estadoTramite.getCodEstadoTramite());
            estadoTramiteDTO.setNombreEstadoTramite(estadoTramite.getNombreEstadoTramite());
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

    public ModificarEstadoTramiteDTO buscarEstadoTramiteAModificar(int codEstadoTramite) {
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

    public void modificarEstadoTramite(ModificarEstadoTramiteDTOIn modificarEstadoTramiteDTOIn) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("codEstadoTramite");
        dto.setOperacion("=");
        dto.setValor(modificarEstadoTramiteDTOIn.getCodEstadoTramite());

        EstadoTramite estadoTramiteEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

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
        
        estadoTramiteEncontrado.setFechaHoraBajaEstadoTramite(new Timestamp(System.currentTimeMillis()));
        
        FachadaPersistencia.getInstance().guardar(estadoTramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
}
