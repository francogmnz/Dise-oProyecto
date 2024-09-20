
package ABMCliente.beans;


import ABMCliente.beans.*;
import ABMCliente.ControladorABMCliente;
import ABMCliente.dtos.DTOCliente;
import ABMCliente.exceptions.ClienteException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;

@Named("uiabmClienteLista")
@ViewScoped

public class UIABMClienteLista implements Serializable {

    private ControladorABMCliente controladorABMCliente = new ControladorABMCliente();
    private int dniFiltro=0;
    private String nombreFiltro="";
   

    public ControladorABMCliente getControladorABMCliente() {
        return controladorABMCliente;
    }

    public void setControladorABMCliente(ControladorABMCliente controladorABMCliente) {
        this.controladorABMCliente = controladorABMCliente;
    }

    public int getDniFiltro() {
        return dniFiltro;
    }

    public void setDniFiltro(int dniFiltro) {
        this.dniFiltro = dniFiltro;
    }

    public String getNombreFiltro() {
        return nombreFiltro;
    }

    public void setNombreFiltro(String descripcionFiltro) {
        this.nombreFiltro = descripcionFiltro;
    } 

    
     public void filtrar()
    {

    }

    public List<ClienteGrillaUI> buscarClientes(){
        System.out.println(dniFiltro);
        System.out.println(nombreFiltro);
        List<ClienteGrillaUI> clientesGrilla = new ArrayList<>();
        List<DTOCliente> clientesDTO = controladorABMCliente.buscarClientes(dniFiltro,nombreFiltro);
        for (DTOCliente clienteDTO : clientesDTO) {
            ClienteGrillaUI clienteGrillaUI = new ClienteGrillaUI();
            clienteGrillaUI.setDniCliente(clienteDTO.getDniCliente());
            clienteGrillaUI.setNombreCliente(clienteDTO.getNombreCliente());
            clienteGrillaUI.setApellidoCliente(clienteDTO.getApellidoCliente());
            clienteGrillaUI.setMailCliente(clienteDTO.getMailCliente());
            
            clienteGrillaUI.setFechaHoraBajaCliente(clienteDTO.getFechaHoraBajaCliente());
            clientesGrilla.add(clienteGrillaUI);
        }
        return clientesGrilla;
    }

    public String irAgregarCliente() {
        BeansUtils.guardarUrlAnterior();
        return "abmCliente?faces-redirect=true&dni=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    
    public String irModificarCliente(int dni) {
        BeansUtils.guardarUrlAnterior();
        return "abmCliente?faces-redirect=true&dni=" + dni; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaCliente(int dni){
        try {
            controladorABMCliente.darDeBajaCliente(dni);
            Messages.create("Anulado").detail("Anulado").add();;
                    
        } catch (ClienteException e) {
            Messages.create("Error!").error().detail("AdminFaces Error message.").add();
            Messages.create("Error!").error().detail("No se puede dar de baja, el cliente tiene asignado al menos un tramite.").add();
        }
    }
    
}
