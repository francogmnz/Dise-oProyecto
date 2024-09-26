package RegistrarTramite;

import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTODocumentacion;
import RegistrarTramite.dtos.DTOFile;
import RegistrarTramite.dtos.DTOTipoTramite;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.dtos.DTOTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.AgendaConsultor;
import entidades.CategoriaTipoTramite;
import entidades.Cliente;
import entidades.Consultor;
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
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoRegistrarTramite {

    private Cliente clienteEncontrado;
    private TipoTramite tipoTramiteEncontrado;

    // mostrarComboEstados(): List<DTOEstadoTramite>
    public List<DTOEstadoTramite> mostrarComboEstados() {

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", null);

        List<DTOEstadoTramite> estadosTramite = new ArrayList<>();
        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            DTOEstadoTramite dtoEstadoTramite = new DTOEstadoTramite();

            dtoEstadoTramite.setNombreEstado(estadoTramite.getNombreEstadoTramite());
            estadosTramite.add(dtoEstadoTramite);
        }
        return estadosTramite;
    }

    // mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, nombreEstadoTramite): List<DTOTramite>
    public List<DTOTramite> mostrarTramites(int nroTramite, Date fechaRecepcionTramite, int dniCliente, int codTipoTramite, String nombreEstadoTramite) {

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        // Filtro para el nroTramite
        if (nroTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();

            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);

            criterioList.add(dto1);
        }

        // Filtro para la fechaRecepcionTramite
        if (fechaRecepcionTramite != null) {
            // Buscamos asi porque hay un problema con Timestamp
            // Establecer la hora de inicio del día
            Calendar calInicio = Calendar.getInstance();
            calInicio.setTime(fechaRecepcionTramite);
            calInicio.set(Calendar.HOUR_OF_DAY, 0);
            calInicio.set(Calendar.MINUTE, 0);
            Date fechaInicioDia = calInicio.getTime();

            // Establecer la hora de fin del día
            Calendar calFin = Calendar.getInstance();
            calFin.setTime(fechaRecepcionTramite);
            calFin.set(Calendar.HOUR_OF_DAY, 23);
            calFin.set(Calendar.MINUTE, 59);
            Date fechaFinDia = calFin.getTime();

            // Crear criterio de rango de fechas
            DTOCriterio dtoFechaRango = new DTOCriterio();
            dtoFechaRango.setAtributo("fechaRecepcionTramite");
            dtoFechaRango.setOperacion("range");  // creado en FachadaInterna
            dtoFechaRango.setValor(new Date[]{fechaInicioDia, fechaFinDia});
            criterioList.add(dtoFechaRango);
        }

        // Filtro para Cliente
        // Buscar("Cliente", "dniCliente = " + dniCliente): List<Object>
        if (dniCliente > 0) {
            DTOCriterio criterioCliente = new DTOCriterio();

            criterioCliente.setAtributo("dniCliente");
            criterioCliente.setOperacion("=");
            criterioCliente.setValor(dniCliente);
            criterioList.add(criterioCliente);

            Cliente clienteEncontrado = null;

            List lClientes = FachadaPersistencia.getInstance().buscar("Cliente", criterioList);
            if (lClientes.size() > 0) {
                clienteEncontrado = (Cliente) lClientes.get(0);
            }

//            criterioList.clear();
            criterioCliente.setAtributo("cliente"); // buscar los Tramites relacionados al Cliente encontrado
            criterioCliente.setOperacion("=");
            criterioCliente.setValor(clienteEncontrado);

            criterioList.add(criterioCliente);

        }

        // Filtro para TipoTramite
        // buscar("TipoTramite", "codTipoTramite = " + codTipoTramite): List<Object>
        if (codTipoTramite > 0) {
            DTOCriterio criterioTT = new DTOCriterio();

            criterioTT.setAtributo("codTipoTramite");
            criterioTT.setOperacion("=");
            criterioTT.setValor(codTipoTramite);
            criterioList.add(criterioTT);

            TipoTramite tipoTramiteEncontrado = null;

            List ltipoTramites = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
            if (!ltipoTramites.isEmpty()) {
                tipoTramiteEncontrado = (TipoTramite) ltipoTramites.get(0);
            }

//            criterioList.clear();
            criterioTT.setAtributo("tipoTramite"); // buscar los Tramites relacionados al TipoTramite encontrado
            criterioTT.setOperacion("=");
            criterioTT.setValor(tipoTramiteEncontrado);

            criterioList.add(criterioTT);
        }

        // Filtro para EstadoTramite
        // buscar("EstadoTramite", "nombreEstadoTramite = " + nombreEstadoTramite): List<Object>
        if (nombreEstadoTramite.trim().length() > 0) {
            DTOCriterio dtoEstado = new DTOCriterio();

            dtoEstado.setAtributo("nombreEstadoTramite");
            dtoEstado.setOperacion("=");
            dtoEstado.setValor(nombreEstadoTramite);
            criterioList.add(dtoEstado);

            EstadoTramite estadoEncontrado = null;

            List estadoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);
            if (!estadoList.isEmpty()) {
                estadoEncontrado = (EstadoTramite) estadoList.get(0);
            }

