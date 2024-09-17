/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.ControladorRegistrarTramiteWeb;
import RegistrarTramiteWeb.dtos.DTOCliente;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import org.omnifaces.util.Messages;

/**
 *
 * @author licciardi
 */
@Named("ingresarDNI")
@ViewScoped
public class IngresarDNI implements Serializable{

    private int dniCliente; 
    private ControladorRegistrarTramiteWeb controladorRegistrarTramiteWeb = new ControladorRegistrarTramiteWeb();

    
    public String confirmar() throws RegistrarTramiteWebException, IOException {
        try {
            
            DTOCliente cliente = controladorRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
            
            if (cliente != null) {
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("mostrarCliente.xhtml");
            } 
            /*
            else {
                
            }
*/
        }catch (RegistrarTramiteWebException e) {
                Messages.create(e.getMessage()).fatal().add();
                return "";
         }
        return "";
    }


    public void cancelar() {
        dniCliente = 0; 
    }


    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }
}
