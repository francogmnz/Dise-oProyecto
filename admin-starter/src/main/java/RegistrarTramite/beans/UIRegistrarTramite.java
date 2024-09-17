package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.TramiteDTO;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.Cliente;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.FachadaPersistencia;

@Named("uitramite")
@ViewScoped
public class UIRegistrarTramite implements Serializable {

    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    private Boolean insert;
    private int nroTramite;
    private int dni;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacion;
    private String nombreTipoTramiteSeleccionado;
    private String nombreEstadoSeleccionado;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;

    private int codTipoTramite;

    private List<TipoTramite> tiposTramiteDisponibles;
//    private List<EstadoTramite> estadosTramiteDisponibles;

    public String getNombreTipoTramiteSeleccionado() {
        return nombreTipoTramiteSeleccionado;
    }

    public void setNombreTipoTramiteSeleccionado(String nombreTipoTramiteSeleccionado) {
        this.nombreTipoTramiteSeleccionado = nombreTipoTramiteSeleccionado;
    }

    public String getNombreEstadoSeleccionado() {
        return nombreEstadoSeleccionado;
    }

    public void setNombreEstadoSeleccionado(String nombreEstadoSeleccionado) {
        this.nombreEstadoSeleccionado = nombreEstadoSeleccionado;
    }

    public boolean isInsert() {
        return insert;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Timestamp getFechaRecepcionTramite() {
        return fechaRecepcionTramite;
    }

    public void setFechaRecepcionTramite(Timestamp fechaRecepcionTramite) {
        this.fechaRecepcionTramite = fechaRecepcionTramite;
    }

    public Timestamp getFechaAnulacion() {
        return fechaAnulacion;
    }

    public void setFechaAnulacion(Timestamp fechaAnulacion) {
        this.fechaAnulacion = fechaAnulacion;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public List<TipoTramite> getTiposTramiteDisponibles() {
        if (tiposTramiteDisponibles == null) {
            cargarTiposTramiteDisponibles();
        }
        return tiposTramiteDisponibles;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public void cargarTiposTramiteDisponibles() {
        List<Object> resultado = FachadaPersistencia.getInstance().buscar("TipoTramite", new ArrayList<>());
        tiposTramiteDisponibles = resultado.stream()
                .map(obj -> (TipoTramite) obj)
                .collect(Collectors.toList());
    }

//    public List<EstadoTramite> getEstadoTramiteDisponibles() {
//        if (estadosTramiteDisponibles == null) {
//            cargarEstadosTramiteDisponibles();
//        }
//        return estadosTramiteDisponibles;
//    }
//
//    public void cargarEstadosTramiteDisponibles() {
//        List<Object> resultado = FachadaPersistencia.getInstance().buscar("EstadoTramite", new ArrayList<>());
//        estadosTramiteDisponibles = resultado.stream()
//                .map(obj -> (EstadoTramite) obj)
//                .collect(Collectors.toList());
//    }
//
//    private EstadoTramite buscarEstadoTramitePorOID(String oid) {
//        for (EstadoTramite estadoTramite : estadosTramiteDisponibles) {
//            if (estadoTramite.getOID().equals(oid)) {
//                return estadoTramite;
//            }
//        }
//        return null;
//    }
    public String registrarTramite() throws RegistrarTramiteException {
        TramiteDTO tramiteDTO = new TramiteDTO();
        tramiteDTO.setDni(dni);
        tramiteDTO.setCodTipoTramite(codTipoTramite);
        controladorRegistrarTramite.registrarTramite(dni, codTipoTramite);
        return BeansUtils.redirectToPreviousPage();
    }

    public void obtenerCliente() {
        try {
            Cliente cliente = controladorRegistrarTramite.obtenerCliente(dni);
            if (cliente != null) {
                this.nombreCliente = cliente.getNombreCliente();
                this.apellidoCliente = cliente.getApellidoCliente();
                this.mailCliente = cliente.getMailCliente();
            }
        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el Cliente"));
            this.nombreCliente = "";
            this.apellidoCliente = "";
            this.mailCliente = "";
        }
    }

    public void obtenerTipoTramite() {
        try {
            TipoTramite tipoTramite = controladorRegistrarTramite.obtenerTipoTramite(codTipoTramite);
            if (tipoTramite != null) {
                this.nombreTipoTramiteSeleccionado = tipoTramite.getNombreTipoTramite();
            }
        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el TipoTramite"));
            this.nombreTipoTramiteSeleccionado = "";
        }
    }

    List<DTOEstadoTramite> nombresEstadoTramite = controladorRegistrarTramite.mostrarComboEstados();

    public List<DTOEstadoTramite> getNombresEstadoTramite() {
        return nombresEstadoTramite;
    }

    public void setNombresEstadoTramite(List<DTOEstadoTramite> nombresEstadoTramite) {
        this.nombresEstadoTramite = nombresEstadoTramite;
    }

    private List<DTOEstadoTramite> estadosTramiteDisponibles;

    public List<DTOEstadoTramite> getEstadosTramiteDisponibles() {
        if (estadosTramiteDisponibles == null) {
            estadosTramiteDisponibles = controladorRegistrarTramite.mostrarComboEstados();
        }
        return estadosTramiteDisponibles;
    }

    public String redirigirAfiltrosTipoTramite() {
        return "FiltrosTipoTramite.xhtml?faces-redirect=true";
    }

}