//            criterioList.clear();
            dtoEstado.setAtributo("estadoTramite");
            dtoEstado.setOperacion("=");
            dtoEstado.setValor(estadoEncontrado);
            criterioList.add(dtoEstado);
        }

        /* buscar("Tramite", "nroTramite = " + nroTramite + " OR fechaRecepcionTramite = " + fechaRecepcionTramite
        + "OR Cliente = " + cliente.toString() + "OR TipoTramite = " + tipoTramite.toString() 
        + "OR EstadoTramite = " + estadoTramite.toString()*/
        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList); // busca Tramites, segun los criterios indicados
        List<DTOTramite> tramiteResultados = new ArrayList<>();

        // Loop por cada Tramite para setear los atributos requeridos en DTOTramite
        for (Object t : objetoList) {
            Tramite tramite = (Tramite) t;
            DTOTramite dtoTramite = new DTOTramite();// :create() DTOTramite
            dtoTramite.setNroTramite(tramite.getNroTramite());
            dtoTramite.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite());
            dtoTramite.setDni(tramite.getCliente().getDniCliente());
            dtoTramite.setCodTipoTramite(tramite.getTipoTramite().getCodTipoTramite());
            dtoTramite.setNombreEstado(tramite.getEstadoTramite().getNombreEstadoTramite());
            dtoTramite.setFechaAnulacion(tramite.getFechaAnulacionTramite());

            tramiteResultados.add(dtoTramite); // Cargo los datos seteados en dtoTramite a la lista
        }

        return tramiteResultados; // Retorna la lista
    }

    // obtenerCliente(dniCliente): DTOCliente
    public DTOCliente obtenerCliente(int dni) throws RegistrarTramiteException {

        List<DTOCriterio> criterioList = new ArrayList<>();

        // buscar("Cliente", "dniCliente = " + dniCliente + " AND fechaHoraBaja = " + null): List<Object>
        if (dni > 0) {
            DTOCriterio dto1 = new DTOCriterio();
            dto1.setAtributo("dniCliente");
            dto1.setOperacion("=");
            dto1.setValor(dni);

            criterioList.add(dto1);
        }

        // :create() DTOCliente
        DTOCliente dtoCliente = new DTOCliente();

        // seteamos en DTOCliente los atributos del Cliente encontrado
        try {
            clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);

            dtoCliente.setNombreCliente(clienteEncontrado.getNombreCliente());
            dtoCliente.setApellidoCliente(clienteEncontrado.getApellidoCliente());
            dtoCliente.setMailCliente(clienteEncontrado.getMailCliente());

        } catch (Exception e) {
            throw new RegistrarTramiteException("Error al obtener el Cliente");
        }

        return dtoCliente; // Retornamos el DTOCliente
    }

    // obtenerTipoTramite(codTipoTramite): DTOTipoTramite
    public DTOTipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        // buscar("TipoTramite", "codTipoTramite = " + codTipoTramite + " AND fechaHoraBaja = " + null): List<Object>
        if (codTipoTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();
            dto1.setAtributo("codTipoTramite");
            dto1.setOperacion("=");
            dto1.setValor(codTipoTramite);

            criterioList.add(dto1);
        }

        // :create() DTOTipoTramite
        DTOTipoTramite dtoTipoTramite = new DTOTipoTramite();

        // seteamos en DTOTipoTramite los datos del TipoTramite encontrado
        try {
            tipoTramiteEncontrado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);

            dtoTipoTramite.setCodTipoTramite(tipoTramiteEncontrado.getCodTipoTramite());
            dtoTipoTramite.setNombreTipoTramite(tipoTramiteEncontrado.getNombreTipoTramite());

        } catch (Exception e) {
            throw new RegistrarTramiteException("Error al obtener el TipoTramite");
        }

        return dtoTipoTramite; // Retornamos el DTOTipoTramite
    }

    // registrarTramite()
    public void registrarTramite() throws RegistrarTramiteException {

        FachadaPersistencia.getInstance().iniciarTransaccion();

        Tramite tramiteCreado = new Tramite(); // :create() Tramite

        // setNroTramite automaticamente en MYSQL
        tramiteCreado.setFechaRecepcionTramite(new Timestamp(System.currentTimeMillis()));
        tramiteCreado.setFechaInicioTramite(null);
        tramiteCreado.setFechaFinTramite(null);
        tramiteCreado.setTipoTramite(tipoTramiteEncontrado);
        tramiteCreado.setCliente(clienteEncontrado);

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>(); // creamos la lista de criterios

        /* buscar("ListaPrecio", "fechaHoraBaja = " + null +
        "AND fechaHoraDesde < " + fechaActual + "AND fechaHoraHasta >" + fechaActual*/
        DTOCriterio dto1 = new DTOCriterio();
        dto1.setAtributo("fechaHoraDesdeListaPrecios");
        dto1.setOperacion("<");
        dto1.setValor(new Timestamp(System.currentTimeMillis()));
        criterioList.add(dto1);

        DTOCriterio dto2 = new DTOCriterio();
        dto2.setAtributo("fechaHoraHastaListaPrecios");
        dto2.setOperacion(">");
        dto2.setValor(new Timestamp(System.currentTimeMillis()));
        criterioList.add(dto2);

        DTOCriterio dto3 = new DTOCriterio();
        dto3.setAtributo("fechaHoraBajaListaPrecios");
        dto3.setOperacion("=");
        dto3.setValor(null);
        criterioList.add(dto3);

        ListaPrecios listaPreciosEncontrada = (ListaPrecios) FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList).get(0);

        // getTipoTramiteListaPrecios(): List<TipoTramiteListaPrecios>
        List<TipoTramiteListaPrecios> precioTTList = listaPreciosEncontrada.getTipoTramiteListaPrecios();
        // loop por cada TipoTramiteListaPrecios
        for (TipoTramiteListaPrecios tTP : precioTTList) {
            if (tTP.getTipoTramite().getCodTipoTramite() == tipoTramiteEncontrado.getCodTipoTramite()) { // getCodTipoTramite() igual al que se muestra
                tramiteCreado.setPrecioTramite(tTP.getPrecioTipoTramite()); // setPrecioTramite(precioTipoTramite)
            }
        }

        criterioList.clear();

        /* buscar("EstadoTramite", "nombreEstadoTramite = " + 'INICIADO' + "AND
        fechaHoraBajaET = " + null): List<Object> */
        DTOCriterio criterioEstado = new DTOCriterio();
        criterioEstado.setAtributo("nombreEstadoTramite");
        criterioEstado.setOperacion("like");
        criterioEstado.setValor("Nombre1");

        criterioList.add(criterioEstado);

        EstadoTramite estadoEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);
        tramiteCreado.setEstadoTramite(estadoEncontrado); // setEstadoTramite(estadoEncontrado)

        TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite(); // :create() TramiteEstadoTramite
        tramiteEstadoTramite.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
        tramiteEstadoTramite.setFechaHoraBajaTET(null);
        tramiteEstadoTramite.setEstadoTramite(estadoEncontrado);

        tramiteCreado.addTramiteEstadoTramite(tramiteEstadoTramite);
        FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite); // guardar(tramiteEstadoTramite)

        criterioList.clear();

        /* buscar("Version", "fechaDesdeVersion <"+ fechaActual + "fechaHastaVersion >"
        + fechaActual + "AND TipoTramite ="+ tipoTramite.toString()*/
        DTOCriterio criteriov1 = new DTOCriterio();
        criteriov1.setAtributo("fechaDesdeVersion");
        criteriov1.setOperacion("<");
        criteriov1.setValor(new Timestamp(System.currentTimeMillis()));
        criterioList.add(criteriov1);

        DTOCriterio criteriov2 = new DTOCriterio();
        criteriov2.setAtributo("fechaHastaVersion");
        criteriov2.setOperacion(">");
        criteriov2.setValor(new Timestamp(System.currentTimeMillis()));
        criterioList.add(criteriov2);

        Version versionEncontrada = (Version) FachadaPersistencia.getInstance().buscar("Version", criterioList).get(0);
        tramiteCreado.setVersion(versionEncontrada);

        criterioList.clear();

        // getTipoTramiteDocumentacion(): List<TipoTramiteDocumentacion>
        List<TipoTramiteDocumentacion> docList = tipoTramiteEncontrado.getTipoTramiteDocumentacion();
        // loop por cada TipoTramiteDocumentacion
        for (TipoTramiteDocumentacion ttDoc : docList) {
            Documentacion doc = ttDoc.getDocumentacion(); // getDocumentacion(): Documentacion
            TramiteDocumentacion tramiteDocumentacion = new TramiteDocumentacion(); // :create() TramiteDocumentacion
            // agregar el codTD ??
            tramiteDocumentacion.setDocumentacion(doc);

            tramiteCreado.addTramiteDocumentacion(tramiteDocumentacion);
            FachadaPersistencia.getInstance().guardar(tramiteDocumentacion);
        }

        FachadaPersistencia.getInstance().guardar(tramiteCreado); // guardar(Tramite)
        FachadaPersistencia.getInstance().finalizarTransaccion();

    }
    
    Tramite tramiteElegido = new Tramite();
    
    // mostrarDatosTramite(): DTOTramiteElegido
    public DTOTramiteElegido mostrarResumenTramite(int nroTramite) {
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (nroTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();

            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);

            criterioList.add(dto1);
        }

        tramiteElegido = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList).get(0);
        DTOTramiteElegido resumenDTO = new DTOTramiteElegido(); // :create() DTOTramiteElegido 

        // seteamos en DTOTramiteElegido los atributos del tramite seleccionado
        resumenDTO.setNroTramite(tramiteElegido.getNroTramite());
        resumenDTO.setFechaRecepcionTramite(tramiteElegido.getFechaRecepcionTramite());
        resumenDTO.setFechaAnulacionTramite(tramiteElegido.getFechaAnulacionTramite());
        resumenDTO.setPlazoDocumentacion(tramiteElegido.getTipoTramite().getPlazoEntregaDocumentacionTT());
        resumenDTO.setCodTipoTramite(tramiteElegido.getTipoTramite().getCodTipoTramite());
        resumenDTO.setNombreTipoTramite(tramiteElegido.getTipoTramite().getNombreTipoTramite());
        resumenDTO.setNombreEstado(tramiteElegido.getEstadoTramite().getNombreEstadoTramite());
        resumenDTO.setPrecioTramite(tramiteElegido.getPrecioTramite());
        resumenDTO.setDniCliente(tramiteElegido.getCliente().getDniCliente());
        resumenDTO.setNombreCliente(tramiteElegido.getCliente().getNombreCliente());
        resumenDTO.setApellidoCliente(tramiteElegido.getCliente().getApellidoCliente());
        resumenDTO.setMailCliente(tramiteElegido.getCliente().getMailCliente());

        List<DTODocumentacion> resumenDocList = new ArrayList<>(); // :create() DTODocumentacion
        // loop por cada TramiteDocumentacion para setear atributos
        for (TramiteDocumentacion doc : tramiteElegido.getTramiteDocumentacion()) {
            DTODocumentacion resumenDoc = new DTODocumentacion();
            resumenDoc.setCodTD(doc.getCodTD());
            resumenDoc.setNombreDocumentacion(doc.getDocumentacion().getNombreDocumentacion());
            resumenDoc.setFechaEntregaDoc(doc.getFechaEntregaTD());
            resumenDocList.add(resumenDoc);
        }

        resumenDTO.setResumenDoc(resumenDocList);

        return resumenDTO;
    }

    // anularTramite()
    public void anularTramite(int nroTramite) throws RegistrarTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (nroTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();

            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);

            criterioList.add(dto1);
        }

        Tramite tramiteEncontrado = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList).get(0);
        tramiteEncontrado.setFechaAnulacionTramite(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(tramiteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    // Se listan todos los tipos tramites al hacer click en el boton de ayuda
    // mostrarTiposTramites(nombreTipoTramite, descripcionTipoTramite, nombreCategoriaTipoTramite): List<DTOTipoTramite>
    public List<DTOTipoTramite> buscarTipoTramite(int codTipoTramite, String nombreTipoTramite, String nombreCategoria, String descTipoTramite) {
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (codTipoTramite > 0) {
            DTOCriterio criterio = new DTOCriterio();
            criterio.setAtributo("codTipoTramite");
            criterio.setOperacion("=");
            criterio.setValor(codTipoTramite);
            criterioList.add(criterio);
        }
        if (nombreTipoTramite.trim().length() > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("nombreTipoTramite");
            criterio1.setOperacion("like");
            criterio1.setValor(nombreTipoTramite);
            criterioList.add(criterio1);
        }

        if (descTipoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("descripcionTipoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(descTipoTramite);
            criterioList.add(criterio2);
        }

        if (nombreCategoria.trim().length() > 0) {
            DTOCriterio criterio3 = new DTOCriterio();
            criterio3.setAtributo("nombreCategoriaTipoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(nombreCategoria);
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
        List<DTOTipoTramite> tipoTramiteResultados = new ArrayList<>();

        // loop por cada TipoTramite para setear los atributos requeridos en DTOTipoTramite
        for (Object x : objecList) {
            TipoTramite tipoTramite = (TipoTramite) x;
            DTOTipoTramite dtoTipoTramite = new DTOTipoTramite();// :create() DTOTipoTramite
            dtoTipoTramite.setCodTipoTramite(tipoTramite.getCodTipoTramite());
            dtoTipoTramite.setNombreTipoTramite(tipoTramite.getNombreTipoTramite());
            dtoTipoTramite.setDescripcionTipoTramite(tipoTramite.getDescripcionTipoTramite());
            dtoTipoTramite.setNombreCategoriaTipoTramite(tipoTramite.getCategoriaTipoTramite().getNombreCategoriaTipoTramite());
            tipoTramiteResultados.add(dtoTipoTramite);
        }

        return tipoTramiteResultados;
    }
    
    public void registrarDocumentacion(int codTD, DTOFile archivoTD){
        
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        //se buscan todas las documentaciones relacionada al tramite
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        
        DTOCriterio criterioTD = new DTOCriterio();
        criterioTD.setAtributo("codTD");
        criterioTD.setOperacion("=");
        criterioTD.setValor(codTD);
        
        criterioList.add(criterioTD);
        
        TramiteDocumentacion td = (TramiteDocumentacion) FachadaPersistencia.getInstance().buscar("TramiteDocumentacion", criterioList).get(0);
        
        td.setArchivoTD(archivoTD.getContenidoB64()); //se le setea el archivo en base 64
        td.setFechaEntregaTD(new Timestamp(System.currentTimeMillis())); //se cambia la fecha de entrega a la fecha actual
        
        FachadaPersistencia.getInstance().guardar(td);
             
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
    
    
        public void asignarConsultor(){
        
        FachadaPersistencia.getInstance().iniciarTransaccion();
        
        //verifico que todas las documentaciones tenga fechaEntrega != null
        List<TramiteDocumentacion> tdList = tramiteElegido.getTramiteDocumentacion();
        
        boolean todasPresentadas = false;
        for(TramiteDocumentacion td: tdList){
            if(td.getFechaEntregaTD() != null){
                todasPresentadas = true;
                break;
            }
        }
        
        if(todasPresentadas == true){
            tramiteElegido.setFechaPresentacionTotalDocumentacion(new Timestamp(System.currentTimeMillis()));
            
            List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

            DTOCriterio agendaCriterio1 = new DTOCriterio();
            agendaCriterio1.setAtributo("fechaAgenda");
            agendaCriterio1.setOperacion("<");
            agendaCriterio1.setValor(new Timestamp(System.currentTimeMillis()));
            criterioList.add(agendaCriterio1);

            DTOCriterio agendaCriterio2 = new DTOCriterio();
            agendaCriterio2.setAtributo("fechaBajaAgenda");
            agendaCriterio2.setOperacion("=");
            agendaCriterio2.setValor(null);
            criterioList.add(agendaCriterio2);


            AgendaConsultor agenda = (AgendaConsultor) FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterioList).get(0);

            List<Consultor> consultorList = agenda.getConsultores();

            Consultor consultorSeleccionado = null;  // Consultor con menor cantidad de trámites
            int menorCantidadTramites = Integer.MAX_VALUE;  // Para inicializar la búsqueda

            for (Consultor consultor : consultorList) {
                int nroMaximoTramites = consultor.getNroMaximoTramites();  // Obtener el máximo de trámites que puede tener
                int tramitesAsignados = 0;

                // Construir los criterios de búsqueda
                criterioList.clear();
                DTOCriterio consuCriterio = new DTOCriterio();
                consuCriterio.setAtributo("consultor");
                consuCriterio.setOperacion("=");
                consuCriterio.setValor(consultor);
                criterioList.add(consuCriterio);

                // Buscar trámites asociados a este consultor
                List<Object> objectList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
                for (Object o : objectList) {
                    tramitesAsignados += 1;  // Contar los trámites asignados
                }

                // Verificar que el número de trámites asignados no supere el máximo permitido
                if (tramitesAsignados < nroMaximoTramites) {
                    // Si este consultor tiene menos trámites asignados que el seleccionado hasta ahora
                    if (tramitesAsignados < menorCantidadTramites) {
                        menorCantidadTramites = tramitesAsignados;
                        consultorSeleccionado = consultor;  
                    }
                }
            }

            tramiteElegido.setConsultor(consultorSeleccionado);
            tramiteElegido.setFechaInicioTramite(new Timestamp(System.currentTimeMillis()));

            FachadaPersistencia.getInstance().guardar(tramiteElegido);

            FachadaPersistencia.getInstance().finalizarTransaccion();            
        }
    }
}
