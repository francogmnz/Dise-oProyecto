 /*
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
  /*
package ABMArticulo.beans;

import ABMArticulo.ControladorABMArticulo;
import ABMArticulo.dtos.ArticuloDTO;
import ABMArticulo.exceptions.ArticuloException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

/**
 *
 * @author matis
 */
  /*
@Named("uiabmArticuloLista")

@ViewScoped
public class UIABMArticuloLista implements Serializable {

 
   private ControladorABMArticulo controladorABMArticulo = new ControladorABMArticulo();
    private int codigoFiltro = 0;
    private String nombreFiltro = "";

    public ControladorABMArticulo getControladorABMArticulo() {
        return controladorABMArticulo;
    }

    public void setControladorABMArticulo(ControladorABMArticulo controladorABMArticulo) {
        this.controladorABMArticulo = controladorABMArticulo;
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

    }

    public List<ArticuloGrillaUI> buscarArticulos() {
        System.out.println(codigoFiltro);
        System.out.println(nombreFiltro);
        List<ArticuloGrillaUI> articulosGrilla = new ArrayList<>();
        List<ArticuloDTO> articulosDTO = controladorABMArticulo.buscarArticulos(codigoFiltro, nombreFiltro);

        for (ArticuloDTO articuloDTO : articulosDTO) {
            ArticuloGrillaUI articuloGrillaUI = new ArticuloGrillaUI();
            articuloGrillaUI.setCodigo(articuloDTO.getCodigo());
            articuloGrillaUI.setNombre(articuloDTO.getNombre());
            articuloGrillaUI.setEstado(articuloDTO.getEstado());
            articuloGrillaUI.setFechaAlta(articuloDTO.getFechaAlta());
            articuloGrillaUI.setFechaBaja(articuloDTO.getFechaBaja());
            articulosGrilla.add(articuloGrillaUI);
        }
        return articulosGrilla;

    }

    public String irAgregarArticulo() {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=0";
    }

    public String irModificarArticulo(int codigo) {
        BeansUtils.guardarUrlAnterior();
        return "abmArticulo?faces-redirect=true&codigo=" + codigo;
    }

    public void darDeBajaArticulo(int codigo) {
        try {
            controladorABMArticulo.darDeBajaArticulo(codigo);
            Messages.create("Anulado").detail("Anulado").add();
        } catch (ArticuloException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();

        }
    }
}
*/