package RegistrarTramite;

import RegistrarTramite.beans.UIResumen;
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
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;
import utils.fechaHoraActual;

public class ExpertoRegistrarTramite {

    private Cliente clienteEncontrado;
    private TipoTramite tipoTramiteEncontrado;
    private Tramite tramiteElegido;

    // mostrarComboEstados(): List<DTOEstadoTramite>
    public List<DTOEstadoTramite> mostrarComboEstados() {

        FachadaPersistencia.getInstance().iniciarTransaccion();

        List objetoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", null);

        List<DTOEstadoTramite> estadosTramite = new ArrayList<>();
        for (Object x : objetoList) {
            EstadoTramite estadoTramite = (EstadoTramite) x;
            DTOEstadoTramite dtoEstadoTramite = new DTOEstadoTramite();

            dtoEstadoTramite.setNombreEstado(estadoTramite.getNombreEstadoTramite());
            estadosTramite.add(dtoEstadoTramite);
        }
        FachadaPersistencia.getInstance().finalizarTransaccion();
        return estadosTramite;
    }

    // mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, nombreEstadoTramite): List<DTOTramite>
    public List<DTOTramite> mostrarTramites(int nroTramite, Date fechaRecepcionTramite, int dniCliente, int codTipoTramite, String nombreEstadoTramite) {

        FachadaPersistencia.getInstance().iniciarTransaccion();
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
            List<DTOCriterio> criterioListC = new ArrayList<DTOCriterio>();
            DTOCriterio criterioCliente = new DTOCriterio();

            criterioCliente.setAtributo("dniCliente");
            criterioCliente.setOperacion("=");
            criterioCliente.setValor(dniCliente);
            criterioListC.add(criterioCliente);

            Cliente clienteEncontrado = null;

            List lClientes = FachadaPersistencia.getInstance().buscar("Cliente", criterioListC);
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
            List<DTOCriterio> criterioListTT = new ArrayList<DTOCriterio>();
            DTOCriterio criterioTT = new DTOCriterio();

            criterioTT.setAtributo("codTipoTramite");
            criterioTT.setOperacion("=");
            criterioTT.setValor(codTipoTramite);
            criterioListTT.add(criterioTT);

            TipoTramite tipoTramiteEncontrado = null;

            List ltipoTramites = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioListTT);
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
            List<DTOCriterio> criterioListET = new ArrayList<DTOCriterio>();
            DTOCriterio dtoEstado = new DTOCriterio();

            dtoEstado.setAtributo("nombreEstadoTramite");
            dtoEstado.setOperacion("=");
            dtoEstado.setValor(nombreEstadoTramite);
            criterioListET.add(dtoEstado);

            EstadoTramite estadoEncontrado = null;

            List estadoList = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioListET);
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
        criterioList.clear();
        List<DTOTramite> tramiteResultados = new ArrayList<>();

        // Loop por cada Tramite para setear los atributos requeridos en DTOTramite
        for (Object t : objetoList) {
            Tramite tramite = (Tramite) t;
            DTOTramite dtoTramite = new DTOTramite();// :create() DTOTramite
            dtoTramite.setNroTramite(tramite.getNroTramite());
            dtoTramite.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite());
            dtoTramite.setFechaAnulacion(tramite.getFechaAnulacionTramite());
            dtoTramite.setFechaFinTramite(tramite.getFechaFinTramite());
            dtoTramite.setDni(tramite.getCliente().getDniCliente());
            dtoTramite.setCodTipoTramite(tramite.getTipoTramite().getCodTipoTramite());
            dtoTramite.setNombreEstado(tramite.getEstadoTramite().getNombreEstadoTramite());
            dtoTramite.setNombreApellidoCliente(tramite.getCliente().getNombreCliente() + " " + tramite.getCliente().getApellidoCliente());

            //
            dtoTramite.setFechaInicioTramite(tramite.getFechaInicioTramite());

            tramiteResultados.add(dtoTramite);// Cargo los datos seteados en dtoTramite a la lista
        }

        FachadaPersistencia.getInstance().finalizarTransaccion();
        return tramiteResultados; // Retorna la lista
    }

    // obtenerCliente(dniCliente): DTOCliente
