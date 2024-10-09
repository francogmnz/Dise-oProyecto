package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTOTipoTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import utils.BeansUtils;

@Named("uitramite")
@ViewScoped
public class UIRegistrarTramite implements Serializable {

    private ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    private Boolean insert;
    private int nroTramite;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacion;
    private int dni;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;
    private int codTipoTramite;
    private String nombreTipoTramite;

    public boolean isInsert() {
        return insert;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
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

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
    }

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
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

    // obtenerCliente(dniCliente): DTOCliente
    public void obtenerCliente() {

        try {
            DTOCliente dtoCliente = controladorRegistrarTramite.obtenerCliente(dni);
            if (dtoCliente != null) {
                nombreCliente = dtoCliente.getNombreCliente();
                apellidoCliente = dtoCliente.getApellidoCliente();
                mailCliente = dtoCliente.getMailCliente();
            }

        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se encontró el Cliente"));
        }
    }

// obtenerTipoTramite(codTipoTramite): DTOTipoTramite
    public void obtenerTipoTramite() {
        try {
            DTOTipoTramite dtoTipoTramite = controladorRegistrarTramite.obtenerTipoTramite(codTipoTramite);
            if (dtoTipoTramite != null) {
                codTipoTramite = dtoTipoTramite.getCodTipoTramite();
                nombreTipoTramite = dtoTipoTramite.getNombreTipoTramite();
            }
        } catch (RegistrarTramiteException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "No se encontró el TipoTramite"));
        }
    }

    public String registrarTramite() throws IOException {
        try {
            controladorRegistrarTramite.registrarTramite();
            nroTramite = controladorRegistrarTramite.getUltimoNroTramite();
            // Redirigir al resumen del trámite con el número del trámite
            return "ResumenTramite?faces-redirect=true&nroTramite=" + nroTramite;
        } catch (RegistrarTramiteException e) {
            // Si ocurre una excepción, muestra el mensaje de error
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
            // No redirigir en caso de error, solo mostrar el mensaje
        }
        return null;
    }

    // Signo de ayuda para ir a los filtros de TipoTramite
    public String redirigirAfiltrosTipoTramite() {
        return "FiltrosTipoTramite.xhtml?faces-redirect=true";
    }

}
