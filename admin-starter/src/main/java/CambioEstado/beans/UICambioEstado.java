package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.DTOEstadoDestinoCE;
import CambioEstado.dtos.DTOEstadoOrigenCE;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import entidades.Consultor;
import entidades.TramiteEstadoTramite;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.PrimeFaces;

@Named("uiCambioEstado")
@ViewScoped
public class UICambioEstado implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private Consultor consultor;
    private int legajoConsultor;  // Código del consultor para filtrar los trámites
    private String nombreConsultor;  // Nombre del consultor (si es necesario usarlo)
    private List<CambioEstadoGrillaUI> tramitesConsultor;  // Lista de trámites para mostrar
    private String nombreEstadoOrigen;
    private int codEstadoOrigen;
    private int nroTramite;
    private String observaciones;
    private List<GrillaEstadosUI> estadosDestinoList;
    private GrillaEstadosUI estadoDestinoSeleccionado;

    public UICambioEstado() throws IOException {
        // Inicialización y obtención de parámetros de la request
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        // Obtener código de consultor desde la request
        if (request.getParameter("legajoConsultor") != null) {
            legajoConsultor = Integer.parseInt(request.getParameter("legajoConsultor"));
            cargarTramitesConsultor(); // Cargar trámites asociados al consultor
        }
    }

    @PostConstruct
    public void init() {
    }

    public void onPreRenderView() {
        System.out.println("Valor de nroTramite en onPreRenderView: " + nroTramite);
        if (nroTramite > 0) {
            mostrarEstadosPosibles(nroTramite);
        }
    }

    public void mostrarEstadosPosibles(int nroTramite) {
        try {
            DTOEstadoOrigenCE estadoOrigen = controladorCambioEstado.mostrarEstadosPosibles(nroTramite);

            this.nombreEstadoOrigen = estadoOrigen.getNombreEstadoOrigen();
            System.out.println("Nombre: " + nombreEstadoOrigen);
            this.codEstadoOrigen = estadoOrigen.getCodEstadoOrigen();
            System.out.println("Codigo: " + codEstadoOrigen);

            estadosDestinoList = new ArrayList<>();
            List<DTOEstadoDestinoCE> estadosDestino = estadoOrigen.getEstadosDestinos();
            for (DTOEstadoDestinoCE estado : estadosDestino) {
                GrillaEstadosUI grillaUI = new GrillaEstadosUI();
                grillaUI.setCodEstadoTramite(estado.getCodEstadoDestino());
                grillaUI.setNombreEstadoTramite(estado.getNombreEstadoDestino());
                estadosDestinoList.add(grillaUI);
            }

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al mostrar estados", e.getMessage()));
        }
        // Ejecutar el script de JavaScript para ir a la página anterior después de 1.5 segundos
    }

    public String irMostrarEstado(int nroTramite) {
        return "EstadosPosiblesUI.xhtml?faces-redirect=true&nroTramite=" + nroTramite;
    }

    public void cambiarEstado() {
        try {
            if (estadoDestinoSeleccionado != null) {
               
                         // Crear un nuevo objeto de cambio de estado
                controladorCambioEstado.cambiarEstado(nroTramite, estadoDestinoSeleccionado.getCodEstadoTramite());
      
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado cambiado con exito", null));
                PrimeFaces.current().executeScript("setTimeout(function(){ window.history.back(); }, 1500);");

            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Debe seleccionar un estado destino", null));
            }
        } catch (CambioEstadoException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cambiar estado", e.getMessage()));
        }
    }

    // Método para cargar trámites al iniciar
    public void cargarTramitesConsultor() {
        tramitesConsultor = new ArrayList<>();
        // Obtener los trámites vigentes del consultor desde el controlador
        List<DTOTramitesVigentes> dtoTramitesVigentesList = controladorCambioEstado.buscarTramites(legajoConsultor);

        // Recorrer los trámites y agregarlos a la lista de UI
        for (DTOTramitesVigentes dtoTramitesVigentes : dtoTramitesVigentesList) {
            for (TramiteDTO tramiteDTO : dtoTramitesVigentes.getTramites()) {
                CambioEstadoGrillaUI tramiteUI = new CambioEstadoGrillaUI();
                tramiteUI.setCodEstadoTramite(tramiteDTO.getEstadoTramite().getCodEstadoTramite());
                tramiteUI.setNombreEstadoTramite(tramiteDTO.getEstadoTramite().getNombreEstadoTramite());
                tramiteUI.setFechaInicioTramite(tramiteDTO.getFechaInicioTramite());
                tramiteUI.setFechaRecepcionTramite(tramiteDTO.getFechaRecepcionTramite());
                tramiteUI.setNroTramite(tramiteDTO.getNroTramite());

                if (tramiteDTO.getNombreConsultor() != null) {
                    tramiteUI.setNombreConsultor(tramiteDTO.getNombreConsultor());
                }
                // Agregar a la lista de trámites a mostrar
                tramitesConsultor.add(tramiteUI);
            }
        }
    }

    // Método para buscar trámites basado en el legajo consultor ingresado
    public void buscarTramites() {
        if (legajoConsultor > 0) {
            cargarTramitesConsultor(); // Cargar trámites para el legajo consultor ingresado
        } else {
            System.out.println("Por favor ingrese un código de consultor válido.");
        }
    }

    private List<TramiteEstadoTramite> historialEstados;

// Getter for the history list
    public List<TramiteEstadoTramite> getHistorialEstados() {
        return historialEstados;
    }
// Getters y Setters

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<GrillaEstadosUI> getEstadosDestinoList() {
        return estadosDestinoList;
    }

    public void setEstadosDestinoList(List<GrillaEstadosUI> estadosDestinoList) {
        this.estadosDestinoList = estadosDestinoList;
    }

    public GrillaEstadosUI getEstadoDestinoSeleccionado() {
        return estadoDestinoSeleccionado;
    }

    public void setEstadoDestinoSeleccionado(GrillaEstadosUI estadoDestinoSeleccionado) {
        this.estadoDestinoSeleccionado = estadoDestinoSeleccionado;
    }

    public List<CambioEstadoGrillaUI> getTramitesConsultor() {
        return tramitesConsultor;
    }

    public int getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(int legajoConsultor) { // Corrige el nombre del setter
        this.legajoConsultor = legajoConsultor;
    }

    public String getNombreConsultor() {
        return nombreConsultor;
    }

    public void setNombreConsultor(String nombreConsultor) {
        this.nombreConsultor = nombreConsultor;
    }

    public String getNombreEstadoOrigen() {
        return nombreEstadoOrigen;
    }

    public void setNombreEstadoOrigen(String nombreEstadoOrigen) {
        this.nombreEstadoOrigen = nombreEstadoOrigen;
    }

    public int getCodEstadoOrigen() {
        return codEstadoOrigen;
    }

    public void setCodEstadoOrigen(int codEstadoOrigen) {
        this.codEstadoOrigen = codEstadoOrigen;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public void volverPantallaTramiteConsultor() throws IOException {
        // Guardar legajoConsultor en el Flash scope
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("legajoConsultor", legajoConsultor);

        // Redirigir a la página de la lista de trámites
        FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/CambioEstado/CambioEstadoTramiteLista.jsf?faces-redirect=true");
    }

}
