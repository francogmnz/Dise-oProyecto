
package ABMConsultor.beans;


import ABMConsultor.ControladorABMConsultor;
import ABMConsultor.dtos.DTOConsultor;
import ABMConsultor.exceptions.ConsultorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

@Named("uiabmConsultorLista")
@ViewScoped

public class UIABMConsultorLista implements Serializable {

    private ControladorABMConsultor controladorABMConsultor = new ControladorABMConsultor();
    private int legajoFiltro=0;
    private String nombreFiltro="";
    private int numMaximoTramitesFiltro=0;

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
    
     public void filtrar()
    {

    }

    public List<ConsultorGrillaUI> buscarConsultores(){
        System.out.println(legajoFiltro);
        System.out.println(nombreFiltro);
        System.out.println(numMaximoTramitesFiltro);
        List<ConsultorGrillaUI> consultorsGrilla = new ArrayList<>();
        List<DTOConsultor> consultoresDTO = controladorABMConsultor.buscarConsultores(legajoFiltro,nombreFiltro, numMaximoTramitesFiltro);
        for (DTOConsultor consultorDTO : consultoresDTO) {
            ConsultorGrillaUI consultorGrillaUI = new ConsultorGrillaUI();
            consultorGrillaUI.setLegajoConsultor(consultorDTO.getLegajoConsultor());
            consultorGrillaUI.setNombreConsultor(consultorDTO.getNombreConsultor());
            consultorGrillaUI.setNumMaximoTramites(consultorDTO.getNumMaximoTramites());
            consultorGrillaUI.setFechaAltaConsultor(consultorDTO.getFechaAltaConsultor());
            consultorGrillaUI.setFechaBajaConsultor(consultorDTO.getFechaBajaConsultor());
            consultorsGrilla.add(consultorGrillaUI);
        }
        return consultorsGrilla;
    }

    public String irAgregarConsultor() {
        BeansUtils.guardarUrlAnterior();
        return "abmConsultor?faces-redirect=true&legajo=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    
    public String irModificarConsultor(int legajo) {
        BeansUtils.guardarUrlAnterior();
        return "abmConsultor?faces-redirect=true&legajo=" + legajo; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaConsultor(int legajo){
        try {
            controladorABMConsultor.darDeBajaConsultor(legajo);
            Messages.create("Anulado").detail("Anulado").add();;
                    
        } catch (ConsultorException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }
    
}
