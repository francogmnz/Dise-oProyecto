/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMEstadoTramite.beans;

import ABMEstadoTramite.ControladorABMEstadoTramite;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTO;
import ABMEstadoTramite.dtos.ModificarEstadoTramiteDTOIn;
import ABMEstadoTramite.dtos.NuevoEstadoTramiteDTO;
import ABMEstadoTramite.exceptions.EstadoTramiteException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

/**
 *
 * @author matis
 */
@Named("uiabmEstadoTramite")
@ViewScoped
public class UIABMEstadoTramite implements Serializable {

    private ControladorABMEstadoTramite controladorABMEstadoTramite = new ControladorABMEstadoTramite();
    private boolean insert;
    private String nombreEstadoTramite;
    private int codEstadoTramite;
    private String descripcionEstadoTramite;

    public ControladorABMEstadoTramite getControladorABMEstadoTramite() {
        return controladorABMEstadoTramite;
    }

    public void setControladorABMEstadoTramite(ControladorABMEstadoTramite controladorABMEstadoTramite) {
        this.controladorABMEstadoTramite = controladorABMEstadoTramite;
    }

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public String getNombreEstadoTramite() {
        return nombreEstadoTramite;
    }

    public void setNombreEstadoTramite(String nombreEstadoTramite) {
        this.nombreEstadoTramite = nombreEstadoTramite;
    }

    public int getCodEstadoTramite() {
        return codEstadoTramite;
    }

    public void setCodEstadoTramite(int codEstadoTramite) {
        this.codEstadoTramite = codEstadoTramite;
    }

    public String getDescripcionEstadoTramite() {
        return descripcionEstadoTramite;
    }

    public void setDescripcionEstadoTramite(String descripcionEstadoTramite) {
        this.descripcionEstadoTramite = descripcionEstadoTramite;
    }

    
    
    public UIABMEstadoTramite() throws EstadoTramiteException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        int codEstadoTramite = Integer.parseInt(request.getParameter("codEstadoTramite"));
        insert = true;
                if(codEstadoTramite > 0)
        {
            insert=false;
            ModificarEstadoTramiteDTO modificarEstadoTramiteDTO = controladorABMEstadoTramite.buscarEstadoTramiteAModificar(codEstadoTramite);
            setNombreEstadoTramite(modificarEstadoTramiteDTO.getNombreEstadoTramite());
            setCodEstadoTramite(modificarEstadoTramiteDTO.getCodEstadoTramite());
            setDescripcionEstadoTramite(modificarEstadoTramiteDTO.getDescripcionEstadoTramite());
        }
    }
    
        public String agregarEstadoTramite(){
        try {

        
            if(!insert)
            {
                ModificarEstadoTramiteDTOIn modificarEstadoTramiteDTOIn = new ModificarEstadoTramiteDTOIn();
                modificarEstadoTramiteDTOIn.setNombreEstadoTramite(getNombreEstadoTramite());
                modificarEstadoTramiteDTOIn.setCodEstadoTramite(getCodEstadoTramite());
                modificarEstadoTramiteDTOIn.setDescripcionEstadoTramite(getDescripcionEstadoTramite());
                controladorABMEstadoTramite.modificarEstadoTramite(modificarEstadoTramiteDTOIn);
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                NuevoEstadoTramiteDTO nuevoEstadoTramiteDTO = new NuevoEstadoTramiteDTO();
                nuevoEstadoTramiteDTO.setNombreEstadoTramite(getNombreEstadoTramite());
                nuevoEstadoTramiteDTO.setCodEstadoTramite(getCodEstadoTramite());
                nuevoEstadoTramiteDTO.setDescripcionEstadoTramite(getDescripcionEstadoTramite());
                controladorABMEstadoTramite.agregarEstadoTramite(nuevoEstadoTramiteDTO);

            }
            return BeansUtils.redirectToPreviousPage();
        }
        
        catch (EstadoTramiteException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
         }
    }
}
