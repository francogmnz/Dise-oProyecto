/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb;

import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import RegistrarTramiteWeb.dtos.DTOCliente;
import RegistrarTramiteWeb.dtos.DTODocumentacion;
import RegistrarTramiteWeb.dtos.DTONumeroTramite;
import RegistrarTramiteWeb.dtos.DTOResumen;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;
import entidades.CategoriaTipoTramite;
import entidades.Cliente;
import entidades.ConfTipoTramiteEstadoTramite;
import entidades.Documentacion;
import entidades.EstadoTramite;
import entidades.ListaPrecios;
import entidades.TipoTramite;
import entidades.TipoTramiteDocumentacion;
import entidades.TipoTramiteListaPrecios;
import entidades.Tramite;
import entidades.TramiteDocumentacion;
import entidades.TramiteEstadoTramite;
import entidades.Version;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

/**
 *
 * @author licciardi
 */
public class ExpertoRegistrarTramiteWeb {
    
    private Cliente cliente;
    
    public DTOCliente buscarClienteIngresado(int dniCliente)  throws RegistrarTramiteWebException{
        List<DTOCriterio> criterioBuscarClienteList = new ArrayList<>();
        DTOCriterio criterioDniIngresado = new DTOCriterio();
        
        criterioDniIngresado.setAtributo("dniCliente");
        criterioDniIngresado.setOperacion("=");
        criterioDniIngresado.setValor(dniCliente);
        
        criterioBuscarClienteList.add(criterioDniIngresado);
        
        DTOCriterio criterioFechaHoraBajaCliente = new DTOCriterio();
        
        criterioFechaHoraBajaCliente.setAtributo("fechaHoraBajaCliente");
        criterioFechaHoraBajaCliente.setOperacion("=");
        criterioFechaHoraBajaCliente.setValor(null);
        
        criterioBuscarClienteList.add(criterioFechaHoraBajaCliente);
        
        //Cliente clienteIngresado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioBuscarClienteList).get(0);
        try {
            // Intentar obtener el cliente
            this.cliente = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioBuscarClienteList).get(0);

            // Validar si el DNI coincide
            if (dniCliente != cliente.getDniCliente()) {
                throw new RegistrarTramiteWebException("Cliente no encontrado, intente nuevamente.");
            }
        } catch (IndexOutOfBoundsException e) {
            // Capturar cuando la búsqueda no arroja resultados
            throw new RegistrarTramiteWebException("Cliente no encontrado, intente nuevamente.");
        }
        DTOCliente dtoCliente = new DTOCliente();
        dtoCliente.setDniCliente(cliente.getDniCliente());
        dtoCliente.setNombreCliente(cliente.getNombreCliente());
        dtoCliente.setApellidoCliente(cliente.getApellidoCliente());
        dtoCliente.setMailCliente(cliente.getMailCliente());
                
