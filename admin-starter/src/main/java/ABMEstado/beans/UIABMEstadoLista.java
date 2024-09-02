package ABMEstado.beans;
import utils.BeansUtils;
import ABMEstado.ControladorABMEstado;
import ABMEstado.dtos.EstadoDTO;

import ABMEstado.exceptions.EstadoException;
import jakarta.annotation.ManagedBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

@Named("uiabmEstadoLista")
@ViewScoped
public class UIABMEstadoLista implements Serializable {

    private ControladorABMEstado controladorABMEstado = new ControladorABMEstado();
    private int codigoFiltro=0;
    private String nombreFiltro="";

    public ControladorABMEstado getControladorABMEstado() {
        return controladorABMEstado;
    }

    public void setControladorABMEstado(ControladorABMEstado controladorABMEstado) {
        this.controladorABMEstado = controladorABMEstado;
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

    public void setNombreFiltro(String descripcionFiltro) {
        this.nombreFiltro = descripcionFiltro;
    }
    public void filtrar()
    {

    }

    public List<EstadoGrillaUI> buscarEstados(){
        System.out.println(codigoFiltro);
        System.out.println(nombreFiltro);
        List<EstadoGrillaUI> estadosGrilla = new ArrayList<>();
        List<EstadoDTO> estadosDTO = controladorABMEstado.buscarEstados(codigoFiltro,nombreFiltro);
        for (EstadoDTO estadoDTO : estadosDTO) {
            EstadoGrillaUI estadoGrillaUI = new EstadoGrillaUI();
            estadoGrillaUI.setCodigo(estadoDTO.getCodigo());
            estadoGrillaUI.setNombre(estadoDTO.getNombre());
            estadoGrillaUI.setFechaAlta(estadoDTO.getFechaAlta());
            estadoGrillaUI.setFechaBaja(estadoDTO.getFechaBaja());
            estadosGrilla.add(estadoGrillaUI);
        }
        return estadosGrilla;
    }

    public String irAgregarEstado() {
        BeansUtils.guardarUrlAnterior();
        return "abmEstado?faces-redirect=true&codigo=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    
    public String irModificarEstado(int codigo) {
        BeansUtils.guardarUrlAnterior();
        return "abmEstado?faces-redirect=true&codigo=" + codigo; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaEstado(int codigo){
        try {
            controladorABMEstado.darDeBajaEstado(codigo);
            Messages.create("Anulado").detail("Anulado").add();;
                    
        } catch (EstadoException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
        }
    }
    
}
