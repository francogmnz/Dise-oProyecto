/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMCategoriaTipoTramite.beans;

import ABMCategoriaTipoTramite.ControladorABMCategoriaTipoTramite;
import ABMCategoriaTipoTramite.dtos.CategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.exceptions.CategoriaTipoTramiteException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

/**
 *
 * @author licciardi
 */
@Named("uiabmCategoriaTipoTramiteLista")
@ViewScoped
public class UIABMCategoriaTipoTramiteLista implements Serializable {

    private ControladorABMCategoriaTipoTramite controladorABMCategoriaTipoTramite = new ControladorABMCategoriaTipoTramite();
    private int codCategoriaTipoTramiteFiltro=0;
    private String nombreCategoriaTipoTramiteFiltro="";
    private String criterio = "";

    public ControladorABMCategoriaTipoTramite getControladorABMCategoriaTipoTramite() {
        return controladorABMCategoriaTipoTramite;
    }

    public void setControladorABMCategoriaTipoTramite(ControladorABMCategoriaTipoTramite controladorABMCategoriaTipoTramite) {
        this.controladorABMCategoriaTipoTramite = controladorABMCategoriaTipoTramite;
    }

    public int getCodCategoriaTipoTramiteFiltro() {
        return codCategoriaTipoTramiteFiltro;
    }

    public void setCodCategoriaTipoTramiteFiltro(int codCategoriaTipoTramiteFiltro) {
        this.codCategoriaTipoTramiteFiltro = codCategoriaTipoTramiteFiltro;
    }

    public String getNombreCategoriaTipoTramiteFiltro() {
        return nombreCategoriaTipoTramiteFiltro;
    }

    public void setNombreCategoriaTipoTramiteFiltro(String nombreCategoriaTipoTramiteFiltro) {
        this.nombreCategoriaTipoTramiteFiltro = nombreCategoriaTipoTramiteFiltro;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }
    
      
    public void filtrar()
    {

    }

    public List<CategoriaTipoTramiteGrillaUI> buscarCategoriasTipoTramite(){
        System.out.println(codCategoriaTipoTramiteFiltro);
        System.out.println(nombreCategoriaTipoTramiteFiltro);
        List<CategoriaTipoTramiteGrillaUI> categoriasTipoTramiteGrilla = new ArrayList<>();
        List<CategoriaTipoTramiteDTO> categoriasTipoTramiteDTO = controladorABMCategoriaTipoTramite.buscarCategoriasTipoTramite(codCategoriaTipoTramiteFiltro, nombreCategoriaTipoTramiteFiltro);
        for (CategoriaTipoTramiteDTO categoriaTipoTramiteDTO : categoriasTipoTramiteDTO) {
            CategoriaTipoTramiteGrillaUI categoriaTipoTramiteGrillaUI = new CategoriaTipoTramiteGrillaUI();
            categoriaTipoTramiteGrillaUI.setCodCategoriaTipoTramite(categoriaTipoTramiteDTO.getCodCategoriaTipoTramite());
            categoriaTipoTramiteGrillaUI.setNombreCategoriaTipoTramite(categoriaTipoTramiteDTO.getNombreCategoriaTipoTramite());
            categoriaTipoTramiteGrillaUI.setDescripcionCategoriaTipoTramite(categoriaTipoTramiteDTO.getDescripcionCategoriaTipoTramite());
            categoriaTipoTramiteGrillaUI.setDescripcionWebCategoriaTipoTramite(categoriaTipoTramiteDTO.getDescripcionWebCategoriaTipoTramite());
            categoriaTipoTramiteGrillaUI.setFechaHoraBajaCategoriaTipoTramite(categoriaTipoTramiteDTO.getFechaHoraBajaCategoriaTipoTramite());

            categoriasTipoTramiteGrilla.add(categoriaTipoTramiteGrillaUI);
        }
        return filtrarCategorias(categoriasTipoTramiteGrilla);
    }

    public String irAgregarCategoriaTipoTramite() {
        BeansUtils.guardarUrlAnterior();
        return "abmCategoriaTipoTramite?faces-redirect=true&codCategoriaTipoTramite=0"; 
    }

    
    public String irModificarCategoriaTipoTramite(int codCategoriaTipoTramite) {
        BeansUtils.guardarUrlAnterior();
        return "abmCategoriaTipoTramite?faces-redirect=true&codCategoriaTipoTramite=" + codCategoriaTipoTramite; 
    }

    public void darDeBajaCategoriaTipoTramite(int codCategoriaTipoTramite){
        try {
            controladorABMCategoriaTipoTramite.darDeBajaCategoriaTipoTramite(codCategoriaTipoTramite);
            Messages.create("Exito").detail("Categoría dada de baja correctamente.").add();
                    
        } catch (CategoriaTipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
        }
    }
    
    
    public boolean isAnulada(CategoriaTipoTramiteGrillaUI categoriaEnviada) {
        if (categoriaEnviada.getFechaHoraBajaCategoriaTipoTramite() != null) {
            return true;
        } else {
            return false;
        }
    }    
    
    public List<CategoriaTipoTramiteGrillaUI> filtrarCategorias(List<CategoriaTipoTramiteGrillaUI> categoriasGrilla) {
        List<CategoriaTipoTramiteGrillaUI> categoriasTTActivas = new ArrayList<>();
        List<CategoriaTipoTramiteGrillaUI> categoriasTTInactivas = new ArrayList<>();
        for (CategoriaTipoTramiteGrillaUI categoria : categoriasGrilla) {
            if (categoria.getFechaHoraBajaCategoriaTipoTramite() == null) {
                categoriasTTActivas.add(categoria);
            } else {
                categoriasTTInactivas.add(categoria);
            }
        }
        switch (criterio) {
            case "categoriasTTActivas":                
                return categoriasTTActivas;
            case "categoriasTTInactivas":
                return categoriasTTInactivas;
            default:
                return categoriasGrilla;
        }
    }    
}
