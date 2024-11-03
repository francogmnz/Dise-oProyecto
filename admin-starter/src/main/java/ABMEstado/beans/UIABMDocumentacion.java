package ABMEstado.beans;

import utils.BeansUtils;
import ABMEstado.ControladorABMEstado;
import ABMEstado.dtos.ModificarEstadoDTO;
import ABMEstado.dtos.ModificarEstadoDTOIn;
import ABMEstado.dtos.NuevoEstadoDTO;
import ABMEstado.exceptions.EstadoException;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.omnifaces.util.Messages;


@Named("uiabmEstado")
@ViewScoped

public class UIABMDocumentacion implements Serializable{

    private ControladorABMEstado controladorABMEstado = new ControladorABMEstado();
    private boolean insert;
    private String nombre;
    private int codigo;
    
    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public UIABMDocumentacion() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        insert=true;
        if(codigo > 0)
        {
            insert=false;
            ModificarEstadoDTO modificarEstadoDTO = controladorABMEstado.buscarEstadoAModificar(codigo);
            setNombre(modificarEstadoDTO.getNombre());
            setCodigo(modificarEstadoDTO.getCodigo());
        }
        
    }
    public String agregarEstado(){
        try {

        
            if(!insert)
            {

                ModificarEstadoDTOIn modificarEstadoDTOIn = new ModificarEstadoDTOIn();
                modificarEstadoDTOIn.setNombre(getNombre());
                modificarEstadoDTOIn.setCodigo(getCodigo());
                controladorABMEstado.modificarEstado(modificarEstadoDTOIn);
                return BeansUtils.redirectToPreviousPage();
            }
            else
            {
                NuevoEstadoDTO nuevoEstadoDTO = new NuevoEstadoDTO();
                nuevoEstadoDTO.setNombre(getNombre());
                nuevoEstadoDTO.setCodigo(getCodigo());
                controladorABMEstado.agregarEstado(nuevoEstadoDTO);

            }
            return BeansUtils.redirectToPreviousPage();
        }
        
        catch (EstadoException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
         }
    }

}
