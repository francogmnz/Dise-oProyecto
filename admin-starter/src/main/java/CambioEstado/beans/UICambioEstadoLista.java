package CambioEstado.beans;

import CambioEstado.ControladorCambioEstado;
import CambioEstado.dtos.DTOHistorialEstado;
import CambioEstado.dtos.DTOTramitesVigentes;
import CambioEstado.dtos.TramiteDTO;
import CambioEstado.exceptions.CambioEstadoException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.PrimeFaces;
import utils.BeansUtils;

@Named("uicambioEstadoLista")
@ViewScoped
public class UICambioEstadoLista implements Serializable {

    private ControladorCambioEstado controladorCambioEstado = new ControladorCambioEstado();
    private int codigoFiltro = 0;
    private String nombreFiltro = "";
    private String nombreTramite;
    private int nroTramite;
    private int contador;
    
    private List<CambioEstadoHistoricoGrillaUI> historialEstadosList = new ArrayList<>();

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    
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
    this.nroTramite = nroTramite;
            
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
                cambioEstadoGrillaUI.setNombreConsultor(tramiteDTO.getNombreConsultor());
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
                grillaUI.setContador(dto.getContadorTET());
                grillaUI.setNroTramite(nroTramite);
                historialEstadosList.add(grillaUI);
            }
        } catch (CambioEstadoException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al obtener historial de estados", e.getMessage()));
        }
    }
    
    public void deshacerUltimoCambio() {
    try {
        controladorCambioEstado.deshacerUltimoCambio(nroTramite); // Llama al método en el controlador
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Último cambio deshecho con éxito", null));

    } catch (CambioEstadoException e) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al deshacer el cambio", e.getMessage()));
    }
  // Ejecutar el script de JavaScript para ir a la página anterior después de 1.5 segundos
    PrimeFaces.current().executeScript("setTimeout(function(){ window.history.back(); }, 1500);");

}
    
    public void volverPantallaTramiteConsultor(){
        BeansUtils.redirectToPreviousPage();
    }
    
}