// obtenerCliente(dniCliente): DTOCliente
    public DTOCliente obtenerCliente(int dni) throws RegistrarTramiteException {

        List<DTOCriterio> criterioList = new ArrayList<>();

        // Verificar si el DNI es válido
        if (dni > 0) {
            DTOCriterio dto1 = new DTOCriterio();
            dto1.setAtributo("dniCliente");
            dto1.setOperacion("=");
            dto1.setValor(dni);
            criterioList.add(dto1);

            DTOCriterio dto2 = new DTOCriterio();
            dto2.setAtributo("fechaHoraBajaCliente");
            dto2.setOperacion("=");
            dto2.setValor(null);
            criterioList.add(dto2);
        } else {
            throw new RegistrarTramiteException("DNI invalido");
        }

        // Buscar clientes con los criterios proporcionados
        List<Object> clientesEncontrados = FachadaPersistencia.getInstance().buscar("Cliente", criterioList);

        // Crear el objeto DTOCliente
        DTOCliente dtoCliente = new DTOCliente();

        // Verificar si se encontraron clientes
        if (!clientesEncontrados.isEmpty()) {
            clienteEncontrado = (Cliente) clientesEncontrados.get(0);
            dtoCliente.setNombreCliente(clienteEncontrado.getNombreCliente());
            dtoCliente.setApellidoCliente(clienteEncontrado.getApellidoCliente());
            dtoCliente.setMailCliente(clienteEncontrado.getMailCliente());
        } else {
            clienteEncontrado = null;
            dtoCliente = null;
        }

        if (clientesEncontrados.isEmpty()) {
            throw new RegistrarTramiteException("No se encontró el Cliente");
        }

        criterioList.clear();

        return dtoCliente; // Retornar DTOCliente (puede ser null si no se encontró)
    }

    // obtenerTipoTramite(codTipoTramite): DTOTipoTramite
    public DTOTipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {

        List<DTOCriterio> criterioListTT = new ArrayList<>();

        // Verificar si el código de tipo de trámite es válido
        if (codTipoTramite > 0) {
            DTOCriterio dto1tt = new DTOCriterio();
            dto1tt.setAtributo("codTipoTramite");
            dto1tt.setOperacion("=");
            dto1tt.setValor(codTipoTramite);
            criterioListTT.add(dto1tt);

            DTOCriterio dto2tt = new DTOCriterio();
            dto2tt.setAtributo("fechaHoraBajaTipoTramite");
            dto2tt.setOperacion("=");
            dto2tt.setValor(null);
            criterioListTT.add(dto2tt);
        } else {
            throw new RegistrarTramiteException("Código de TipoTramite inválido");
        }

        // Buscar tipos de trámite con los criterios proporcionados
        List<Object> ttEncontrados = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioListTT);

        // Crear el objeto DTOTipoTramite
        DTOTipoTramite dtoTipoTramite = new DTOTipoTramite();

        // Verificar si se encontraron tipos de trámite
        if (!ttEncontrados.isEmpty()) {
            tipoTramiteEncontrado = (TipoTramite) ttEncontrados.get(0);
            dtoTipoTramite.setNombreTipoTramite(tipoTramiteEncontrado.getNombreTipoTramite());
        } else {
            tipoTramiteEncontrado = null;
            dtoTipoTramite = null;
        }

        if (ttEncontrados.isEmpty()) {
            throw new RegistrarTramiteException("No se encontró el TipoTramite");
        }

        criterioListTT.clear();

        return dtoTipoTramite; // Retornar DTOTipoTramite (puede ser null si no se encontró)
    }

    // registrarTramite()
    public void registrarTramite() throws RegistrarTramiteException {

        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {

            Tramite tramiteCreado = new Tramite(); // :create() Tramite

            int nroTramite = generarNroTramite(); // Creo el nroTramite incremental
            tramiteCreado.setNroTramite(nroTramite);
            tramiteCreado.setFechaRecepcionTramite(fechaHoraActual.obtenerFechaHoraActual());
            tramiteCreado.setFechaInicioTramite(null);
            tramiteCreado.setFechaFinTramite(null);

            if (clienteEncontrado == null && tipoTramiteEncontrado == null) {
                throw new RegistrarTramiteException("No se pudo registrar el trámite. Cliente && TipoTramite no encontrado/s");
            }

            List<DTOCriterio> clienteTT = new ArrayList<>();
            DTOCriterio tramiteCliente = new DTOCriterio();
            tramiteCliente.setAtributo("cliente");
            tramiteCliente.setOperacion("=");
            tramiteCliente.setValor(clienteEncontrado);
            clienteTT.add(tramiteCliente);

            // Obtenemos los trámites asociados al cliente encontrado
            List tramitesCliente = FachadaPersistencia.getInstance().buscar("Tramite", clienteTT);

            for (Object tC : tramitesCliente) {
                Tramite t = (Tramite) tC;
                // Validamos si existe un trámite en curso del mismo tipo sin fecha de fin o anulación
                boolean mismoTipoTramite = t.getTipoTramite().getNombreTipoTramite().equals(tipoTramiteEncontrado.getNombreTipoTramite());
                boolean tramiteEnCurso = t.getFechaFinTramite() == null && t.getFechaAnulacionTramite() == null;

                if (mismoTipoTramite && tramiteEnCurso) {
                    throw new RegistrarTramiteException("El Cliente tiene un mismo Tipo Trámite en curso");
                }
            }

            if (tipoTramiteEncontrado == null) {
                throw new RegistrarTramiteException("No se pudo registrar el trámite. TipoTramite no encontrado");
            }
            if (clienteEncontrado == null) {
                throw new RegistrarTramiteException("No se pudo registrar el trámite. Cliente no encontrado");
            }

            tramiteCreado.setCliente(clienteEncontrado);
            tramiteCreado.setTipoTramite(tipoTramiteEncontrado);

            List<DTOCriterio> criterioList = new ArrayList<>(); // Creamos la lista de criterios

            // Criterio para fechaHoraDesdeListaPrecios < fechaActual
            DTOCriterio fechaDesdeLP = new DTOCriterio();
            fechaDesdeLP.setAtributo("fechaHoraDesdeListaPrecios");
            fechaDesdeLP.setOperacion("<");
            fechaDesdeLP.setValor(fechaHoraActual.obtenerFechaHoraActual());
            criterioList.add(fechaDesdeLP);

            // Criterio para fechaHoraHastaListaPrecios > fechaActual
            DTOCriterio fechaAltaLP = new DTOCriterio();
            fechaAltaLP.setAtributo("fechaHoraHastaListaPrecios");
            fechaAltaLP.setOperacion(">");
            fechaAltaLP.setValor(fechaHoraActual.obtenerFechaHoraActual());
            criterioList.add(fechaAltaLP);

            // Criterio para fechaHoraBajaListaPrecios == null
            DTOCriterio fechaBajaLP = new DTOCriterio();
            fechaBajaLP.setAtributo("fechaHoraBajaListaPrecios");
            fechaBajaLP.setOperacion("=");
            fechaBajaLP.setValor(null);
            criterioList.add(fechaBajaLP);

            List<Object> listaPreciosEncontradas = FachadaPersistencia.getInstance().buscar("ListaPrecios", criterioList);

            // Verificación de existencia de lista de precios activa
            if (listaPreciosEncontradas == null || listaPreciosEncontradas.isEmpty()) {
                throw new RegistrarTramiteException("No se encontró una lista de precios activa.");
            }

            ListaPrecios listaPreciosEncontrada = (ListaPrecios) listaPreciosEncontradas.get(0);

            // Obtenemos la lista de precios por tipo de trámite
            List<TipoTramiteListaPrecios> precioTTList = listaPreciosEncontrada.getTipoTramiteListaPrecios();

            // Verificación de precios para el tipo de trámite seleccionado
            boolean tipoTramiteConPrecio = false;
            for (TipoTramiteListaPrecios tTP : precioTTList) {
                if (tTP.getTipoTramite().getCodTipoTramite() == tipoTramiteEncontrado.getCodTipoTramite()) {
                    tramiteCreado.setPrecioTramite(tTP.getPrecioTipoTramite());
                    tipoTramiteConPrecio = true;
                    break;
                }
            }

            if (!tipoTramiteConPrecio) {
                throw new RegistrarTramiteException("El tipo de trámite seleccionado no tiene una lista de precios activa.");
            }

            criterioList.clear();


            /* buscar("EstadoTramite", "nombreEstadoTramite = " + 'INICIADO' + "AND
        fechaHoraBajaET = " + null): List<Object> */
            DTOCriterio criterioEstado = new DTOCriterio();
            criterioEstado.setAtributo("nombreEstadoTramite");
            criterioEstado.setOperacion("=");
            criterioEstado.setValor("Iniciado");

            criterioList.add(criterioEstado);

            List<Object> estadosEncontrados = FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList);

            if (estadosEncontrados.isEmpty()) {
                throw new RegistrarTramiteException("No se encontro un estado con nombre 'Iniciado' ");
            }

            EstadoTramite estadoEncontrado = (EstadoTramite) estadosEncontrados.get(0);

            tramiteCreado.setEstadoTramite(estadoEncontrado); // setEstadoTramite(estadoEncontrado)

            TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite(); // :create() TramiteEstadoTramite
            tramiteEstadoTramite.setFechaDesdeTET(fechaHoraActual.obtenerFechaHoraActual());
            tramiteEstadoTramite.setFechaHastaTET(null);
            tramiteEstadoTramite.setEstadoTramite(estadoEncontrado);

            tramiteCreado.addTramiteEstadoTramite(tramiteEstadoTramite);
            FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite); // guardar(tramiteEstadoTramite)

            criterioList.clear();

            /* buscar("Version", "fechaDesdeVersion <"+ fechaActual + "fechaHastaVersion >"
        + fechaActual + "AND TipoTramite ="+ tipoTramite.toString()*/
            DTOCriterio criteriov1 = new DTOCriterio();
            criteriov1.setAtributo("fechaDesdeVersion");
            criteriov1.setOperacion("<");
            criteriov1.setValor(fechaHoraActual.obtenerFechaHoraActual());
            criterioList.add(criteriov1);

            DTOCriterio criteriov2 = new DTOCriterio();
            criteriov2.setAtributo("fechaHastaVersion");
            criteriov2.setOperacion(">");
            criteriov2.setValor(fechaHoraActual.obtenerFechaHoraActual());
            criterioList.add(criteriov2);

            DTOCriterio criteriov3 = new DTOCriterio();
            criteriov3.setAtributo("fechaBajaVersion");
            criteriov3.setOperacion("=");
            criteriov3.setValor(null);
            criterioList.add(criteriov3);

            DTOCriterio criteriov4 = new DTOCriterio();
            criteriov4.setAtributo("tipoTramite");
            criteriov4.setOperacion("=");
            criteriov4.setValor(tipoTramiteEncontrado);
            criterioList.add(criteriov4);

            List<Object> versionesEncontradas = FachadaPersistencia.getInstance().buscar("Version", criterioList);

            if (versionesEncontradas == null || versionesEncontradas.isEmpty()) {
                throw new RegistrarTramiteException("El tipo de trámite seleccionado no tiene una version activa.");
            }

            Version versionEncontrada = (Version) versionesEncontradas.get(0);

            tramiteCreado.setVersion(versionEncontrada);

            criterioList.clear();

            // getTipoTramiteDocumentacion(): List<TipoTramiteDocumentacion>
            List<TipoTramiteDocumentacion> docList = tipoTramiteEncontrado.getTipoTramiteDocumentacion();
            // loop por cada TipoTramiteDocumentacion
            for (TipoTramiteDocumentacion ttDoc : docList) {
                if (ttDoc.getFechaHoraBajaTTD() == null) {
                    Documentacion doc = ttDoc.getDocumentacion();
                    TramiteDocumentacion tramiteDocumentacion = new TramiteDocumentacion(); // :create() TramiteDocumentacion
                    int cotTD = generarCodTD(); // codTD incremental
                    tramiteDocumentacion.setCodTD(cotTD);
                    //tramiteDocumentacion.setNombreTD(nombreTD);
                    tramiteDocumentacion.setDocumentacion(doc);
                    tramiteCreado.addTramiteDocumentacion(tramiteDocumentacion);
                    FachadaPersistencia.getInstance().guardar(tramiteDocumentacion);
                }// getDocumentacion(): Documentacion

            }

            FachadaPersistencia.getInstance().guardar(tramiteCreado); // guardar(Tramite)
            FachadaPersistencia.getInstance().finalizarTransaccion();
        } catch (RegistrarTramiteException e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw e;
        }
    }

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
        resumenDTO.setFechaPresentacionTotalDocumentacion(tramiteElegido.getFechaPresentacionTotalDocumentacion());
        resumenDTO.setFechaInicioTramite(tramiteElegido.getFechaInicioTramite());
        resumenDTO.setFechaFinTramite(tramiteElegido.getFechaFinTramite());
        resumenDTO.setPlazoDocumentacion(tramiteElegido.getTipoTramite().getPlazoEntregaDocumentacionTT());
        resumenDTO.setCodTipoTramite(tramiteElegido.getTipoTramite().getCodTipoTramite());
        resumenDTO.setNombreTipoTramite(tramiteElegido.getTipoTramite().getNombreTipoTramite());
        resumenDTO.setNombreEstado(tramiteElegido.getEstadoTramite().getNombreEstadoTramite());
        resumenDTO.setPrecioTramite(tramiteElegido.getPrecioTramite());
        resumenDTO.setDniCliente(tramiteElegido.getCliente().getDniCliente());
        resumenDTO.setNombreCliente(tramiteElegido.getCliente().getNombreCliente());
        resumenDTO.setApellidoCliente(tramiteElegido.getCliente().getApellidoCliente());
        resumenDTO.setMailCliente(tramiteElegido.getCliente().getMailCliente());

        if (tramiteElegido.getConsultor() != null) {
            resumenDTO.setNombreConsultor(tramiteElegido.getConsultor().getNombreConsultor());
            resumenDTO.setLegajoConsultor(tramiteElegido.getConsultor().getLegajoConsultor());
        } else {
            resumenDTO.setNombreConsultor("");
            resumenDTO.setLegajoConsultor(0);
        }
        List<DTODocumentacion> resumenDocList = new ArrayList<>(); // :create() DTODocumentacion
        // loop por cada TramiteDocumentacion para setear atributos
        for (TramiteDocumentacion doc : tramiteElegido.getTramiteDocumentacion()) {
            DTODocumentacion resumenDoc = new DTODocumentacion();
            resumenDoc.setCodTD(doc.getCodTD());
            resumenDoc.setNombreTD(doc.getNombreTD());
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

        tramiteElegido.setFechaAnulacionTramite(fechaHoraActual.obtenerFechaHoraActual());

        FachadaPersistencia.getInstance().merge(tramiteElegido);
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
            List<DTOCriterio> criterioList2 = new ArrayList<DTOCriterio>();

            DTOCriterio criterio3 = new DTOCriterio();

            criterio3.setAtributo("nombreCategoriaTipoTramite");
            criterio3.setOperacion("like");
            criterio3.setValor(nombreCategoria);
            criterioList2.add(criterio3);

            List lCategoria = FachadaPersistencia.getInstance().buscar("CategoriaTipoTramite", criterioList2);

            CategoriaTipoTramite categoriaEncontrada = null;

            if (lCategoria.size() > 0) {
                categoriaEncontrada = (CategoriaTipoTramite) lCategoria.get(0);
            }

            DTOCriterio criterio4 = new DTOCriterio();

            criterio4.setAtributo("categoriaTipoTramite");
            criterio4.setOperacion("=");
            criterio4.setValor(categoriaEncontrada);

            criterioList.add(criterio4);
        }

        List objecList = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);
        criterioList.clear();
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

    public void registrarDocumentacion(int codTD, DTOFile archivoTD, int nroTramite) throws RegistrarTramiteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Verificar si el trámite ya ha sido elegido
        if (tramiteElegido == null) {
            List<DTOCriterio> criterioList = new ArrayList<>();
            DTOCriterio criterio = new DTOCriterio();
            criterio.setAtributo("nroTramite");
            criterio.setOperacion("=");
            criterio.setValor(nroTramite);
            criterioList.add(criterio);

            tramiteElegido = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList).get(0);
        }

        List<DTOCriterio> criterioListTD = new ArrayList<>();
        DTOCriterio criterioTD = new DTOCriterio();
        criterioTD.setAtributo("codTD");
        criterioTD.setOperacion("=");
        criterioTD.setValor(codTD);
        criterioListTD.add(criterioTD);

        System.out.println("codTD " + codTD);

        TramiteDocumentacion td = (TramiteDocumentacion) FachadaPersistencia.getInstance().buscar("TramiteDocumentacion", criterioListTD).get(0);
        FachadaPersistencia.getInstance().merge(td);

        List<TramiteDocumentacion> tdList = tramiteElegido.getTramiteDocumentacion();

        for (TramiteDocumentacion tds : tdList) {
            if (tds.getCodTD() == codTD) {
                tds.setArchivoTD(archivoTD.getContenidoB64());
                tds.setNombreTD(archivoTD.getNombre());
                tds.setFechaEntregaTD(fechaHoraActual.obtenerFechaHoraActual());
                FachadaPersistencia.getInstance().merge(tds);
            }
        }

        boolean todasPresentadas = true;
        for (TramiteDocumentacion tds : tdList) {
            if (tds.getFechaEntregaTD() == null) {
                todasPresentadas = false;
                break;
            }
        }

        FachadaPersistencia.getInstance().refrescar(tramiteElegido);

        try {
            if (todasPresentadas) {
                tramiteElegido.setFechaPresentacionTotalDocumentacion(fechaHoraActual.obtenerFechaHoraActual());

                List<DTOCriterio> criterioListAgenda = new ArrayList<>();
                DTOCriterio agendaCriterio1 = new DTOCriterio();
                agendaCriterio1.setAtributo("fechaHastaSemana");
                agendaCriterio1.setOperacion(">");
                agendaCriterio1.setValor(fechaHoraActual.obtenerFechaHoraActual());
                criterioListAgenda.add(agendaCriterio1);

                List<Object> agendas = FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterioListAgenda);
                AgendaConsultor agendaSeleccionada = null;

                // Buscar una agenda que tenga al menos un consultor disponible
                for (Object obj : agendas) {
                    AgendaConsultor agenda = (AgendaConsultor) obj;
                    List<Consultor> consultores = agenda.getConsultores();

                    if (consultores != null && !consultores.isEmpty()) {
                        agendaSeleccionada = agenda;
                        break;
                    }
                }

                if (agendaSeleccionada == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay agendas disponibles con consultores para asignar."));
                    return;
                }

                List<Consultor> consultorList = agendaSeleccionada.getConsultores();
                Consultor consultorSeleccionado = null;
                int menorCantidadTramites = Integer.MAX_VALUE;

                for (Consultor consultor : consultorList) {
                    if (consultor.getFechaHoraBajaConsultor() == null) {
                        criterioListAgenda.clear();
                        DTOCriterio consuCriterio = new DTOCriterio();
                        consuCriterio.setAtributo("consultor");
                        consuCriterio.setOperacion("=");
                        consuCriterio.setValor(consultor);
                        criterioListAgenda.add(consuCriterio);

                        List<Object> objectList = FachadaPersistencia.getInstance().buscar("Tramite", criterioListAgenda);
                        int tramitesAsignados = objectList.size();

                        if (tramitesAsignados < consultor.getNumMaximoTramites() && tramitesAsignados < menorCantidadTramites) {
                            menorCantidadTramites = tramitesAsignados;
                            consultorSeleccionado = consultor;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Consultor asignado con éxito."));
                        }
                    }
                }

                if (consultorSeleccionado == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Todos los consultores están ocupados."));
                    return;
                }

                tramiteElegido.setConsultor(consultorSeleccionado);
                tramiteElegido.setFechaInicioTramite(fechaHoraActual.obtenerFechaHoraActual());
                FachadaPersistencia.getInstance().merge(tramiteElegido);
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        } finally {
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public void asignarConsultorFuturo() throws RegistrarTramiteException {

        List<DTOCriterio> criterioListTramite = new ArrayList<>();
        DTOCriterio criterioTramite = new DTOCriterio();
        criterioTramite.setAtributo("nroTramite");
        criterioTramite.setOperacion("=");
        criterioTramite.setValor(tramiteElegido.getNroTramite());
        criterioListTramite.add(criterioTramite);

        Tramite tramite = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioListTramite).get(0);

        if (tramite.getFechaPresentacionTotalDocumentacion() != null && tramite.getFechaInicioTramite() == null) {

            FachadaPersistencia.getInstance().iniciarTransaccion();

            try {

                tramiteElegido.setFechaPresentacionTotalDocumentacion(fechaHoraActual.obtenerFechaHoraActual());

                List<DTOCriterio> criterioListAgenda = new ArrayList<>();
                DTOCriterio agendaCriterio1 = new DTOCriterio();
                agendaCriterio1.setAtributo("fechaHastaSemana");
                agendaCriterio1.setOperacion(">");
                agendaCriterio1.setValor(fechaHoraActual.obtenerFechaHoraActual());
                criterioListAgenda.add(agendaCriterio1);

                List<Object> agendas = FachadaPersistencia.getInstance().buscar("AgendaConsultor", criterioListAgenda);
                AgendaConsultor agendaSeleccionada = null;

                // Buscar una agenda que tenga al menos un consultor disponible
                for (Object obj : agendas) {
                    AgendaConsultor agenda = (AgendaConsultor) obj;
                    List<Consultor> consultores = agenda.getConsultores();

                    if (consultores != null && !consultores.isEmpty()) {
                        agendaSeleccionada = agenda;
                        break;
                    }
                }

                if (agendaSeleccionada == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "No hay agendas disponibles con consultores para asignar."));
                    return;
                }

                List<Consultor> consultorList = agendaSeleccionada.getConsultores();
                Consultor consultorSeleccionado = null;
                int menorCantidadTramites = Integer.MAX_VALUE;

                for (Consultor consultor : consultorList) {
                    if (consultor.getFechaHoraBajaConsultor() == null) {
                        criterioListAgenda.clear();
                        DTOCriterio consuCriterio = new DTOCriterio();
                        consuCriterio.setAtributo("consultor");
                        consuCriterio.setOperacion("=");
                        consuCriterio.setValor(consultor);
                        criterioListAgenda.add(consuCriterio);

                        List<Object> objectList = FachadaPersistencia.getInstance().buscar("Tramite", criterioListAgenda);
                        int tramitesAsignados = objectList.size();

                        if (tramitesAsignados < consultor.getNumMaximoTramites() && tramitesAsignados < menorCantidadTramites) {
                            menorCantidadTramites = tramitesAsignados;
                            consultorSeleccionado = consultor;
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exitoso", "Consultor asignado con éxito."));
                        }
                    }
                }

                if (consultorSeleccionado == null) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Todos los consultores están ocupados."));
                    return;
                }

                
                tramiteElegido.setConsultor(consultorSeleccionado);
                if (tramiteElegido.getFechaInicioTramite() == null){
                tramiteElegido.setFechaInicioTramite(fechaHoraActual.obtenerFechaHoraActual());
                }
                FachadaPersistencia.getInstance().merge(tramiteElegido);

            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            } finally {
                FachadaPersistencia.getInstance().finalizarTransaccion();
            }
        }
    }

    public void eliminarDocumentacion(int codTD, int nroTramite) throws Exception {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        try {
            // Verificar si el trámite ya ha sido elegido
            if (tramiteElegido == null) {
                List<DTOCriterio> criterioList = new ArrayList<>();
                DTOCriterio criterio = new DTOCriterio();
                criterio.setAtributo("nroTramite");
                criterio.setOperacion("=");
                criterio.setValor(nroTramite);
                criterioList.add(criterio);

                tramiteElegido = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList).get(0);
            }

            // Buscar la documentación existente
            List<DTOCriterio> criterioListTD = new ArrayList<>();
            DTOCriterio criterioTD = new DTOCriterio();
            criterioTD.setAtributo("codTD");
            criterioTD.setOperacion("=");
            criterioTD.setValor(codTD);
            criterioListTD.add(criterioTD);

            TramiteDocumentacion td = (TramiteDocumentacion) FachadaPersistencia.getInstance().buscar("TramiteDocumentacion", criterioListTD).get(0);

            // Eliminar los datos de la documentación
            if (td != null) {
                td.setArchivoTD(null);
                td.setNombreTD(null);
                td.setFechaEntregaTD(null);

                // Guardar los cambios en la base de datos
                FachadaPersistencia.getInstance().guardar(td);
            } else {
                throw new Exception("No se encontró el documento con el código proporcionado");
            }

            if (tramiteElegido.getFechaPresentacionTotalDocumentacion() != null) {
                tramiteElegido.setConsultor(null);
                tramiteElegido.setFechaPresentacionTotalDocumentacion(null);
            }
            FachadaPersistencia.getInstance().merge(tramiteElegido);
            // Finalizar la transacción correctamente
            FachadaPersistencia.getInstance().finalizarTransaccion();

        } catch (Exception e) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public TramiteDocumentacion buscarDocDescargar(int codigoDoc) {
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();
        DTOCriterio fileCriterio = new DTOCriterio();
        fileCriterio.setAtributo("codTD");
        fileCriterio.setOperacion("=");
        fileCriterio.setValor(codigoDoc);
        criterioList.add(fileCriterio);
        TramiteDocumentacion td = (TramiteDocumentacion) FachadaPersistencia.getInstance().buscar("TramiteDocumentacion", criterioList).get(0);

        return td;
    }

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

    // Generar codTD incremental
    public int generarCodTD() {
        int ultimoCodTD = buscarUltimoCodTD();
        return ultimoCodTD + 1;
    }

    // Buscar el ultimo codTD
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

}
