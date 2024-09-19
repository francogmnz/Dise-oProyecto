package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.ModificarVersionDTOIn;
import ABMVersion.dtos.DTODatosVersion;
import ABMVersion.exceptions.VersionException;
import Version.beans.NodoIU;
import Version.beans.NodoMenuIU;
import com.google.gson.Gson;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import jakarta.faces.application.NavigationHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Named("uiabmVersion")
@ViewScoped
public class UIABMVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    private ControladorABMVersion controladorABMVersion = new ControladorABMVersion();
    private boolean insert;
    private int nroVersion;
    private String descripcionVersion;
    private int codTipoTramite;
    private String nombreTipoTramite;
    private Date fechaBajaVersion;
    private Date fechaDesdeVersion;
    private Date fechaHastaVersion;

    private List<EstadoTramite> listaEstadosTramite = new ArrayList<>();
    private List<TipoTramite> listaTiposTramite = new ArrayList<>();

    private String estadoSeleccionado;
    private String tipoTramiteSeleccionado;

    // Variables para la interfaz del diagrama
    private String guardarJSON = "";
    private String cargarJSON = "";
    private String titulo = "";
    private boolean editable;
    private String nodosPosibles = "";

    // Constructor
    public UIABMVersion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        try {
            String nroVersionParam = request.getParameter("nroVersion");
            if (nroVersionParam != null) {
                int nroVersion = Integer.parseInt(nroVersionParam);
                this.nroVersion = nroVersion;
                insert = true;
                if (nroVersion > 0) {
                    ModificarVersionDTO modificarVersionDTO = controladorABMVersion.buscarVersionAModificar(nroVersion);
                    if (modificarVersionDTO != null) {
                        setDescripcionVersion(modificarVersionDTO.getDescripcionVersion());
                        setCodTipoTramite(modificarVersionDTO.getCodTipoTramite());
                    }
                }
            } else {
                insert = true; // Agregar nueva versión
            }

            // Cargar los estados de trámite y tipos de trámite
            actualizarEstadosTramite();
            actualizarTiposTramite();

            // Preparar nodos después de cargar los datos
            prepararNodos();

        } catch (NumberFormatException e) {
            Messages.create("Error al obtener el número de versión.").fatal().add();
        } catch (Exception e) {
            Messages.create("Error inesperado: " + e.getMessage()).fatal().add();
        }
    }

    // Método para preparar los nodos del diagrama
    public void prepararNodos() {
     

        // Convertir a JSON
        Gson gson = new Gson();

        // Actualizar título y estado editable
        titulo = "Versión Dinámica";
        editable = true;

        // Estados posibles dinámicos (menú)
        List<NodoMenuIU> lestadosP = listaEstadosTramite.stream()
                .map(estado -> {
                    NodoMenuIU nodoMenu = new NodoMenuIU();
                    nodoMenu.setCodigo(estado.getCodEstadoTramite());
                    nodoMenu.setNombre(estado.getNombreEstadoTramite());
                    return nodoMenu;
                })
                .collect(Collectors.toList());

        nodosPosibles = gson.toJson(lestadosP);
    }

    // Getters y setters
    public ControladorABMVersion getControladorABMVersion() {
        return controladorABMVersion;
    }

    public void setControladorABMVersion(ControladorABMVersion controladorABMVersion) {
        this.controladorABMVersion = controladorABMVersion;
    }

    public List<TipoTramite> getListaTiposTramite() {
        return listaTiposTramite;
    }

    public List<EstadoTramite> getListaEstadosTramite() {
        return listaEstadosTramite;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getTipoTramiteSeleccionado() {
        return tipoTramiteSeleccionado;
    }

    public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
        this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
    }

    public Date getFechaDesdeVersion() {
        return fechaDesdeVersion;
    }

    public void setFechaDesdeVersion(Date fechaDesdeVersion) {
        this.fechaDesdeVersion = fechaDesdeVersion;
    }

    public Date getFechaHastaVersion() {
        return fechaHastaVersion;
    }

    public void setFechaHastaVersion(Date fechaHastaVersion) {
        this.fechaHastaVersion = fechaHastaVersion;
    }

    public Date getFechaBajaVersion() {
        return fechaBajaVersion;
    }

    public void setFechaBajaVersion(Date fechaBajaVersion) {
        this.fechaBajaVersion = fechaBajaVersion;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getNroVersion() {
        return nroVersion;
    }

    public void setNroVersion(int nroVersion) {
        this.nroVersion = nroVersion;
    }

    public String getDescripcionVersion() {
        return descripcionVersion;
    }

    public void setDescripcionVersion(String descripcionVersion) {
        this.descripcionVersion = descripcionVersion;
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

    // Métodos de actualización de estados y tipos de trámite
    public void actualizarEstadosTramite() {
        try {
            this.listaEstadosTramite = controladorABMVersion.obtenerEstadosTramiteActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los estados de trámite: " + e.getMessage()).fatal().add();
        }
    }

    public void actualizarTiposTramite() {
        try {
            this.listaTiposTramite = controladorABMVersion.obtenerTiposTramitesActivos();
        } catch (Exception e) {
            Messages.create("Error al cargar los tipos de trámite: " + e.getMessage()).fatal().add();
        }
    }

    // Método para agregar o modificar una versión
    public String agregarVersion() {
        try {
            if (!insert) {
                ModificarVersionDTOIn modificarVersionDTOIn = new ModificarVersionDTOIn();
                modificarVersionDTOIn.setNroVersion(getNroVersion());
                modificarVersionDTOIn.setDescripcionVersion(getDescripcionVersion());
                modificarVersionDTOIn.setCodTipoTramite(getCodTipoTramite());
                controladorABMVersion.modificarVersion(modificarVersionDTOIn);
            } else {
                DTODatosVersion nuevaVersionDTO = new DTODatosVersion();
                nuevaVersionDTO.setNroVersion(getNroVersion());
                nuevaVersionDTO.setDescripcionVersion(getDescripcionVersion());
                nuevaVersionDTO.setCodTipoTramite(getCodTipoTramite());
                nuevaVersionDTO.setFechaDesdeVersion(new Timestamp(getFechaDesdeVersion().getTime()));
                nuevaVersionDTO.setFechaHastaVersion(new Timestamp(getFechaHastaVersion().getTime()));
                nuevaVersionDTO.setFechaBajaVersion(null);
                controladorABMVersion.agregarVersion(nuevaVersionDTO);
            }
            return BeansUtils.redirectToPreviousPage();
        } catch (VersionException e) {
            Messages.create("Error al guardar la versión: " + e.getMessage()).fatal().add();
            return "";
        }
    }
    public String irAgregarVersion() {
  FacesContext facesContext = FacesContext.getCurrentInstance();
    ExternalContext externalContext = facesContext.getExternalContext();
    Flash flash = externalContext.getFlash();
    
    // Guarda el codTipoTramite en Flash para que esté disponible en la página destino
    flash.put("codTipoTramite", getCodTipoTramite());
    
    // Redirige a la página drawVersion.xhtml
    NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();
    navigationHandler.handleNavigation(facesContext, null, "drawIU.xhtml?faces-redirect=true");
    
    return null; // El redireccionamiento se maneja a través del NavigationHandler
}
    
    // Métodos relacionados con el manejo de nodos del diagrama
    public String getGuardarJSON() {
        return guardarJSON;
    }

    public void setGuardarJSON(String guardarJSON) {
        this.guardarJSON = guardarJSON;
    }

    public String getCargarJSON() {
        return cargarJSON;
    }

    public void setCargarJSON(String cargarJSON) {
        this.cargarJSON = cargarJSON;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getNodosPosibles() {
        return nodosPosibles;
    }

    public void setNodosPosibles(String nodosPosibles) {
        this.nodosPosibles = nodosPosibles;
    }

}