        return dtoCliente;
                
        
    }
    
    public List<DTOCategoriaTipoTramite> listarCategoriasTipoTramtite(){
        List<DTOCriterio> criterioListarCategoriasTTList = new ArrayList<>();
        DTOCriterio criterioFechaHoraBajaCTT = new DTOCriterio();
        
        criterioFechaHoraBajaCTT.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        criterioFechaHoraBajaCTT.setOperacion("=");
        criterioFechaHoraBajaCTT.setValor(null);
        
        criterioListarCategoriasTTList.add(criterioFechaHoraBajaCTT);
        
        List categoriasTipoTramiteObjectList = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioListarCategoriasTTList);
        List<DTOCategoriaTipoTramite> categoriasTipoTramiteAListar = new ArrayList<>();
        for(Object x: categoriasTipoTramiteObjectList){
            CategoriaTipoTramite categoriaTipoTramite = (CategoriaTipoTramite) x;
            DTOCategoriaTipoTramite dtoCategoriaTipoTramite = new DTOCategoriaTipoTramite();
            
            dtoCategoriaTipoTramite.setCodCategoriaTipoTramite(categoriaTipoTramite.getCodCategoriaTipoTramite());
            dtoCategoriaTipoTramite.setNombreCategoriaTipoTramite(categoriaTipoTramite.getNombreCategoriaTipoTramite());
            dtoCategoriaTipoTramite.setDescripcionCategoriaTipoTramite(categoriaTipoTramite.getDescripcionCategoriaTipoTramite());
            dtoCategoriaTipoTramite.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramite.getDescripcionWebCategoriaTipoTramite());
            
            categoriasTipoTramiteAListar.add(dtoCategoriaTipoTramite);
        }
        return categoriasTipoTramiteAListar;
        
    }

    public List<DTOTipoTramite> listarTipoTramites(int codCategoriaTipoTramite){
    
        List<DTOCriterio> criterioCategoriaTTRelacionadaList = new ArrayList<>();
        DTOCriterio criterioCodigoCTT = new DTOCriterio();
        
        criterioCodigoCTT.setAtributo("codCategoriaTipoTramite");
        criterioCodigoCTT.setOperacion("=");
        criterioCodigoCTT.setValor(codCategoriaTipoTramite);
        
        criterioCategoriaTTRelacionadaList.add(criterioCodigoCTT);
        
        DTOCriterio criterioFechaHoraBajaCTT = new DTOCriterio();
        
        criterioFechaHoraBajaCTT.setAtributo("fechaHoraBajaCategoriaTipoTramite");
        criterioFechaHoraBajaCTT.setOperacion("=");
        criterioFechaHoraBajaCTT.setValor(null);
        
        criterioCategoriaTTRelacionadaList.add(criterioFechaHoraBajaCTT);
        
        CategoriaTipoTramite categoriaTipoTramiteRelacionada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriaTTRelacionadaList).get(0);
        
        List<DTOCriterio> criterioTipoTramitesList = new ArrayList<>();
        DTOCriterio criterioFechaHoraBajaTT = new DTOCriterio();
        
        criterioFechaHoraBajaTT.setAtributo("fechaHoraBajaTipoTramite");
        criterioFechaHoraBajaTT.setOperacion("=");
        criterioFechaHoraBajaTT.setValor(null);
        
        criterioTipoTramitesList.add(criterioFechaHoraBajaTT);
        
        DTOCriterio criterioCategoriaTTRelacionada = new DTOCriterio();
        
        criterioCategoriaTTRelacionada.setAtributo("categoriaTipoTramite");
        criterioCategoriaTTRelacionada.setOperacion("=");
        criterioCategoriaTTRelacionada.setValor(categoriaTipoTramiteRelacionada);
        
        criterioTipoTramitesList.add(criterioCategoriaTTRelacionada);

        List tipoTramitesObjectList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramitesList);
        List<DTOTipoTramite> tipoTramitesAListar = new ArrayList<>();
        
        for(Object x: tipoTramitesObjectList){
            TipoTramite tipoTramite = (TipoTramite) x;
            DTOTipoTramite dtoTipoTramite = new DTOTipoTramite();
            
            dtoTipoTramite.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            dtoTipoTramite.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            dtoTipoTramite.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            dtoTipoTramite.setDescripcionWebTipoTramite(tipoTramite.getDescripcionWebTipoTramite());
            
            List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = tipoTramite.getTipoTramiteDocumentacion();
            
            for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacion){
                if (ttd.getFechaHoraBajaTTD() == null){
                Documentacion documentacion = ttd.getDocumentacion();
                
                if (documentacion.getFechaHoraBajaDocumentacion() == null) {
                DTODocumentacion dtoDocumentacion = new DTODocumentacion();    
                dtoDocumentacion.setNombreDocumentacion(documentacion.getNombreDocumentacion());
                
                dtoTipoTramite.addDTODocumentacion(dtoDocumentacion);
                
                    }
                }
            }
            
            tipoTramitesAListar.add(dtoTipoTramite);
        }

        return tipoTramitesAListar;

    }
    
    public DTOResumen confirmacionTipoTramite(int codTipoTramite){
        
    
        List<DTOCriterio> criterioTipoTramiteRelacionadoList = new ArrayList<>();
        DTOCriterio criterioCodigoTT = new DTOCriterio();
        
        criterioCodigoTT.setAtributo("codCategoriaTipoTramite");
        criterioCodigoTT.setOperacion("=");
        criterioCodigoTT.setValor(codTipoTramite);
        
        criterioTipoTramiteRelacionadoList.add(criterioCodigoTT);
        
        DTOCriterio criterioFechaHoraBajaTT = new DTOCriterio();
        
        criterioFechaHoraBajaTT.setAtributo("fechaHoraBajaTipoTramite");
        criterioFechaHoraBajaTT.setOperacion("=");
        criterioFechaHoraBajaTT.setValor(null);
        
        criterioTipoTramiteRelacionadoList.add(criterioFechaHoraBajaTT);
        
        TipoTramite tipoTramiteRelacionado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramiteRelacionadoList).get(0);
        
        List<DTOCriterio> criterioEstadoTramiteList = new ArrayList<>();
        DTOCriterio criterioNombreEstadoTramite = new DTOCriterio();
        
        criterioNombreEstadoTramite.setAtributo("nombreEstadoTramite");
        criterioNombreEstadoTramite.setOperacion("=");
        criterioNombreEstadoTramite.setValor("Pendiente Documentacion"); // ver q nombre quedo al final 
        
        criterioEstadoTramiteList.add(criterioNombreEstadoTramite);
        
        DTOCriterio criterioFechaHoraBajaET = new DTOCriterio();
        
        criterioFechaHoraBajaET.setAtributo("fechaHoraBajaEstadoTramite");
        criterioFechaHoraBajaET.setOperacion("=");
        criterioFechaHoraBajaET.setValor(null);
        
        criterioEstadoTramiteList.add(criterioFechaHoraBajaET);
        
        EstadoTramite estadoTramite = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioEstadoTramiteList).get(0);
        
        Tramite nuevoTramite = new Tramite();
        
        nuevoTramite.setCliente(this.cliente);
        nuevoTramite.setTipoTramite(tipoTramiteRelacionado);
        nuevoTramite.setEstadoTramite(estadoTramite);
        nuevoTramite.setFechaRecepcionTramite(new Timestamp(System.currentTimeMillis()));
        nuevoTramite.setFechaInicioTramite(null);
        nuevoTramite.setFechaFinTramite(null);
        nuevoTramite.setFechaAnulacionTramite(null);
        
        List<DTOCriterio> criterioUltimaVersionTTList = new ArrayList<>();
        DTOCriterio criterioFechaDesdeVersion = new DTOCriterio();
        
        criterioFechaDesdeVersion.setAtributo("fechaDesdeVersion");
        criterioFechaDesdeVersion.setOperacion("<");
        criterioFechaDesdeVersion.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioUltimaVersionTTList.add(criterioFechaDesdeVersion);
        
        DTOCriterio criterioFechaHastaVersion = new DTOCriterio();
        
        criterioFechaHastaVersion.setAtributo("fechaHastaVersion");
        criterioFechaHastaVersion.setOperacion(">");
        criterioFechaHastaVersion.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioUltimaVersionTTList.add(criterioFechaHastaVersion);
        
        DTOCriterio criterioTTRelacionado = new DTOCriterio();
        
        criterioTTRelacionado.setAtributo("tipoTramite");
        criterioTTRelacionado.setOperacion("=");
        criterioTTRelacionado.setValor(tipoTramiteRelacionado);
        
        criterioUltimaVersionTTList.add(criterioTTRelacionado);
        
        Version version = (Version) FachadaPersistencia.getInstance().buscar("Version", criterioUltimaVersionTTList);
        
        nuevoTramite.setVersion(version);
        
