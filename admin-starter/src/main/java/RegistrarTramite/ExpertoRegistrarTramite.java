package RegistrarTramite;

import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTODocumentacion;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.DTOTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.CategoriaTipoTramite;
import entidades.Cliente;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoRegistrarTramite {

    /* mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, 
    nombreEstadoTramite): List<DTOTramite> */
    public List<DTOTramite> mostrarTramites(int nroTramite, Date fechaRecepcionTramite,
            int dniCliente, int codTipoTramite, String nombreEstadoTramite) {

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
            DTOCriterio dtoCliente = new DTOCriterio();

            dtoCliente.setAtributo("dniCliente");
            dtoCliente.setOperacion("contains");
            dtoCliente.setValor(dniCliente);
            criterioList.add(dtoCliente);

            Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);

            criterioList.clear();

            dtoCliente.setAtributo("cliente"); // buscar los Tramites relacionados al TipoTramite encontrado
            dtoCliente.setOperacion("=");
            dtoCliente.setValor(clienteEncontrado);

            criterioList.add(dtoCliente);

        }

        // Filtro para TipoTramite
        // buscar("TipoTramite", "codTipoTramite = " + codTipoTramite): List<Object>
        if (codTipoTramite > 0) {
            DTOCriterio dtoTipoTramite = new DTOCriterio();

            dtoTipoTramite.setAtributo("codTipoTramite");
            dtoTipoTramite.setOperacion("=");
            dtoTipoTramite.setValor(codTipoTramite);
            criterioList.add(dtoTipoTramite);
            TipoTramite tipoTramiteEncontrado = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);

            criterioList.clear();

            dtoTipoTramite.setAtributo("tipoTramite"); // buscar los Tramites relacionados al TipoTramite encontrado
            dtoTipoTramite.setOperacion("=");
            dtoTipoTramite.setValor(tipoTramiteEncontrado);

            criterioList.add(dtoTipoTramite);
        }

        // Filtro para EstadoTramite
        // buscar("EstadoTramite", "nombreEstadoTramite = " + nombreEstadoTramite): List<Object>
        if (nombreEstadoTramite.trim().length() > 0) {
            DTOCriterio dtoEstado = new DTOCriterio();

            dtoEstado.setAtributo("nombreEstadoTramite");
            dtoEstado.setOperacion("like");
            dtoEstado.setValor(nombreEstadoTramite);
            criterioList.add(dtoEstado);
            EstadoTramite estadoEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

            criterioList.clear();

            dtoEstado.setAtributo("estadoTramite"); // buscar los Tramites relacionados al TipoTramite encontrado
            dtoEstado.setOperacion("=");
            dtoEstado.setValor(estadoEncontrado);

            criterioList.add(dtoEstado);
        }

        /* buscar("Tramite", "nroTramite = " + nroTramite + " OR fechaRecepcionTramite = " + fechaRecepcionTramite
        + "OR Cliente = " + cliente.toString() + "OR TipoTramite = " + tipoTramite.toString() 
        + "OR EstadoTramite = " + estadoTramite.toString()*/
        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList); // busca Tramites, segun los criterios indicados
        List<DTOTramite> tramiteResultados = new ArrayList<>();

        // Loop por cada Tramite
        // Creo DTOTramite
        for (Object t : objetoList) {
            Tramite tramite = (Tramite) t;
            DTOTramite dtoTramite = new DTOTramite();
            dtoTramite.setNroTramite(tramite.getNroTramite());  // setea nroTramite
            dtoTramite.setFechaRecepcionTramite(tramite.getFechaRecepcionTramite()); //setea FechaRecepcionTramite
            dtoTramite.setDni(tramite.getCliente().getDniCliente()); //setea el dniCliente
            dtoTramite.setNombreTipoTramite(tramite.getTipoTramite().getNombreTipoTramite()); // setea el nombreTipoTramite
            dtoTramite.setNombreEstado(tramite.getEstadoTramite().getNombreEstadoTramite()); // setea el nombreEstado
            dtoTramite.setFechaAnulacion(tramite.getFechaAnulacionTramite()); //setea la fechaAnulacionTramite

            tramiteResultados.add(dtoTramite); // Cargo los datos seteados en dtoTramite a la lista
        }

        return tramiteResultados; // Retorna la lista
    }

    //Mostrar resumenTramite
    public DTOTramiteElegido mostrarResumenTramite(int nroTramite) {
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (nroTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();

            dto1.setAtributo("nroTramite");
            dto1.setOperacion("=");
            dto1.setValor(nroTramite);

            criterioList.add(dto1);
        }

        Tramite tramiteEncontrado = (Tramite) FachadaPersistencia.getInstance().buscar("Tramite", criterioList);
        DTOTramiteElegido resumenDTO = new DTOTramiteElegido();
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

        List<DTODocumentacion> resumenDocList = new ArrayList<>();
        for (TramiteDocumentacion doc : tramiteEncontrado.getTramiteDocumentacion()) {
            DTODocumentacion resumenDoc = new DTODocumentacion();
            resumenDoc.setNombreDocumentacion(doc.getDocumentacion().getNombreDocumentacion());
            resumenDoc.setFechaEntregaDoc(doc.getFechaEntregaTD());
            resumenDocList.add(resumenDoc);
        }

        resumenDTO.setResumenDoc(resumenDocList);

        return resumenDTO;
    }

    //anular tramite
    public void anularTramite(int nroTramite) {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (nroTramite > 0) {
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

    private Cliente clienteEncontrado;

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

        // creamos el DTOCliente
        DTOCliente dtoCliente = new DTOCliente();

        // seteamos los datos del cliente encontrado en el dto
        try {
            clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);

            dtoCliente.setNombreCliente(clienteEncontrado.getNombreCliente());
            dtoCliente.setApellidoCliente(clienteEncontrado.getApellidoCliente());
            dtoCliente.setMailCliente(clienteEncontrado.getMailCliente());

        } catch (Exception e) {
            throw new RegistrarTramiteException("Error al obtener el cliente");
        }

        return dtoCliente; // Retornamos el DTOCliente
    }

    //Obtengo al TipoTramite para registrar el Tramite
    public TipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {

        FachadaPersistencia.getInstance().iniciarTransaccion();
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (codTipoTramite > 0) {
            DTOCriterio dto1 = new DTOCriterio();
            dto1.setAtributo("codTipoTramite");
            dto1.setOperacion("=");
            dto1.setValor(codTipoTramite);

            criterioList.add(dto1);
        }

        List<Object> tipoTramiteEncontrado = FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList);

        //Si no conoce al codTipoTrramite, se le permite hace click en un boton de ayuda para mostrarle todos los tramites disponibles
        if (tipoTramiteEncontrado.isEmpty()) {
            throw new RegistrarTramiteException("No se encontro al TipoTramite");
        }

        return (TipoTramite) tipoTramiteEncontrado.get(0);
    }

    public void registrarTramite(int dni, int codTipoTramite) throws RegistrarTramiteException {

        FachadaPersistencia.getInstance().iniciarTransaccion();

        Tramite tramiteCreado = new Tramite();
//        tramiteCreado.setNroTramite(generarNroTramite());
        tramiteCreado.setFechaRecepcionTramite(new Timestamp(System.currentTimeMillis()));

        tramiteCreado.setCliente(clienteEncontrado);
        tramiteCreado.setTipoTramite(obtenerTipoTramite(codTipoTramite));

        //busco el Estado iniciado 
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        DTOCriterio criterioEstado = new DTOCriterio();
        criterioEstado.setAtributo("nombreEstadoTramite");
        criterioEstado.setOperacion("like");
        criterioEstado.setValor("Nombre1");

        criterioList.add(criterioEstado);

        EstadoTramite estadoEncontrado = (EstadoTramite) FachadaPersistencia.getInstance().buscar("EstadoTramite", criterioList).get(0);

        TramiteEstadoTramite tramiteEstadoTramite = new TramiteEstadoTramite();
        tramiteEstadoTramite.setFechaHoraAltaTET(new Timestamp(System.currentTimeMillis()));
        tramiteEstadoTramite.setEstadoTramite(estadoEncontrado);

        tramiteCreado.addTramiteEstadoTramite(tramiteEstadoTramite);
        FachadaPersistencia.getInstance().guardar(tramiteEstadoTramite);

        tramiteCreado.setEstadoTramite(estadoEncontrado);

        criterioList.clear();

        // Busco version
        /* buscar ("Version", "fechaDesdeVersion <"+ fechaActual + "fechaHastaVersion >"
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

//        criterioList.clear();
//        TipoTramite tipoTramite = (TipoTramite) FachadaPersistencia.getInstance().buscar("TipoTramite", criterioList).get(0);
        //busco lista de precio para setearle el precio
        criterioList.clear();

        /*buscar "ListaPrecio", "fechaHoraBaja ="+ null 
       "AND fechaHoraDesde <"+ fechaActual "AND fechaHoraHasta >" + fechaActual*/
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
        List<TipoTramiteListaPrecios> precioTTList = listaPreciosEncontrada.getTipoTramiteListaPrecios();
        for (TipoTramiteListaPrecios tTP : precioTTList) {
            if (tTP.getTipoTramite().getCodTipoTramite() == codTipoTramite) {
                tramiteCreado.setPrecioTramite(tTP.getPrecioTipoTramite());
            }
        }

        //Busco la doc del tipoTramite
        List<TipoTramiteDocumentacion> docList = obtenerTipoTramite(codTipoTramite).getTipoTramiteDocumentacion();
        for (TipoTramiteDocumentacion ttDoc : docList) {
            Documentacion doc = ttDoc.getDocumentacion();
            TramiteDocumentacion tramiteDocumentacion = new TramiteDocumentacion();
            //agregar el codTD ??
            tramiteDocumentacion.setDocumentacion(doc);
            tramiteCreado.addTramiteDocumentacion(tramiteDocumentacion);

            FachadaPersistencia.getInstance().guardar(tramiteDocumentacion);
        }

        FachadaPersistencia.getInstance().guardar(tramiteCreado);

        FachadaPersistencia.getInstance().finalizarTransaccion();

    }

    //se listan todos los tipos tramites al hacer click en el boton de ayuda
    public List<TipoTramiteResumenDTO> buscarTipoTramite(int codTipoTramite, String nomTipoTramite, String nomCategoria, String descTipoTramite) {
        List<DTOCriterio> criterioList = new ArrayList<DTOCriterio>();

        if (codTipoTramite > 0) {
            DTOCriterio criterio = new DTOCriterio();
            criterio.setAtributo("codTipoTramite");
            criterio.setOperacion("=");
            criterio.setValor(codTipoTramite);
            criterioList.add(criterio);
        }
        if (nomTipoTramite.trim().length() > 0) {
            DTOCriterio criterio1 = new DTOCriterio();
            criterio1.setAtributo("nombreTipoTramite");
            criterio1.setOperacion("like");
            criterio1.setValor(nomTipoTramite);
            criterioList.add(criterio1);
        }

        if (descTipoTramite.trim().length() > 0) {
            DTOCriterio criterio2 = new DTOCriterio();
            criterio2.setAtributo("descripcionTipoTramite");
            criterio2.setOperacion("like");
            criterio2.setValor(descTipoTramite);
            criterioList.add(criterio2);
        }

        if (nomCategoria.trim().length() > 0) {
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

        for (Object x : objecList) {
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
}
