package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.beans.VersionGrillaUI;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.VersionDTO;
import entidades.TipoTramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiabmVersionLista")
@ViewScoped
public class UIABMVersionLista implements Serializable {

    private ControladorABMVersion controladorABMVersion = new ControladorABMVersion();
    private int nroVersionFiltro;
    private String descripcionFiltro = "";

    // Getters y Setters
    public ControladorABMVersion getControladorABMVersion() {
        return controladorABMVersion;
    }

    public void setControladorABMVersion(ControladorABMVersion controladorABMVersion) {
        this.controladorABMVersion = controladorABMVersion;
    }

    public int getNroVersionFiltro() {
        return nroVersionFiltro;
    }

    public void setNroVersionFiltro(int nroVersionFiltro) {
        this.nroVersionFiltro = nroVersionFiltro;
    }

    public String getDescripcionFiltro() {
        return descripcionFiltro;
    }

    public void setDescripcionFiltro(String descripcionFiltro) {
        this.descripcionFiltro = descripcionFiltro;
    }

    public void filtrar() {
        mostrarVersion();
    }

   

    public void anularVersion(int codTipoTramite, int nroVersion) {

        controladorABMVersion.anularVersion(codTipoTramite, nroVersion);
        Messages.create("Versión anulada").detail("La versión ha sido anulada correctamente.").add();
    }

    public String irConfigurarTipoTramite(int codTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "/Version/drawIU.xhtml?faces-redirect=true&codTipoTramite=" + codTipoTramite;
    }

    public List<VersionGrillaUI> mostrarVersion() {
        List<VersionGrillaUI> versionesGrilla = new ArrayList<>();
        try {
            List<DTOTipoTramiteVersion> versionesDTO = controladorABMVersion.mostrarVersion();

            for (DTOTipoTramiteVersion versionDTO : versionesDTO) {
                VersionGrillaUI versionGrillaUI = new VersionGrillaUI();
                versionGrillaUI.setNroVersion(versionDTO.getNroVersion());
                versionGrillaUI.setCodTipoTramite(versionDTO.getCodTipoTramite());
                versionGrillaUI.setNombreTipoTramite(versionDTO.getNombreTipoTramite());
                versionGrillaUI.setFechaDesdeVersion(versionDTO.getFechaDesdeVersion());
                versionGrillaUI.setFechaHastaVersion(versionDTO.getFechaHastaVersion());

                // Si la versión es 0, la añades a la lista para que se pueda modificar
                if (versionDTO.getNroVersion() == 0) {
                    versionesGrilla.add(versionGrillaUI);
                }
            }

            // Buscar y agregar la versión más alta para cada tipo de trámite
            for (DTOTipoTramiteVersion versionDTO : versionesDTO) {
                boolean esVersionMasAlta = true;
                for (DTOTipoTramiteVersion otraVersionDTO : versionesDTO) {
                    if (versionDTO.getCodTipoTramite() == otraVersionDTO.getCodTipoTramite()
                            && otraVersionDTO.getNroVersion() > versionDTO.getNroVersion()) {
                        esVersionMasAlta = false;
                        break;
                    }
                }
                // Si es la versión más alta, agregarla a la lista
                if (esVersionMasAlta) {
                    VersionGrillaUI versionGrillaUI = new VersionGrillaUI();
                    versionGrillaUI.setNroVersion(versionDTO.getNroVersion());
                    versionGrillaUI.setCodTipoTramite(versionDTO.getCodTipoTramite());
                    versionGrillaUI.setNombreTipoTramite(versionDTO.getNombreTipoTramite());
                    versionGrillaUI.setFechaDesdeVersion(versionDTO.getFechaDesdeVersion());
                    versionGrillaUI.setFechaHastaVersion(versionDTO.getFechaHastaVersion());
                    versionesGrilla.add(versionGrillaUI);
                }
            }

            // Aquí puedes llamar a un método para actualizar la UI
        } catch (Exception e) {
            Messages.create("Error al recuperar las versiones.").error().detail(e.getMessage()).add();
        }

        return versionesGrilla;
    }

    public boolean isAnulable(VersionGrillaUI v) {
        if (v.getNroVersion() > 1) {
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isVersionActiva(VersionGrillaUI v) {
        if (v != null) {
            Timestamp fd = v.getFechaDesdeVersion();
            Timestamp fh = v.getFechaHastaVersion();
            Timestamp fb = v.getFechaBajaVersion();
            if (fb == null) {  // Si no hay fecha de baja
                Timestamp hoy = new Timestamp(System.currentTimeMillis());
                // Verifica si hoy está dentro del rango de fechas
                return fd.before(hoy) && fh.after(hoy);
            }
        }
        return false; // Si la versión es nula o no está activa
    }

    
}