/*  Creo que esta de mas.. ya fue seteado anteriormente el estado y con sus respectivas verificaciones
        List<ConfTipoTramiteEstadoTramite> confTipoTramiteEstadoTramiteList = version.getConfTipoTramiteEstadoTramite();
        
        for(ConfTipoTramiteEstadoTramite cttet: confTipoTramiteEstadoTramiteList){
            List<EstadoTramite> estadosTramite = cttet.getEstadoTramiteOrigen();
            
            for(EstadoTramite estadoTramiteValidacion : estadosTramite){
            String nombreEstadoTramite = estadoTramiteValidacion.getNombreEstadoTramite();
            if("Pendiente Documentacion" == nombreEstadoTramite){
            nuevoTramite.setEstadoTramite(estadoTramite);
            }
            }
            
            
        }
        
*/       
        TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite();
        
        tramiteEstadoTramite.setFechaHoraBajaTET(null);
        tramiteEstadoTramite.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
        tramiteEstadoTramite.setEstadoTramite(estadoTramite);
        
        nuevoTramite.addTramiteEstadoTramite(tramiteEstadoTramite); //
        
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = tipoTramiteRelacionado.getTipoTramiteDocumentacion();
        
        // comprobar este if, no estaba en secuencia 
        for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacion){
            if (ttd.getFechaHoraBajaTTD() == null){
                Documentacion documentacion = ttd.getDocumentacion();
                TramiteDocumentacion tramiteDocumentacion = new TramiteDocumentacion();
                
                tramiteDocumentacion.setArchivoTD(null);
                //tramiteDocumentacion.setCodTD(0);
                tramiteDocumentacion.setFechaEntregaTD(null);
                tramiteDocumentacion.setDocumentacion(documentacion);
                
                nuevoTramite.addTramiteDocumentacion(tramiteDocumentacion);
            }
        
            
        
        }
        
        List<DTOCriterio> criterioListaPreciosList = new ArrayList<>();
        DTOCriterio criterioFechaHoraBajaLP = new DTOCriterio();
        
        criterioFechaHoraBajaLP.setAtributo("fechaHoraBajaListaPrecios");
        criterioFechaHoraBajaLP.setOperacion("null");
        criterioFechaHoraBajaLP.setValor(null);
        
        criterioListaPreciosList.add(criterioFechaHoraBajaLP);
        
        DTOCriterio criterioFechaHoraDesdeLP = new DTOCriterio();
        
        criterioFechaHoraDesdeLP.setAtributo("fechaHoraDesdeListaPrecios");
        criterioFechaHoraDesdeLP.setOperacion("<");
        criterioFechaHoraDesdeLP.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioListaPreciosList.add(criterioFechaHoraDesdeLP);

        DTOCriterio criterioFechaHoraHastaLP = new DTOCriterio();
        
        criterioFechaHoraHastaLP.setAtributo("fechaHoraHastaListaPrecios");
        criterioFechaHoraHastaLP.setOperacion("<");
        criterioFechaHoraHastaLP.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioListaPreciosList.add(criterioFechaHoraHastaLP); 
        
        ListaPrecios listaPrecios = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioListaPreciosList).get(0);
        
        List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = listaPrecios.getTipoTramiteListaPrecios();
        
        for(TipoTramiteListaPrecios ttlp: tipoTramiteListaPrecios){
            TipoTramite tipoTramite = ttlp.getTipoTramite();
            if(tipoTramite.getCodTipoTramite() == tipoTramiteRelacionado.getCodTipoTramite()){
                nuevoTramite.setPrecioTramite(ttlp.getPrecioTipoTramite());
            }
        
        }
        
        
        DTOResumen dtoResumen = new DTOResumen();
        
        
        dtoResumen.setNombreCliente(this.cliente.getNombreCliente());
        dtoResumen.setApellidoCliente(this.cliente.getApellidoCliente());
        dtoResumen.setDniCliente(this.cliente.getDniCliente());
        dtoResumen.setMailCliente(this.cliente.getMailCliente());
        
        dtoResumen.setNombreTipoTramite(tipoTramiteRelacionado.getNombreTipoTramite());
        dtoResumen.setDescripcionTipoTramite(tipoTramiteRelacionado.getDescripcionTipoTramite());
        dtoResumen.setPlazoEntregaDocumentacionTT(tipoTramiteRelacionado.getPlazoEntregaDocumentacionTT());
        
        dtoResumen.setPrecioTramite(nuevoTramite.getPrecioTramite());
        
        return dtoResumen;
        
        
        
       
    }
    
    public DTONumeroTramite registrarTramite(Tramite nuevoTramite) throws RegistrarTramiteWebException {
        
        FachadaPersistencia.getInstance().iniciarTransaccion();
    
        FachadaPersistencia.getInstance().guardar(nuevoTramite);
        
        for (TramiteEstadoTramite tramiteEstadoTramite : nuevoTramite.getTramiteEstadoTramite()) {
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);
        } 
        
        for (TramiteDocumentacion tramiteDocumentacion : nuevoTramite.getTramiteDocumentacion()) {
            FachadaPersistencia.getInstance().guardar(tramiteDocumentacion);
        }        
        
        int numeroTramiteObtenido = nuevoTramite.getNroTramite();
        DTONumeroTramite dtoNumeroTramite = new DTONumeroTramite();
        dtoNumeroTramite.setNumeroTramite(numeroTramiteObtenido);
        
        FachadaPersistencia.getInstance().finalizarTransaccion();
        
        return dtoNumeroTramite;
        
    }
}