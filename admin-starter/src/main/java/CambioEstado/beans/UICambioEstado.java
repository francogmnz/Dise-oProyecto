package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.ExpertoCambioEstado;
import CambioEstado.dtos.DTOEstadoDestinoCE;
import CambioEstado.dtos.DTOEstadoOrigenCE;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import entidades.EstadoTramite;
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
import utils.BeansUtils;

@Named("uiCambioEstado")
@ViewScoped
public class UICambioEstado implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private int legajoConsultor;  // Código del consultor para filtrar los trámites
    private String nombreConsultor;  // Nombre del consultor (si es necesario usarlo)
    private List<CambioEstadoGrillaUI> tramitesConsultor;  // Lista de trámites para mostrar
    private String nombreEstadoOrigen;
    private int codEstadoOrigen;
    private int nroTramite;
    

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
        mostrarEstadosPosibles(nroTramite);
    }
    
    public List<GrillaEstadosUI> mostrarEstadosPosibles(int nroTramite) {
    try {
        DTOEstadoOrigenCE estadoOrigen = controladorCambioEstado.mostrarEstadosPosibles(nroTramite);
        
        this.nombreEstadoOrigen = estadoOrigen.getNombreEstadoOrigen();
        System.out.println("Nombre: " + nombreEstadoOrigen);
        this.codEstadoOrigen = estadoOrigen.getCodEstadoOrigen();
        System.out.println("Codigo: " + codEstadoOrigen);
        
        List<GrillaEstadosUI> grillaEstados = new ArrayList<>();
        List<DTOEstadoDestinoCE> estadosDestino = estadoOrigen.getEstadosDestinos();
        for(DTOEstadoDestinoCE estado: estadosDestino){
            GrillaEstadosUI grillaUI = new GrillaEstadosUI();
            grillaUI.setCodEstadoTramite(estado.getCodEstadoDestino());
            grillaUI.setNombreEstadoTramite(estado.getNombreEstadoDestino());
            grillaEstados.add(grillaUI);
        }
        return grillaEstados;

    } catch (Exception e) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cambiar estado", e.getMessage()));
    }
    return new ArrayList<>(); // Stay on the same page
}

public String irMostrarEstado(int nroTramite) {
    this.nroTramite = nroTramite; // Guarda el nroTramite en una variable de instancia si no está definido ya en el bean
    return "EstadosPosiblesUI.xhtml?faces-redirect=true";
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

// Method to fetch the history



    

    // Getters y Setters
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
    
    
}