/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.Flash;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author licciardi
 */

@Named("seleccionarCategoria")
@ViewScoped
public class SeleccionarCategoria implements Serializable {

    private List<DTOCategoriaTipoTramite> categoriasTipoTramite;
    private List<CategoriaTipoTramiteGrillaUI> categoriasTipoTramiteGrilla;
    private ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb = new ControladorRegistrarTramiteWeb();

    public SeleccionarCategoria() {
        categoriasTipoTramite = new ArrayList<>();
    }

    public List<DTOCategoriaTipoTramite> getCategoriasTipoTramite() {
        return categoriasTipoTramite;
    }

    public List<CategoriaTipoTramiteGrillaUI> getCategoriasTipoTramiteGrilla() {
        return categoriasTipoTramiteGrilla;
    }

    public List<CategoriaTipoTramiteGrillaUI> listarCategoriasTipoTramite() {
        List<CategoriaTipoTramiteGrillaUI> categoriaTipoTramiteGrilla = new ArrayList<>();
        List<DTOCategoriaTipoTramite> dtoCategoriasTipoTramite = controladorRegistrarTramiteWeb.listarCategoriasTipoTramite();

        for (DTOCategoriaTipoTramite dtoCategoria : dtoCategoriasTipoTramite) {
            categoriasTipoTramite.add(dtoCategoria);
            CategoriaTipoTramiteGrillaUI categoriaGrillaUI = new CategoriaTipoTramiteGrillaUI();
            categoriaGrillaUI.setCodCategoriaTipoTramite(dtoCategoria.getCodCategoriaTipoTramite());
            categoriaGrillaUI.setNombreCategoriaTipoTramite(dtoCategoria.getNombreCategoriaTipoTramite());
            categoriaGrillaUI.setDescripcionCategoriaTipoTramite(dtoCategoria.getDescripcionCategoriaTipoTramite());

            categoriaTipoTramiteGrilla.add(categoriaGrillaUI);
        }

        return categoriaTipoTramiteGrilla;
    }


    
    public void seleccionarCategoria(CategoriaTipoTramiteGrillaUI categoriaGrillaUI) {
        DTOCategoriaTipoTramite categoriaSeleccionada = null;

        for (DTOCategoriaTipoTramite categoria : categoriasTipoTramite) {
            if (categoria.getCodCategoriaTipoTramite() == categoriaGrillaUI.getCodCategoriaTipoTramite()) {
                categoriaSeleccionada = categoria;
                break; 
            }
        }

        if (categoriaSeleccionada != null) {
            try {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                Flash flash = externalContext.getFlash();
                flash.put("categoriaSeleccionada", categoriaSeleccionada);
                flash.keep("categoriaSeleccionada"); // Asegura que el dato se mantenga después del redirect
                externalContext.redirect("seleccionarTipoTramite.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void cancelar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
