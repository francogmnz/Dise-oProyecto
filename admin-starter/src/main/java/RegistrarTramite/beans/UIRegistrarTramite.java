package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTOTipoTramite;
import RegistrarTramite.dtos.DTOTramite;
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

    public String registrarTramite() throws RegistrarTramiteException {
        DTOTramite tramiteDTO = new DTOTramite();
        tramiteDTO.setDni(dni);
        tramiteDTO.setCodTipoTramite(codTipoTramite);
        controladorRegistrarTramite.registrarTramite(dni, codTipoTramite);
        return BeansUtils.redirectToPreviousPage();
    }

    public void obtenerCliente() {
        try {
            DTOCliente dtoCliente = controladorRegistrarTramite.obtenerCliente(dni);
            if (dtoCliente != null) {
                nombreCliente = dtoCliente.getNombreCliente();
                apellidoCliente = dtoCliente.getApellidoCliente();
                mailCliente = dtoCliente.getMailCliente();
            }
        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el Cliente"));
        }
    }

    public void obtenerTipoTramite() {
        try {
            TipoTramite tipoTramite = controladorRegistrarTramite.obtenerTipoTramite(codTipoTramite);
            if (tipoTramite != null) {
                DTOTipoTramite dtoTipoTramite = new DTOTipoTramite();
                this.nombreTipoTramiteSeleccionado = tipoTramite.getNombreTipoTramite();
            }
        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el TipoTramite"));
            this.nombreTipoTramiteSeleccionado = "";
        }
    }

    public String redirigirAfiltrosTipoTramite() {
        return "FiltrosTipoTramite.xhtml?faces-redirect=true";
    }

    // UIRegistrarTramite -> mostrarComboEstados(): List<DTOEstadoTramite>
    // Lista para guardar los estados disponibles
    private List<DTOEstadoTramite> estadoTramiteDisponibles;

    // Método para llamar al controlador y obtener la lista de estados
    public List<DTOEstadoTramite> getEstadoTramiteDisponibles() {
        if (estadoTramiteDisponibles == null) {
            estadoTramiteDisponibles = controladorRegistrarTramite.mostrarComboEstados();
        }
        return estadoTramiteDisponibles;
    }

}
