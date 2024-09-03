
package ABMConsultor.beans;

import ABMConsultor.ControladorABMConsultor;
import ABMConsultor.dtos.DTOIngresoDatos;
import ABMConsultor.dtos.DTOModificacionDatos;
import ABMConsultor.dtos.DTOModificacionDatosIn;
import ABMConsultor.exceptions.ConsultorException;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

@Named("uiabmConsultor")
@ViewScoped

public class UIABMConsultor implements Serializable{
    
    private ControladorABMConsultor controladorABMConsultor = new ControladorABMConsultor();
    private boolean insert;
    private String nombreConsultor;
    private int legajoConsultor;
    private int numMaximoTramites;
    
    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public String getNombreConsultor() {
        return nombreConsultor;
    }

    public void setNombreConsultor(String nombreConsultor) {
        this.nombreConsultor = nombreConsultor;
    }

    public int getLegajoConsultor() {
        return legajoConsultor;
    }

    public void setLegajoConsultor(int legajoConsultor) {
        this.legajoConsultor = legajoConsultor;
    }

    public int getNumMaximoTramites() {
        return numMaximoTramites;
    }

    public void setNumMaximoTramites(int numMaximoTramites) {
        this.numMaximoTramites = numMaximoTramites;
    }
    
    public UIABMConsultor() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        int legajo = Integer.parseInt(request.getParameter("legajo"));
        insert=true;
        if(legajo > 0)
        {
            insert=false;
            DTOModificacionDatos dtoModificacionDatos = controladorABMConsultor.buscarConsultorAModificar(legajo);
            setNombreConsultor(dtoModificacionDatos.getNombreConsultor());
            setLegajoConsultor(dtoModificacionDatos.getLegajoConsultor());
            setNumMaximoTramites(dtoModificacionDatos.getNumMaximoTramites());
        }
        
    }
    public String agregarConsultor(){
        try {

        
            if(!insert)
            {

                DTOModificacionDatosIn dtoModificacionDatosIn = new DTOModificacionDatosIn();
                dtoModificacionDatosIn.setNombreConsultor(getNombreConsultor());
                dtoModificacionDatosIn.setLegajoConsultor(getLegajoConsultor());
                dtoModificacionDatosIn.setNumMaximoTramites(getNumMaximoTramites());
                controladorABMConsultor.modificarConsultor(dtoModificacionDatosIn);
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                DTOIngresoDatos nuevoConsultorDTO = new DTOIngresoDatos();
                nuevoConsultorDTO.setNombreConsultor(getNombreConsultor());
                nuevoConsultorDTO.setLegajoConsultor(getLegajoConsultor());
                nuevoConsultorDTO.setNumMaximoTramites(getNumMaximoTramites());
                controladorABMConsultor.agregarConsultor(nuevoConsultorDTO);

            }
            return BeansUtils.redirectToPreviousPage();
        }
        
        catch (ConsultorException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
         }
    }
}
