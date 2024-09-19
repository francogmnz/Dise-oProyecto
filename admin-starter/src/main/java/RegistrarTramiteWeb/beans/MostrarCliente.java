/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb.beans;

import RegistrarTramiteWeb.dtos.DTOCliente;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
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
@Named("mostrarCliente")
//@ViewScoped
@SessionScoped
public class MostrarCliente implements Serializable{
    
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String mailCliente;

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getMailCliente() {
        return mailCliente;
    }

    public void setMailCliente(String mailCliente) {
        this.mailCliente = mailCliente;
    }
    
    @PostConstruct
    public void init() {
        // Obtener el cliente desde el Flash Scope
        
        DTOCliente cliente = (DTOCliente) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("cliente");

        if (cliente != null) {
            // Asignar los datos del cliente a las propiedades del bean
            this.dniCliente = cliente.getDniCliente();
            this.nombreCliente = cliente.getNombreCliente();
            this.apellidoCliente = cliente.getApellidoCliente();
            this.mailCliente = cliente.getMailCliente();
        }
    }
    
    public void confirmar() {
        // Lógica para la siguiente vista o acción a seguir
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("seleccionarCategoria.xhtml");
        } catch (IOException e) {
            Messages.create("Error al redirigir.").fatal().add();
        }
    }
    
    public void cancelar() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/admin/index.jsf");
        } catch (IOException e) {
            Messages.create("Error al redirigir al inicio.").fatal().add();
        }
    }


}
