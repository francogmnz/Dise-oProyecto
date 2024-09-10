package ABMVersion.beans;
import ABMVersion.ControladorABMVersion;
import ABMVersion.dtos.ModificarVersionDTO;
import ABMVersion.dtos.ModificarVersionDTOIn;
import ABMVersion.dtos.NuevaVersionDTO;
import ABMVersion.exceptions.VersionException;
import entidades.Estado;
import entidades.EstadoTramite;
import entidades.TipoTramite;
import utils.BeansUtils;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.omnifaces.util.Messages;
import utils.FachadaPersistencia;

@Named("uiabmVersion")
@ViewScoped
public class UIABMVersion implements Serializable {

    private static final long serialVersionUID = 1L; // Agregar serialVersionUID para Serializable

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
    private String estadoSeleccionado;
    private String tipoTramiteSeleccionado;

    public String getTipoTramiteSeleccionado() {
        return tipoTramiteSeleccionado;
    }

    public void setTipoTramiteSeleccionado(String tipoTramiteSeleccionado) {
        this.tipoTramiteSeleccionado = tipoTramiteSeleccionado;
    }
    private List<TipoTramite> listaTiposTramite = new ArrayList<>();
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
    public void setListaEstadosTramite(List<EstadoTramite> listaEstadosTramite) {
        this.listaEstadosTramite = listaEstadosTramite;
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
    // Método para actualizar los estados de trámite
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
                        // Configurar las fechas si están disponibles en el DTO
                        // setFechaDesdeVersion(modificarVersionDTO.getFechaDesdeVersion());
                        // setFechaHastaVersion(modificarVersionDTO.getFechaHastaVersion());
                        // setFechaBajaVersion(modificarVersionDTO.getFechaBajaVersion());
                    }
                }
            } else {
                insert = true; // Agregar nueva versión
            }

            // Cargar los estados de trámite
            actualizarEstadosTramite();
            actualizarTiposTramite();
            // También puedes cargar otros datos si es necesario, como los tipos de trámite
            // this.listaTiposTramite = controladorABMVersion.obtenerTiposTramite();

        } catch (NumberFormatException e) {
            Messages.create("Error al obtener el número de versión.").fatal().add();
        } catch (Exception e) {
            Messages.create("Error inesperado: " + e.getMessage()).fatal().add();
        }
    }
    public String agregarVersion() {
        try {
            if (!insert) {
                ModificarVersionDTOIn modificarVersionDTOIn = new ModificarVersionDTOIn();
                modificarVersionDTOIn.setNroVersion(getNroVersion());
                modificarVersionDTOIn.setDescripcionVersion(getDescripcionVersion());
                modificarVersionDTOIn.setCodTipoTramite(getCodTipoTramite());
                // También puedes agregar las fechas aquí si es necesario
                // modificarVersionDTOIn.setFechaDesdeVersion(getFechaDesdeVersion());
                // modificarVersionDTOIn.setFechaHastaVersion(getFechaHastaVersion());
                // modificarVersionDTOIn.setFechaBajaVersion(getFechaBajaVersion());

                controladorABMVersion.modificarVersion(modificarVersionDTOIn);
            } else {
                NuevaVersionDTO nuevaVersionDTO = new NuevaVersionDTO();
                nuevaVersionDTO.setNroVersion(getNroVersion());
                nuevaVersionDTO.setDescripcionVersion(getDescripcionVersion());
                nuevaVersionDTO.setCodTipoTramite(getCodTipoTramite());
                // También puedes agregar las fechas aquí si es necesario
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
}
