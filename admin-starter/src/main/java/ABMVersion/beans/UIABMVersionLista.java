package ABMVersion.beans;

import ABMVersion.ControladorABMVersion;
import ABMVersion.beans.VersionGrillaUI;
import ABMVersion.dtos.DTODatosVersionH;
import ABMVersion.dtos.DTOTipoTramiteVersion;
import ABMVersion.dtos.DTOVersionH;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

@Named("uiabmVersionLista")
@ViewScoped
public class UIABMVersionLista implements Serializable {

    private ControladorABMVersion controladorABMVersion = new ControladorABMVersion();

    private int codigoFiltro = 0;
    private String nombreFiltro = "";
    //agregado
    private int codTipoTramite;
    private String nombreTipoTramite;
    private List<DTODatosVersionH> datosVersionH;

    public List<DTODatosVersionH> getDatosVersionH() {
        return datosVersionH;
    }

    public void setDatosVersionH(List<DTODatosVersionH> datosVersionH) {
        this.datosVersionH = datosVersionH;
    }

    public String getNombreTipoTramite() {
        return nombreTipoTramite;
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

    public void setNombreTipoTramite(String nombreTipoTramite) {
        this.nombreTipoTramite = nombreTipoTramite;
    }

    public int getCodTipoTramite() {
        return codTipoTramite;
    }

    public void setCodTipoTramite(int codTipoTramite) {
        this.codTipoTramite = codTipoTramite;
    }
    //agregado

    // Getters y Setters
    public ControladorABMVersion getControladorABMVersion() {
        return controladorABMVersion;
    }

    public void setControladorABMVersion(ControladorABMVersion controladorABMVersion) {
        this.controladorABMVersion = controladorABMVersion;
    }

    public void filtrar() {
    }

    public void anularVersion(int codTipoTramite, int nroVersion) {

        controladorABMVersion.anularVersion(codTipoTramite, nroVersion);
        Messages.create("Versión anulada").detail("La versión ha sido anulada correctamente.").add();
    }

    public void cargarHistorial() {
        if (codTipoTramite != 0) {
            mostrarHistoricoVersion(codTipoTramite);
        }
    }

    public String irConfigurarTipoTramite(int codTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "/Version/drawIU.xhtml?faces-redirect=true&codTipoTramite=" + codTipoTramite;
    }

    public String irMostrarHistoricoVersiones(int codTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "/ABMVersion/historialVersion.xhtml?faces-redirect=true&codTipoTramite=" + codTipoTramite;
    }

    public List<VersionGrillaUI> mostrarVersion() {

        // Lista para la grilla
        List<VersionGrillaUI> versionGrilla = new ArrayList<>();

        // Obtener los trámites vigentes del consultor filtrado
        List<DTOTipoTramiteVersion> dtoTramitesVigentesList = controladorABMVersion.mostrarVersion(codigoFiltro, nombreFiltro);

        // Mapa para almacenar la versión más alta por cada tipo de trámite
        Map<Integer, DTOTipoTramiteVersion> versionMasRecientePorTramite = new HashMap<>();

        // Iterar sobre cada DTO
        for (DTOTipoTramiteVersion dtoTipoTramiteVersion : dtoTramitesVigentesList) {
            int codTipoTramite = dtoTipoTramiteVersion.getCodTipoTramite();

            // Verificar si ya existe una versión para este tipo de trámite
            if (!versionMasRecientePorTramite.containsKey(codTipoTramite)) {
                // Si no existe, agregar esta versión
                versionMasRecientePorTramite.put(codTipoTramite, dtoTipoTramiteVersion);
            } else {
                // Si ya existe una versión, verificar si esta es más reciente
                DTOTipoTramiteVersion versionExistente = versionMasRecientePorTramite.get(codTipoTramite);
                if (dtoTipoTramiteVersion.getFechaDesdeVersion().after(versionExistente.getFechaDesdeVersion())) {
                    // Actualizar si la versión actual tiene una fecha desde mayor
                    versionMasRecientePorTramite.put(codTipoTramite, dtoTipoTramiteVersion);
                }
            }
        }

        // Convertir las versiones más recientes en objetos para la grilla
        for (DTOTipoTramiteVersion versionReciente : versionMasRecientePorTramite.values()) {
            VersionGrillaUI versionGrillaUI = new VersionGrillaUI();

            // Setear los atributos del trámite en el objeto de la grilla
           
          
            versionGrillaUI.setNombreTipoTramite(versionReciente.getNombreTipoTramite());
            versionGrillaUI.setNroVersion(versionReciente.getNroVersion());
            versionGrillaUI.setFechaDesdeVersion(versionReciente.getFechaDesdeVersion());
            versionGrillaUI.setFechaHastaVersion(versionReciente.getFechaHastaVersion());
            versionGrillaUI.setFechaBajaVersion(versionReciente.getFechaBajaVersion());
            versionGrillaUI.setCodTipoTramite(versionReciente.getCodTipoTramite());
            // Agregar el objeto a la lista que se mostrará en la grilla
            versionGrilla.add(versionGrillaUI);
        }

        // Retornar la lista de objetos para mostrar en la grilla
        return versionGrilla;
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

    public void mostrarHistoricoVersion(int codTipoTramite) {
        // Lógica para obtener las versiones históricas
        VersionHistoricoGrillaUI versionHistoricoGrillaUI = new VersionHistoricoGrillaUI();

        try {
            // Llamar al controlador para obtener el DTO de versiones históricas
            DTOVersionH versionH = controladorABMVersion.mostrarHistoricoVersion(codTipoTramite);

            // Comprobar que el DTO no sea nulo
            if (versionH != null && versionH.getDtoDatosVersionH() != null) {
//            for (DTODatosVersionH datosVersionH : versionH.getDtoDatosVersionH()) 

                //VersionGrillaUI versionGrillaUI = new VersionGrillaUI();
                this.codTipoTramite = versionH.getCodTipoTramite();
                this.nombreTipoTramite = versionH.getNombreTipoTramite();
                this.datosVersionH = versionH.getDtoDatosVersionH();

                //Recorrer la lista por DTODatosVersionH
//                versionGrillaUI.setNroVersion(datosVersionH.getNroVersion());
//                versionGrillaUI.setFechaDesdeVersion(datosVersionH.getFechaDesdeVersion());
//                versionGrillaUI.setFechaHastaVersion(datosVersionH.getFechaHastaVersion());
            }

        } catch (Exception e) {
            Messages.create("Error al recuperar las versiones históricas.").error().detail(e.getMessage()).add();
        }

        // Redirigir a la página de historial de versiones
//        return versionHistoricoGrillaUI;
    }
       public void volverPantallaVersion(){
        BeansUtils.redirectToPreviousPage();
    }
       
       
       
}
