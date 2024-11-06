package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOMostrarHistorial;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import entidades.TramiteEstadoTramite;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import utils.BeansUtils;

@Named("uicambioEstadoLista")
@ViewScoped
public class UICambioEstadoLista implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private int codigoFiltro = 0;
    private String nombreFiltro = "";
    private String nombreTramite;
    private int nroTramite;
    
    private List<CambioEstadoHistoricoGrillaUI> historialEstadosList = new ArrayList<>();

    
    public List<CambioEstadoHistoricoGrillaUI> getHistorialEstadosList() {
        return historialEstadosList;
    }

    public void setHistorialEstadosList(List<CambioEstadoHistoricoGrillaUI> historialEstadosList) {
        this.historialEstadosList = historialEstadosList;
    }
    
    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    
    public ControladorCambioEstado getControladorCambioEstado() {
        return controladorCambioEstado;
    }

    public void setControladorCambioEstado(ControladorCambioEstado controladorCambioEstado) {
        this.controladorCambioEstado = controladorCambioEstado;
    }

    public int getCodigoFiltro() {
        return codigoFiltro;
    }

    public void setCodigoFiltro(int codigoFiltro) {
        this.codigoFiltro = codigoFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public void filtrar() {
        // Implementar lógica de filtrado según los filtros si es necesario
    }
    
    public String irMostrarHistorialEstados(int nroTramite) {
    this.nroTramite = nroTramite; // Set it in case it's required on the next page
        System.out.println("Hola");
            
    BeansUtils.guardarUrlAnterior();
    return "/CambioEstado/HistorialTramite.xhtml?faces-redirect=true&nroTramite=" + nroTramite;
}

    public List<CambioEstadoGrillaUI> buscarTramites() {
        System.out.println(codigoFiltro); // Código del consultor
        System.out.println(nombreFiltro); // Filtro de nombre (si es que lo usas)

        // Lista para la grilla
        List<CambioEstadoGrillaUI> cambioEstadoGrilla = new ArrayList<>();
        
        // Obtener los trámites vigentes del consultor filtrado
        List<DTOTramitesVigentes> dtoTramitesVigentesList = controladorCambioEstado.buscarTramites(codigoFiltro);
        
        // Recorrer cada DTOTramitesVigentes
        for (DTOTramitesVigentes dtoTramitesVigentes : dtoTramitesVigentesList) {
            
            // Recorrer cada trámite en el DTOTramitesVigentes
            for (TramiteDTO tramiteDTO : dtoTramitesVigentes.getTramites()) {
                
                // Crear un nuevo objeto para la interfaz de la grilla
                CambioEstadoGrillaUI cambioEstadoGrillaUI = new CambioEstadoGrillaUI();
                
                // Setear los atributos del trámite en el objeto de la grilla
                cambioEstadoGrillaUI.setCodEstadoTramite(tramiteDTO.getEstadoTramite().getCodEstadoTramite()); // Código del estado del trámite
                cambioEstadoGrillaUI.setNombreEstadoTramite(tramiteDTO.getEstadoTramite().getNombreEstadoTramite());
                cambioEstadoGrillaUI.setFechaInicioTramite(tramiteDTO.getFechaInicioTramite()); // Fecha de inicio del trámite
                cambioEstadoGrillaUI.setFechaRecepcionTramite(tramiteDTO.getFechaRecepcionTramite()); // Fecha de recepción
                cambioEstadoGrillaUI.setNroTramite(tramiteDTO.getNroTramite()); // Número del trámite
                
                // Agregar el objeto a la lista que se mostrará en la grilla
                cambioEstadoGrilla.add(cambioEstadoGrillaUI);
            }
        }
        
        // Retornar la lista de objetos para mostrar en la grilla
        return cambioEstadoGrilla;
    }
    
 
    public void mostrarHistorialEstados(int nroTramite) {
        try {
            List<DTOHistorialEstado> dtoHistorial = controladorCambioEstado.obtenerHistorialEstados(nroTramite);

            historialEstadosList = new ArrayList<>();

            for (DTOHistorialEstado dto : dtoHistorial) {
                CambioEstadoHistoricoGrillaUI grillaUI = new CambioEstadoHistoricoGrillaUI();
                grillaUI.setNombreEstadoTramite(dto.getNombreEstadoTramite());
                grillaUI.setFechaDesdeTET(dto.getFechaDesdeTET());
                grillaUI.setFechaHastaTET(dto.getFechaHastaTET());
                grillaUI.setContador(dto.getContador());
                grillaUI.setNroTramite(nroTramite);
                historialEstadosList.add(grillaUI);
            }
        } catch (CambioEstadoException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener historial de estados", e.getMessage()));
        }
    }
    public void volverPantallaTramiteConsultor(){
        BeansUtils.redirectToPreviousPage();
    }
    
}
        // Redirigir a la página de historial de versiones
//        return versionHistoricoGrillaUI;
  



   /* public String irAgregarArticulo() {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=0";
    }

    public String irModificarArticulo(int codigo) {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=" + codigo;
    }

    /*public void darDeBajaArticulo(int codigo) {
        try {
            controladorABMArticulo.darDeBajaArticulo(codigo);
            Messages.create("Anulado").detail("Anulado").add();
        } catch (ArticuloException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();

        }
    }*/
