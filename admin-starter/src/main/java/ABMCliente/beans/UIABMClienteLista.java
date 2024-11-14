package ABMCliente.beans;

import ABMCliente.ControladorABMCliente;
import ABMCliente.dtos.DTOCliente;
import ABMCliente.exceptions.ClienteException;
import entidades.Cliente;
import entidades.Tramite;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.omnifaces.util.Messages;
import utils.BeansUtils;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

@Named("uiabmClienteLista")
@ViewScoped

public class UIABMClienteLista implements Serializable {

    private ControladorABMCliente controladorABMCliente = new ControladorABMCliente();
    private int dniFiltro = 0;
    private String nombreFiltro = "";
    private String apellidoFiltro = "";
    private String mailFiltro = "";
    private String criterio = "";
    private int dniSeleccionado = 0;
    

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

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public int getDniSeleccionado() {
        return dniSeleccionado;
    }

    public void setDniSeleccionado(int dniSeleccionado) {
        this.dniSeleccionado = dniSeleccionado;
    }
    

    public void filtrar() {

    }

    public List<ClienteGrillaUI> buscarClientes() {
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
        return filtrarClientes(clientesGrilla);
    }

    public String irAgregarCliente() {
        BeansUtils.guardarUrlAnterior();
        return "abmCliente?faces-redirect=true&dni=0"; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public String irModificarCliente(int dni) {
        setDniSeleccionado(dni);
        BeansUtils.guardarUrlAnterior();
        return "abmCliente?faces-redirect=true&dni=" + dni; // Usa '?faces-redirect=true' para hacer una redirección
    }

    public void darDeBajaCliente(int dni) {
        try {
            controladorABMCliente.darDeBajaCliente(dni);
            Messages.create("Anulado").detail("Anulado").add();;

        } catch (ClienteException e) {
            Messages.create("Error!").error().detail(e.getMessage()).add();
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

    public int buscarTramitesCliente(int dniCliente) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();

        DTOCriterio unCriterio = new DTOCriterio();
        unCriterio.setAtributo("dniCliente");
        unCriterio.setOperacion("=");
        unCriterio.setValor(dniCliente);
        lCriterio.add(unCriterio);

        Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", lCriterio).get(0);
        int dniEncontrado = clienteEncontrado.getDniCliente();

        lCriterio.clear();

        unCriterio = new DTOCriterio();

        unCriterio.setAtributo("fechaFinTramite");
        unCriterio.setOperacion("=");
        unCriterio.setValor(null);

        lCriterio.add(unCriterio);

        DTOCriterio unCriterio2 = new DTOCriterio();

        unCriterio2.setAtributo("fechaAnulacionTramite");
        unCriterio2.setOperacion("=");
        unCriterio2.setValor(null);

        lCriterio.add(unCriterio2);

        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", lCriterio);

        int cantidadTramites = 0;

        for (Object x : objetoList) {

            Tramite tramite = (Tramite) x;
            if (tramite.getCliente() != null) {
                Cliente cliente = tramite.getCliente();
                int dniCli = cliente.getDniCliente();

                if (dniEncontrado == dniCli) {
                    cantidadTramites += 1;
                }

            }
        }

        return cantidadTramites;

    }

    public List<ClienteGrillaUI> filtrarClientes(List<ClienteGrillaUI> cliGrilla) {
        List<ClienteGrillaUI> clientesActivos = new ArrayList<>();
        List<ClienteGrillaUI> clientesInactivos = new ArrayList<>();
        for (ClienteGrillaUI cliente : cliGrilla) {
            if (cliente.getFechaHoraBajaCliente() == null) {
                clientesActivos.add(cliente);
            } else {
                clientesInactivos.add(cliente);
            }
        }
        switch (criterio) {
            case "cliActivo":                
                return clientesActivos;
            case "cliInactivo":
                return clientesInactivos;
            default:
                return cliGrilla;
        }
    }

}
