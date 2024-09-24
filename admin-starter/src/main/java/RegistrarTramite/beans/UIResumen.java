package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;
import org.omnifaces.util.Messages;

@Named("uiresumen")
@ViewScoped
public class UIResumen implements Serializable{
    
    private int nroTramite;
    private Timestamp fechaRecepcionTramite;
    private int plazoDocumentacion;
    private int codTipoTramite;
    private String nombreTipoTramite;
    private String nombreEstado;
    private int precioTramite;
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;
    
    @PostConstruct
    public void init() {
        // Obtener el parámetro de la URL
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String codigoTramite = params.get("codigo");

        if (codigoTramite != null) {
            try {
                this.nroTramite = Integer.parseInt(codigoTramite);

                // Llamar al controlador para obtener el tramite
                DTOTramiteElegido tramiteElegido = controladorRegistrarTramite.mostrarResumenTramite(nroTramite);

                // Asignar los datos del tramite a los atributos del bean
                if (tramiteElegido != null) {
                    this.nroTramite = tramiteElegido.getNroTramite();
                    this.fechaRecepcionTramite = tramiteElegido.getFechaRecepcionTramite();
                    this.plazoDocumentacion = tramiteElegido.getPlazoDocumentacion();
                    this.precioTramite = tramiteElegido.getPrecioTramite();
                    this.codTipoTramite = tramiteElegido.getCodTipoTramite();
                    this.nombreTipoTramite = tramiteElegido.getNombreTipoTramite();
                    this.nombreEstado = tramiteElegido.getNombreEstado();
                    this.dniCliente = tramiteElegido.getDniCliente();
                    this.nombreCliente = tramiteElegido.getNombreCliente();
                    this.apellidoCliente = tramiteElegido.getApellidoCliente();
                    this.mailCliente = tramiteElegido.getMailCliente();
                }
            } catch (NumberFormatException e) {
                // Manejar error de conversión de número
                e.printStackTrace();
            }
        }
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

    public int getPlazoDocumentacion() {
        return plazoDocumentacion;
    }

    public void setPlazoDocumentacion(int plazoDocumentacion) {
        this.plazoDocumentacion = plazoDocumentacion;
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

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public int getPrecioTramite() {
        return precioTramite;
    }

    public void setPrecioTramite(int precioTramite) {
        this.precioTramite = precioTramite;
    }

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
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
    
    ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();
    
    public void anularTramite(int nroTramite){
        controladorRegistrarTramite.anularTramite(nroTramite);
        Messages.create("Anulado").detail("Anulado").add();   
    }
    
}