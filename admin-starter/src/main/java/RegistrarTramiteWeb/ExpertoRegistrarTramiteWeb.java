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
    private Tramite tramiteEnProceso;
    
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
            
            cliente = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioBuscarClienteList).get(0);

            
            if (dniCliente != cliente.getDniCliente()) {
                throw new RegistrarTramiteWebException("Cliente no encontrado, intente nuevamente.");
            }
        } catch (IndexOutOfBoundsException e) {
            
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

    public List<DTOTipoTramite> listarTipoTramites(int codCategoriaTipoTramite) throws RegistrarTramiteWebException {
    
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
        
        //CategoriaTipoTramite categoriaTipoTramiteRelacionada = (CategoriaTipoTramite) FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriaTTRelacionadaList).get(0);
        
        List<Object> categorias = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioCategoriaTTRelacionadaList);

        if (categorias == null || categorias.isEmpty()) {
            throw new RegistrarTramiteWebException("No se encontró la categoría seleccionada.");
        }

    CategoriaTipoTramite categoriaTipoTramiteRelacionada = (CategoriaTipoTramite) categorias.get(0);
        
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
   
/*    
    public static int generarNroTramite(int codTipoTramite) {
        return (int) (System.currentTimeMillis() + codTipoTramite);
    }
*/
    public int generarNroTramite() {
        int ultimoNroTramite = buscarUltimoNroTramite();
        return ultimoNroTramite + 1;  
    }

    public static int buscarUltimoNroTramite() {
 
        List<DTOCriterio> criterioUltimoNroTramiteList = new ArrayList<>();


        DTOCriterio criterioNroTramite = new DTOCriterio();
        criterioNroTramite.setAtributo("nroTramite");
        criterioNroTramite.setOperacion("desc"); 
        criterioUltimoNroTramiteList.add(criterioNroTramite);

        
        List<Object> tramiteList = FachadaPersistencia.getInstance().buscar("Tramite", criterioUltimoNroTramiteList);

        // Si no hay tramite devuelvo 0 
        if (tramiteList == null || tramiteList.isEmpty()) {
            return 0;
        }

        
        Tramite ultimoTramite = (Tramite) tramiteList.get(0);

        return ultimoTramite.getNroTramite();
    }

    
    public int generarCodTD() {
        int ultimoCodTD = buscarUltimoCodTD();
        return ultimoCodTD + 1;  
    }
    
    public static int buscarUltimoCodTD() {


        List<DTOCriterio> criterioUltimoCodTDList = new ArrayList<>();

 
        DTOCriterio criterioCodTD = new DTOCriterio();
        criterioCodTD.setAtributo("codTD");
        criterioCodTD.setOperacion("desc"); 
        criterioUltimoCodTDList.add(criterioCodTD);


        List<Object> tramiteDocumentacionList = FachadaPersistencia.getInstance().buscar("TramiteDocumentacion", criterioUltimoCodTDList);


        if (tramiteDocumentacionList == null || tramiteDocumentacionList.isEmpty()) {
            return 0;
        }


        TramiteDocumentacion ultimoTramiteDocumentacion = (TramiteDocumentacion) tramiteDocumentacionList.get(0);

        return ultimoTramiteDocumentacion.getCodTD();
    }
    
    
    public DTOResumen mostrarResumenTipoTramite(int codTipoTramite){
        
        TipoTramite tipoTramiteRelacionado = null;
        EstadoTramite estadoTramite = null;
        Version version = null;
        ListaPrecios listaPrecios = null;
 
        List<DTOCriterio> criterioTipoTramiteRelacionadoList = new ArrayList<>();
        DTOCriterio criterioCodigoTT = new DTOCriterio();
        
        criterioCodigoTT.setAtributo("codTipoTramite");
        criterioCodigoTT.setOperacion("=");
        criterioCodigoTT.setValor(codTipoTramite);
        
        criterioTipoTramiteRelacionadoList.add(criterioCodigoTT);
        
        DTOCriterio criterioFechaHoraBajaTT = new DTOCriterio();
        
        criterioFechaHoraBajaTT.setAtributo("fechaHoraBajaTipoTramite");
        criterioFechaHoraBajaTT.setOperacion("=");
        criterioFechaHoraBajaTT.setValor(null);
        
        criterioTipoTramiteRelacionadoList.add(criterioFechaHoraBajaTT);
        
        List<Object> tipoTramiteList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramiteRelacionadoList);
        if (tipoTramiteList != null && !tipoTramiteList.isEmpty()) {
            tipoTramiteRelacionado = (TipoTramite) tipoTramiteList.get(0);
        } else {
            System.out.println("Error: No se encontró un TipoTramite con los criterios especificados.");
            //return null;  // ver si meto un cartelito de error
        }
        
        List<DTOCriterio> criterioEstadoTramiteList = new ArrayList<>();
        DTOCriterio criterioNombreEstadoTramite = new DTOCriterio();
        
        criterioNombreEstadoTramite.setAtributo("nombreEstadoTramite");
        criterioNombreEstadoTramite.setOperacion("=");
        criterioNombreEstadoTramite.setValor("Iniciado"); // ver q nombre quedo al final 
        
        criterioEstadoTramiteList.add(criterioNombreEstadoTramite);
        
        DTOCriterio criterioFechaHoraBajaET = new DTOCriterio();
        
        criterioFechaHoraBajaET.setAtributo("fechaHoraBajaEstadoTramite");
        criterioFechaHoraBajaET.setOperacion("=");
        criterioFechaHoraBajaET.setValor(null);
        
        criterioEstadoTramiteList.add(criterioFechaHoraBajaET);
        
        List<Object> estadoTramiteList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioEstadoTramiteList);
        if (estadoTramiteList != null && !estadoTramiteList.isEmpty()) {
            estadoTramite = (EstadoTramite) estadoTramiteList.get(0);
        } else {
            System.out.println("Error: No se encontró un EstadoTramite con los criterios especificados.");
            //return null;  // ver si meto un cartelito de error
        }
        
        Tramite nuevoTramite = new Tramite();
        
        int nroTramite = generarNroTramite();
        nuevoTramite.setNroTramite(nroTramite);
        nuevoTramite.setCliente(cliente);
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
        
        List<Object> versionList = FachadaPersistencia.getInstance().buscar("Version", criterioUltimaVersionTTList);
        if (versionList != null && !versionList.isEmpty()) {
            version = (Version) versionList.get(0);
        } else {
            System.out.println("Error: No se encontró una Versión con los criterios especificados.");
            //return null;  // ver si meto un cartelito de error
        }
        
        nuevoTramite.setVersion(version);
           
        TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite();
        
        tramiteEstadoTramite.setFechaHastaTET(null);
        tramiteEstadoTramite.setFechaDesdeTET(new Timestamp(System.currentTimeMillis()));
        tramiteEstadoTramite.setEstadoTramite(estadoTramite);
        
        nuevoTramite.addTramiteEstadoTramite(tramiteEstadoTramite); //
        
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = tipoTramiteRelacionado.getTipoTramiteDocumentacion();
        
        // comprobar este if, no estaba en secuencia 
        for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacion){
            if (ttd.getFechaHoraBajaTTD() == null){
                Documentacion documentacion = ttd.getDocumentacion();
                TramiteDocumentacion tramiteDocumentacion = new TramiteDocumentacion();
                
                tramiteDocumentacion.setArchivoTD(null);
                int nuevoCodTD = generarCodTD();
                tramiteDocumentacion.setCodTD(nuevoCodTD);
                tramiteDocumentacion.setFechaEntregaTD(null);
                tramiteDocumentacion.setDocumentacion(documentacion);
                
                nuevoTramite.addTramiteDocumentacion(tramiteDocumentacion);
            }
        
            
        
        }

        List<DTOCriterio> criterioListaPreciosList = new ArrayList<>();
        DTOCriterio criterioFechaHoraBajaLP = new DTOCriterio();
        
        criterioFechaHoraBajaLP.setAtributo("fechaHoraBajaListaPrecios");
        criterioFechaHoraBajaLP.setOperacion("=");
        criterioFechaHoraBajaLP.setValor(null);
        
        criterioListaPreciosList.add(criterioFechaHoraBajaLP);
        
        DTOCriterio criterioFechaHoraDesdeLP = new DTOCriterio();
        
        criterioFechaHoraDesdeLP.setAtributo("fechaHoraDesdeListaPrecios");
        criterioFechaHoraDesdeLP.setOperacion("<");
        criterioFechaHoraDesdeLP.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioListaPreciosList.add(criterioFechaHoraDesdeLP);

        DTOCriterio criterioFechaHoraHastaLP = new DTOCriterio();
        
        criterioFechaHoraHastaLP.setAtributo("fechaHoraHastaListaPrecios");
        criterioFechaHoraHastaLP.setOperacion(">");
        criterioFechaHoraHastaLP.setValor(new Timestamp(System.currentTimeMillis()));
        
        criterioListaPreciosList.add(criterioFechaHoraHastaLP); 
        
        List<Object> listaPreciosList = FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioListaPreciosList);
        if (listaPreciosList != null && !listaPreciosList.isEmpty()) {
            listaPrecios = (ListaPrecios) listaPreciosList.get(0);
        } else {
            System.out.println("Error: No se encontró una ListaPrecios con los criterios especificados.");
            //return null;  ver si meto un cartelito de error
        }
        
        List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = listaPrecios.getTipoTramiteListaPrecios();
        
        for(TipoTramiteListaPrecios ttlp: tipoTramiteListaPrecios){
            TipoTramite tipoTramite = ttlp.getTipoTramite();
            System.out.println("el codigo del tramite es:" + tipoTramite.getCodTipoTramite());
            if(tipoTramite.getCodTipoTramite() == tipoTramiteRelacionado.getCodTipoTramite()){
                nuevoTramite.setPrecioTramite(ttlp.getPrecioTipoTramite());
            }
        
        }

        
        DTOResumen dtoResumen = new DTOResumen();
        
        
        dtoResumen.setNombreCliente(nuevoTramite.getCliente().getNombreCliente());
        dtoResumen.setApellidoCliente(nuevoTramite.getCliente().getApellidoCliente());
        dtoResumen.setDniCliente(nuevoTramite.getCliente().getDniCliente());
        dtoResumen.setMailCliente(nuevoTramite.getCliente().getMailCliente());
        
        dtoResumen.setNombreTipoTramite(tipoTramiteRelacionado.getNombreTipoTramite());
        dtoResumen.setDescripcionTipoTramite(tipoTramiteRelacionado.getDescripcionTipoTramite());
        dtoResumen.setPlazoEntregaDocumentacionTT(tipoTramiteRelacionado.getPlazoEntregaDocumentacionTT());
        
        dtoResumen.setPrecioTramite(nuevoTramite.getPrecioTramite()); 
        
        tramiteEnProceso = nuevoTramite;
        
        return dtoResumen;

        
        
       
    }
     
    public DTONumeroTramite registrarTramite() throws RegistrarTramiteWebException {
        if (tramiteEnProceso == null) {
            throw new RegistrarTramiteWebException("No hay un trámite en proceso para registrar.");
        }

        FachadaPersistencia.getInstance().iniciarTransaccion();
        //TipoTramite tipoTramiteRelacionado = tramiteEnProceso.getTipoTramite();
        //tipoTramiteRelacionado.getTipoTramiteDocumentacion().size();
        for (TramiteEstadoTramite tramiteEstadoTramite : tramiteEnProceso.getTramiteEstadoTramite()) {
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);
        } 

        for (TramiteDocumentacion tramiteDocumentacion : tramiteEnProceso.getTramiteDocumentacion()) {
            FachadaPersistencia.getInstance().guardar(tramiteDocumentacion);
        }        

        FachadaPersistencia.getInstance().guardar(tramiteEnProceso);
        DTONumeroTramite dtoNumeroTramite = new DTONumeroTramite();

        //List<DTODocumentacion> DTODocumentacionList = new ArrayList<>();    
        int numeroTramiteObtenido = tramiteEnProceso.getNroTramite();
        
        
        List<DTOCriterio> criterioTipoTramiteParaEvitarLazy = new ArrayList<>();
        DTOCriterio criterioFechaHoraBajaTT = new DTOCriterio();
        
        criterioFechaHoraBajaTT.setAtributo("fechaHoraBajaTipoTramite");
        criterioFechaHoraBajaTT.setOperacion("=");
        criterioFechaHoraBajaTT.setValor(null);
        
        criterioTipoTramiteParaEvitarLazy.add(criterioFechaHoraBajaTT);
        
        DTOCriterio criterioCodigoTT = new DTOCriterio();
        
        criterioCodigoTT.setAtributo("codTipoTramite");
        criterioCodigoTT.setOperacion("=");
        criterioCodigoTT.setValor(tramiteEnProceso.getTipoTramite().getCodTipoTramite());
        
        criterioTipoTramiteParaEvitarLazy.add(criterioCodigoTT);
        
        TipoTramite tipoTramiteEvitandoLazy = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioTipoTramiteParaEvitarLazy).get(0);
        
        
        /*
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = tramiteEnProceso.getTipoTramite().getTipoTramiteDocumentacion();
        for(TipoTramiteDocumentacion ttd : tipoTramiteDocumentacion){
            if(ttd.getFechaHoraBajaTTD() == null){
                Documentacion documentacion = ttd.getDocumentacion();
                if(documentacion.getFechaHoraBajaDocumentacion() == null){
                    DTODocumentacion dtoDocumentacion = new DTODocumentacion();    
                    dtoDocumentacion.setNombreDocumentacion(documentacion.getNombreDocumentacion());
                    
                    DTODocumentacionList.add(dtoDocumentacion);
                }
            }
        }  
        */
        
        
        
        List<TipoTramiteDocumentacion> tipoTramiteDocumentacion = tipoTramiteEvitandoLazy.getTipoTramiteDocumentacion();
        for(TipoTramiteDocumentacion ttd: tipoTramiteDocumentacion){
            if (ttd.getFechaHoraBajaTTD() == null){
                Documentacion documentacion = ttd.getDocumentacion();
                if (documentacion.getFechaHoraBajaDocumentacion() == null) {
                DTODocumentacion dtoDocumentacion = new DTODocumentacion();    
                dtoDocumentacion.setNombreDocumentacion(documentacion.getNombreDocumentacion());
                
                dtoNumeroTramite.addDTODocumentacion(dtoDocumentacion);
                
                    }
                }
            }        
        
        
        
        
        int plazoEntregaDocumentacionTT = tipoTramiteEvitandoLazy.getPlazoEntregaDocumentacionTT();
        
        

        
        
        //DTONumeroTramite dtoNumeroTramite = new DTONumeroTramite();      
        dtoNumeroTramite.setNumeroTramite(numeroTramiteObtenido);
        dtoNumeroTramite.setPlazoEntregaDocumentacionTT(plazoEntregaDocumentacionTT);
        //dtoNumeroTramite.setDocumentaciones(DTODocumentacionList);
        
        FachadaPersistencia.getInstance().finalizarTransaccion();

        // termino y vuelvo a null el tramite en proceso
        tramiteEnProceso = null;

        return dtoNumeroTramite;
    }

    
        public void resetearEstado() {
        this.cliente = null;
        this.tramiteEnProceso = null;
        
    }

}
/*
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
*/