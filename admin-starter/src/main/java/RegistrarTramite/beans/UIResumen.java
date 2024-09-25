package RegistrarTramite.beans;

import RegistrarTramite.ControladorRegistrarTramite;
import RegistrarTramite.dtos.DTODocumentacion;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.Part;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import utils.BeansUtils;

@Named("uiresumen")
@ViewScoped
public class UIResumen implements Serializable {

    private int nroTramite;
    private Timestamp fechaRecepcionTramite;
    private Timestamp fechaAnulacionTramite;
    private int plazoDocumentacion;
    private int codTipoTramite;
    private String nombreTipoTramite;
    private String nombreEstado;
    private int precioTramite;
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;
    private int codTD;
    private String nombreDocumentacion;
    private Timestamp fechaEntregaDoc;
    private List<DTODocumentacion> resumenDoc;

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
                    this.fechaAnulacionTramite = tramiteElegido.getFechaAnulacionTramite();
                    this.plazoDocumentacion = tramiteElegido.getPlazoDocumentacion();
                    this.precioTramite = tramiteElegido.getPrecioTramite();
                    this.codTipoTramite = tramiteElegido.getCodTipoTramite();
                    this.nombreTipoTramite = tramiteElegido.getNombreTipoTramite();
                    this.nombreEstado = tramiteElegido.getNombreEstado();
                    this.dniCliente = tramiteElegido.getDniCliente();
                    this.nombreCliente = tramiteElegido.getNombreCliente();
                    this.apellidoCliente = tramiteElegido.getApellidoCliente();
                    this.mailCliente = tramiteElegido.getMailCliente();

                    this.resumenDoc = tramiteElegido.getResumenDoc();

                    for (DTODocumentacion doc : resumenDoc) {
                        this.codTD = doc.getCodTD();
                        this.nombreDocumentacion = doc.getNombreDocumentacion();
                        this.fechaEntregaDoc = doc.getFechaEntregaDoc();
                    }
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

    public String getNombreDocumentacion() {
        return nombreDocumentacion;
    }

    public void setNombreDocumentacion(String nombreDocumentacion) {
        this.nombreDocumentacion = nombreDocumentacion;
    }

    public Timestamp getFechaEntregaDoc() {
        return fechaEntregaDoc;
    }

    public void setFechaEntregaDoc(Timestamp fechaEntregaDoc) {
        this.fechaEntregaDoc = fechaEntregaDoc;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }

    public Timestamp getFechaAnulacionTramite() {
        return fechaAnulacionTramite;
    }

    public void setFechaAnulacionTramite(Timestamp fechaAnulacionTramite) {
        this.fechaAnulacionTramite = fechaAnulacionTramite;
    }

    public int getCodTD() {
        return codTD;
    }

    public void setCodTD(int codTD) {
        this.codTD = codTD;
    }

    public List<DTODocumentacion> getResumenDoc() {
        return resumenDoc;
    }

    public void setResumenDoc(List<DTODocumentacion> resumenDoc) {
        this.resumenDoc = resumenDoc;
    }

    ControladorRegistrarTramite controladorRegistrarTramite = new ControladorRegistrarTramite();

    // anularTramite()
    public void anularTramite(int nroTramite) throws RegistrarTramiteException {
        try {
            controladorRegistrarTramite.anularTramite(nroTramite);
            Messages.create("Trámite Anulado").add();
        } catch (RegistrarTramiteException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }

    public String registrarDocumentacion(int codTD){
        return "CargaDocumentacion?faces-redirect=true&codTD=" + codTD;
    }
}
