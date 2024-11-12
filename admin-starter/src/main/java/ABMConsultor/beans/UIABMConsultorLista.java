package ABMConsultor.beans;

import ABMConsultor.ControladorABMConsultor;
import ABMConsultor.dtos.DTOConsultor;
import ABMConsultor.exceptions.ConsultorException;
import entidades.Consultor;
import entidades.Tramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiabmConsultorLista")
@ViewScoped

public class UIABMConsultorLista implements Serializable {

    private ControladorABMConsultor controladorABMConsultor = new ControladorABMConsultor();
    private int legajoFiltro = 0;
    private int legajoSeleccionado=0;
    private String nombreFiltro = "";
    private int numMaximoTramitesFiltro = 0;
    private String criterio = "";

    public ControladorABMConsultor getControladorABMConsultor() {
        return controladorABMConsultor;
    }

    public void setControladorABMConsultor(ControladorABMConsultor controladorABMConsultor) {
        this.controladorABMConsultor = controladorABMConsultor;
    }

    public int getLegajoFiltro() {
        return legajoFiltro;
    }

    public void setLegajoFiltro(int legajoFiltro) {
        this.legajoFiltro = legajoFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String descripcionFiltro) {
        this.nombreFiltro = descripcionFiltro;
    }

    public int getNumMaximoTramitesFiltro() {
        return numMaximoTramitesFiltro;
    }

    public void setNumMaximoTramitesFiltro(int numMaximoTramitesFiltro) {
        this.numMaximoTramitesFiltro = numMaximoTramitesFiltro;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public int getLegajoSeleccionado() {
        return legajoSeleccionado;
    }

    public void setLegajoSeleccionado(int legajoSeleccionado) {
        this.legajoSeleccionado = legajoSeleccionado;
    }
    

    public void filtrar() {

    }

    public List<ConsultorGrillaUI> buscarConsultores() {

        List<ConsultorGrillaUI> consultorsGrilla = new ArrayList<>();
        List<DTOConsultor> consultoresDTO = controladorABMConsultor.buscarConsultores(legajoFiltro, nombreFiltro, numMaximoTramitesFiltro);
        for (DTOConsultor consultorDTO : consultoresDTO) {
            ConsultorGrillaUI consultorGrillaUI = new ConsultorGrillaUI();
            consultorGrillaUI.setLegajoConsultor(consultorDTO.getLegajoConsultor());
            consultorGrillaUI.setNombreConsultor(consultorDTO.getNombreConsultor());
            consultorGrillaUI.setNumMaximoTramites(consultorDTO.getNumMaximoTramites());
            consultorGrillaUI.setFechaHoraBajaConsultor(consultorDTO.getFechaHoraBajaConsultor());
            consultorsGrilla.add(consultorGrillaUI);
        }
        return filtrarConsultores(consultorsGrilla);
    }

    public String irAgregarConsultor() {
        BeansUtils.guardarUrlAnterior();
        return "abmConsultor?faces-redirect=true&legajo=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public String irModificarConsultor(int legajo) {
        BeansUtils.guardarUrlAnterior();
        return "abmConsultor?faces-redirect=true&legajo=" + legajo; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaConsultor(int legajo) {
        try {
            controladorABMConsultor.darDeBajaConsultor(legajo);
            Messages.create("Anulado").detail("Anulado").add();;

        } catch (ConsultorException e) {
            Messages.create("Error!").error().detail("No se puede dar de baja, el consultor tiene asignado al menos un tramite.").add();
        }
    }

    //    DEVUELVE TRUE SI LA LISTA DE PRECIOS ESTA ANULADA
    public boolean isAnulada(ConsultorGrillaUI consultorEnviado) {
        if (consultorEnviado.getFechaHoraBajaConsultor() != null) {
            return true;
        } else {
            return false;
        }
    }

    public int buscarTramitesConsultor(int legajoConsultor) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        DTOCriterio unCriterio = new DTOCriterio();
        unCriterio.setAtributo("legajoConsultor");
        unCriterio.setOperacion("=");
        unCriterio.setValor(legajoConsultor);
        lCriterio.add(unCriterio);

        Consultor consultorEncontrado = (Consultor) FachadaPersistencia.getInstance().buscar("Consultor", lCriterio).get(0);
        int legajoEncontrado = consultorEncontrado.getLegajoConsultor();

        lCriterio.clear();

        unCriterio = new DTOCriterio();

        unCriterio.setAtributo("fechaFinTramite");
        unCriterio.setOperacion("=");
        unCriterio.setValor(null);

        lCriterio.add(unCriterio);

        DTOCriterio unCriterio2 = new DTOCriterio();

        unCriterio2.setAtributo("fechaAnulacionTramite");
        unCriterio2.setOperacion("=");
        unCriterio2.setValor(null);

        lCriterio.add(unCriterio2);

        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", lCriterio);

        int cantidadTramites = 0;

        for (Object x : objetoList) {

            Tramite tramite = (Tramite) x;
            if (tramite.getConsultor() != null) {
                Consultor consultor = tramite.getConsultor();
                int legajo = consultor.getLegajoConsultor();

                if (legajoEncontrado == legajo) {
                    cantidadTramites += 1;
                }

            }
        }

        return cantidadTramites;

    }

    public List<ConsultorGrillaUI> filtrarConsultores(List<ConsultorGrillaUI> conGrilla) {
        List<ConsultorGrillaUI> consultoresActivos = new ArrayList<>();
        List<ConsultorGrillaUI> consultoresInactivos = new ArrayList<>();
        for (ConsultorGrillaUI consultor : conGrilla) {
            if (consultor.getFechaHoraBajaConsultor() == null) {
                consultoresActivos.add(consultor);
            } else {
                consultoresInactivos.add(consultor);
            }
        }
        switch (criterio) {
            case "conActivo":
                return consultoresActivos;
            case "conInactivo":
                return consultoresInactivos;
            default:
                return conGrilla;
        }
    }

}
