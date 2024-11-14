package ABMDocumentacion.beans;

import ABMDocumentacion.ControladorABMDocumentacion;
import ABMDocumentacion.dtos.ModificarDocumentacionDTO;
import ABMDocumentacion.dtos.ModificarDocumentacionDTOIn;
import ABMDocumentacion.dtos.NuevoDocumentacionDTO;
import ABMDocumentacion.exceptions.DocumentacionException;
import utils.BeansUtils;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;


@Named("uiabmDocumentacion")
@ViewScoped

public class UIABMDocumentacion implements Serializable{

    private ControladorABMDocumentacion controladorABMDocumentacion = new ControladorABMDocumentacion();
    private boolean insert;
    private String nombreDocumentacion;
    private int codDocumentacion;
    private String descripcionDocumentacion;
    
    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public String getNombreDocumentacion() {
        return nombreDocumentacion;
    }

    public void setNombreDocumentacion(String nombreDocumentacion) {
        this.nombreDocumentacion = nombreDocumentacion;
    }

    public int getCodDocumentacion() {
        return codDocumentacion;
    }

    public void setCodDocumentacion(int codDocumentacion) {
        this.codDocumentacion = codDocumentacion;
    }

    public String getDescripcionDocumentacion() {
        return descripcionDocumentacion;
    }

    public void setDescripcionDocumentacion(String descripcionDocumentacion) {
        this.descripcionDocumentacion = descripcionDocumentacion;
    }
    
    


    
    public UIABMDocumentacion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        codDocumentacion = Integer.parseInt(request.getParameter("codDocumentacion"));
        insert=true;
        if(codDocumentacion > 0)
        {
            insert=false;
            try {
                ModificarDocumentacionDTO modificarDocumentacionDTO = controladorABMDocumentacion.buscarDocumentacionAModificar(codDocumentacion);
                setNombreDocumentacion(modificarDocumentacionDTO.getNombreDocumentacion());
                setCodDocumentacion(modificarDocumentacionDTO.getCodDocumentacion());
                setDescripcionDocumentacion(modificarDocumentacionDTO.getDescripcionDocumentacion());
            } catch (Exception e) {
                Messages.create("Error!").error().detail(e.getMessage()).add();
                Faces.getExternalContext().getFlash().setKeepMessages(true);
                Faces.redirect(externalContext.getRequestContextPath() + "/ABMDocumentacion/abmDocumentacionLista.jsf");
            }
        }
        
    }
    public String agregarDocumentacion(){
        try {
            
            if(!insert)
            {

                ModificarDocumentacionDTOIn modificarDocumentacionDTOIn = new ModificarDocumentacionDTOIn();
                modificarDocumentacionDTOIn.setNombreDocumentacion(getNombreDocumentacion());
                modificarDocumentacionDTOIn.setCodDocumentacion(getCodDocumentacion());
                modificarDocumentacionDTOIn.setDescripcionDocumentacion(getDescripcionDocumentacion());
                controladorABMDocumentacion.modificarDocumentacion(modificarDocumentacionDTOIn);
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                NuevoDocumentacionDTO nuevoDocumentacionDTO = new NuevoDocumentacionDTO();
                nuevoDocumentacionDTO.setNombreDocumentacion(getNombreDocumentacion());
                nuevoDocumentacionDTO.setCodDocumentacion(getCodDocumentacion());
                nuevoDocumentacionDTO.setDescripcionDocumentacion(getDescripcionDocumentacion());
                controladorABMDocumentacion.agregarDocumentacion(nuevoDocumentacionDTO);

            }
            return BeansUtils.redirectToPreviousPage();
        }
        
        catch (DocumentacionException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
         }
    }
}
