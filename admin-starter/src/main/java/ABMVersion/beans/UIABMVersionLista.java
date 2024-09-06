package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.beans.VersionGrillaUI;
import ABMVersion.dtos.VersionDTO;
import ABMVersion.exceptions.VersionException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

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
        // Actualiza la lista de versiones con los filtros aplicados
        buscarVersiones();
    }

    public List<VersionGrillaUI> buscarVersiones() {
        List<VersionGrillaUI> versionesGrilla = new ArrayList<>();
        try {
            List<VersionDTO> versionesDTO = controladorABMVersion.buscarVersion(nroVersionFiltro, descripcionFiltro, 0); // Ajusta según necesidad
            for (VersionDTO versionDTO : versionesDTO) {
                VersionGrillaUI versionGrillaUI = new VersionGrillaUI();
                versionGrillaUI.setNroVersion(versionDTO.getNroVersion());
                versionGrillaUI.setDescripcionVersion(versionDTO.getDescripcionVersion());
                versionGrillaUI.setFechaDesdeVersion(versionDTO.getFechaDesdeVersion());
                versionGrillaUI.setFechaHastaVersion(versionDTO.getFechaHastaVersion());
                versionGrillaUI.setFechaBajaVersion(versionDTO.getFechaBajaVersion());
                versionesGrilla.add(versionGrillaUI);
            }
        } catch (Exception e) {
            Messages.create("Error al recuperar las versiones.").error().detail(e.getMessage()).add();
        }
        return versionesGrilla;
    }

    public String irAgregarVersion() {
        BeansUtils.guardarUrlAnterior();
        return "abmVersion?faces-redirect=true&nroVersion=0";
    }

    public String irModificarVersion(int nroVersion) {
        BeansUtils.guardarUrlAnterior();
        return "abmVersion?faces-redirect=true&nroVersion=" + nroVersion;
    }

    public void darDeBajaVersion(int nroVersion) {
        try {
            controladorABMVersion.darDeBajaVersion(nroVersion);
            Messages.create("Versión anulada").detail("La versión ha sido anulada correctamente.").add();
        } catch (VersionException e) {
            Messages.create("Error al anular la versión.").error().detail(e.getMessage()).add();
        }
    }
}
