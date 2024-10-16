package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("uiCambioEstado")
@ViewScoped
public class UICambioEstado implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private int legajoConsultor;  // Código del consultor para filtrar los trámites
    private String nombreConsultor;  // Nombre del consultor (si es necesario usarlo)
    private List<CambioEstadoGrillaUI> tramitesConsultor;  // Lista de trámites para mostrar

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
}
