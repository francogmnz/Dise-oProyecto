/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMCategoriaTipoTramite.beans;
import utils.BeansUtils;
import ABMCategoriaTipoTramite.ControladorABMCategoriaTipoTramite;
import ABMCategoriaTipoTramite.dtos.ModificarCategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.dtos.ModificarCategoriaTipoTramiteDTOIn;
import ABMCategoriaTipoTramite.dtos.NuevaCategoriaTipoTramiteDTO;
import ABMCategoriaTipoTramite.exceptions.CategoriaTipoTramiteException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.omnifaces.util.Messages;

/**
 *
 * @author licciardi
 */

        
@Named("uiabmCategoriaTipoTramite")
@ViewScoped
public class UIABMCategoriaTipoTramite implements Serializable {

    private ControladorABMCategoriaTipoTramite controladorABMCategoriaTipoTramite = new ControladorABMCategoriaTipoTramite();

    private boolean insert;
    private int codCategoriaTipoTramite;
    private String nombreCategoriaTipoTramite;
    private String descripcionCategoriaTipoTramite;
    private String descripcionWebCategoriaTipoTramite;
    


    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public int getCodCategoriaTipoTramite() {
        return codCategoriaTipoTramite;
    }

    public void setCodCategoriaTipoTramite(int codCategoriaTipoTramite) {
        this.codCategoriaTipoTramite = codCategoriaTipoTramite;
    }

    public String getNombreCategoriaTipoTramite() {
        return nombreCategoriaTipoTramite;
    }

    public void setNombreCategoriaTipoTramite(String nombreCategoriaTipoTramite) {
        this.nombreCategoriaTipoTramite = nombreCategoriaTipoTramite;
    }

    public String getDescripcionCategoriaTipoTramite() {
        return descripcionCategoriaTipoTramite;
    }

    public void setDescripcionCategoriaTipoTramite(String descripcionCategoriaTipoTramite) {
        this.descripcionCategoriaTipoTramite = descripcionCategoriaTipoTramite;
    }

    public String getDescripcionWebCategoriaTipoTramite() {
        return descripcionWebCategoriaTipoTramite;
    }

    public void setDescripcionWebCategoriaTipoTramite(String descripcionWebCategoriaTipoTramite) {
        this.descripcionWebCategoriaTipoTramite = descripcionWebCategoriaTipoTramite;
    }



    

    public UIABMCategoriaTipoTramite() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        int codCategoriaTipoTramite = Integer.parseInt(request.getParameter("codCategoriaTipoTramite")); 
        insert = true;

        if (codCategoriaTipoTramite > 0) {
            insert = false;
            ModificarCategoriaTipoTramiteDTO modificarCategoriaTipoTramiteDTO = controladorABMCategoriaTipoTramite.buscarCategoriaTipoTramiteAModificar(codCategoriaTipoTramite);

            setCodCategoriaTipoTramite(modificarCategoriaTipoTramiteDTO.getCodCategoriaTipoTramite()); 
            setNombreCategoriaTipoTramite(modificarCategoriaTipoTramiteDTO.getNombreCategoriaTipoTramite());
            setDescripcionCategoriaTipoTramite(modificarCategoriaTipoTramiteDTO.getDescripcionCategoriaTipoTramite());
            setDescripcionWebCategoriaTipoTramite(modificarCategoriaTipoTramiteDTO.getDescripcionWebCategoriaTipoTramite());


        }
    }

        public String agregarCategoriaTipoTramite(){
        try {
            
            if(!insert)
            {
                ModificarCategoriaTipoTramiteDTOIn modificarCategoriaTipoTramiteDTOIn = new ModificarCategoriaTipoTramiteDTOIn();
                modificarCategoriaTipoTramiteDTOIn.setCodCategoriaTipoTramite(getCodCategoriaTipoTramite());
                modificarCategoriaTipoTramiteDTOIn.setNombreCategoriaTipoTramite(getNombreCategoriaTipoTramite());
                modificarCategoriaTipoTramiteDTOIn.setDescripcionCategoriaTipoTramite(getDescripcionCategoriaTipoTramite());
                modificarCategoriaTipoTramiteDTOIn.setDescripcionWebCategoriaTipoTramite(getDescripcionWebCategoriaTipoTramite());
                
                controladorABMCategoriaTipoTramite.modificarCategoriaTipoTramite(modificarCategoriaTipoTramiteDTOIn);
                Messages.create("Éxito").detail("Categoria modificada correctamente.").add();
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                NuevaCategoriaTipoTramiteDTO nuevaCategoriaTipoTramiteDTO = new NuevaCategoriaTipoTramiteDTO();
                nuevaCategoriaTipoTramiteDTO.setCodCategoriaTipoTramite(getCodCategoriaTipoTramite());
                nuevaCategoriaTipoTramiteDTO.setNombreCategoriaTipoTramite(getNombreCategoriaTipoTramite());
                nuevaCategoriaTipoTramiteDTO.setDescripcionCategoriaTipoTramite(getDescripcionCategoriaTipoTramite());
                nuevaCategoriaTipoTramiteDTO.setDescripcionWebCategoriaTipoTramite(getDescripcionWebCategoriaTipoTramite());        
                        
                controladorABMCategoriaTipoTramite.agregarCategoriaTipoTramite(nuevaCategoriaTipoTramiteDTO);
                Messages.create("Exito").detail("Categoria agregada correctamente.").add();
                return BeansUtils.redirectToPreviousPage();

            }
   
        }catch (CategoriaTipoTramiteException e) {
            Messages.create("Error").error().detail(e.getMessage()).add();
            return null;
    
         }
    }

}

    

