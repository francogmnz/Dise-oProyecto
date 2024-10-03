
package ABMCliente.beans;


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
    private String apellidoFiltro="";
    private String mailFiltro="";

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

    public void setNombreFiltro(String nombreFiltro) {
        this.nombreFiltro = nombreFiltro;
    }

    public String getApellidoFiltro() {
        return apellidoFiltro;
    }

    public void setApellidoFiltro(String apellidoFiltro) {
        this.apellidoFiltro = apellidoFiltro;
    }

    public String getMailFiltro() {
        return mailFiltro;
    }

    public void setMailFiltro(String mailFiltro) {
        this.mailFiltro = mailFiltro;
    }
   

 
    
     public void filtrar()
    {

    }

    public List<ClienteGrillaUI> buscarClientes(){
        List<ClienteGrillaUI> clientesGrilla = new ArrayList<>();
        List<DTOCliente> clientesDTO = controladorABMCliente.buscarClientes(dniFiltro, nombreFiltro, apellidoFiltro, mailFiltro);
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
            Messages.create("Error!").error().detail("No se puede dar de baja, el cliente tiene asignado al menos un tramite.").add();
        }
    }
    
    //    DEVUELVE TRUE SI LA LISTA DE PRECIOS ESTA ANULADA
    public boolean isAnulada(ClienteGrillaUI clienteEnviado) {
        if (clienteEnviado.getFechaHoraBajaCliente() != null) {
            return true;
        } else {
            return false;
        }
    }
}
