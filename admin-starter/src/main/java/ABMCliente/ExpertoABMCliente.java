package ABMCliente;

import ABMCliente.dtos.DTOCliente;
import ABMCliente.dtos.DTOIngresoDatos;
import ABMCliente.dtos.DTOModificacionDatos;
import ABMCliente.dtos.DTOModificacionDatosIn;
import ABMCliente.exceptions.ClienteException;
import entidades.Cliente;
import entidades.Tramite;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMCliente {

    public List<DTOCliente> buscarClientes(int dniCliente, String nombreCliente, String apellidoCliente, String mailCliente) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        if (dniCliente > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("dniCliente");
            unCriterio.setOperacion("=");
            unCriterio.setValor(dniCliente);
            lCriterio.add(unCriterio);
        }
        if (nombreCliente.trim().length() > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("nombreCliente");
            unCriterio.setOperacion("like");
            unCriterio.setValor(nombreCliente);
            lCriterio.add(unCriterio);
        }
        if (apellidoCliente.trim().length() > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("apellidoCliente");
            unCriterio.setOperacion("like");
            unCriterio.setValor(apellidoCliente);
            lCriterio.add(unCriterio);
        }
        if (mailCliente.trim().length() > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("mailCliente");
            unCriterio.setOperacion("like");
            unCriterio.setValor(mailCliente);
            lCriterio.add(unCriterio);
        }

        List objetoList = FachadaPersistencia.getInstance().buscar("Cliente", lCriterio);
        List<DTOCliente> clientesResultado = new ArrayList<>();
        for (Object x : objetoList) {
            Cliente cliente = (Cliente) x;
            DTOCliente dtoCliente = new DTOCliente();
            dtoCliente.setDniCliente(cliente.getDniCliente());
            dtoCliente.setNombreCliente(cliente.getNombreCliente());
            dtoCliente.setApellidoCliente(cliente.getApellidoCliente());
            dtoCliente.setMailCliente(cliente.getMailCliente());
            dtoCliente.setFechaHoraBajaCliente(cliente.getFechaHoraBajaCliente());
            clientesResultado.add(dtoCliente);
        }
        return clientesResultado;
    }

    public void agregarCliente(DTOIngresoDatos nuevoClienteDTO) throws ClienteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("dniCliente");
        dto.setOperacion("=");
        dto.setValor(nuevoClienteDTO.getDniCliente());

        criterioList.add(dto);
        List lCliente = FachadaPersistencia.getInstance().buscar("Cliente", criterioList);

        if (lCliente.size() > 0) {
            throw new ClienteException("El DNI de cliente ya existe");
        } else {
            Cliente cliente = new Cliente();
            cliente.setDniCliente(nuevoClienteDTO.getDniCliente());
            cliente.setNombreCliente(nuevoClienteDTO.getNombreCliente());
            cliente.setApellidoCliente(nuevoClienteDTO.getApellidoCliente());
            cliente.setMailCliente(nuevoClienteDTO.getMailCliente());

            FachadaPersistencia.getInstance().guardar(cliente);
            FachadaPersistencia.getInstance().finalizarTransaccion();
        }
    }

    public DTOModificacionDatos buscarClienteAModificar(int dniCliente) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();
        DTOCriterio dto2 = new DTOCriterio();

        dto.setAtributo("dniCliente");
        dto.setOperacion("=");
        dto.setValor(dniCliente);

        criterioList.add(dto);
        
        dto2.setAtributo("fechaHoraBajaCliente");
        dto2.setOperacion("=");
        dto2.setValor(null);

        criterioList.add(dto2);
        DTOModificacionDatos dtoModificacionDatos = new DTOModificacionDatos();
        try {

            Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);
            if (clienteEncontrado == null) {
                externalContext.redirect(externalContext.getRequestContextPath() + "/ABMCliente/abmClienteLista.jsf");
            }
            dtoModificacionDatos.setNombreCliente(clienteEncontrado.getNombreCliente());
            dtoModificacionDatos.setDniCliente(clienteEncontrado.getDniCliente());
            dtoModificacionDatos.setApellidoCliente(clienteEncontrado.getApellidoCliente());
            dtoModificacionDatos.setMailCliente(clienteEncontrado.getMailCliente());
            return dtoModificacionDatos;
        } catch (Exception e) {
            externalContext.redirect(externalContext.getRequestContextPath() + "/ABMCliente/abmClienteLista.jsf");
        }
        return dtoModificacionDatos;
    }

    public void modificarCliente(DTOModificacionDatosIn dtoModificacionDatosIn) throws ClienteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("dniCliente");
        dto.setOperacion("=");
        dto.setValor(dtoModificacionDatosIn.getDniCliente());

        criterioList.add(dto);
        Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);
        clienteEncontrado.setDniCliente(dtoModificacionDatosIn.getDniCliente());
        clienteEncontrado.setNombreCliente(dtoModificacionDatosIn.getNombreCliente());
        clienteEncontrado.setApellidoCliente(dtoModificacionDatosIn.getApellidoCliente());
        clienteEncontrado.setMailCliente(dtoModificacionDatosIn.getMailCliente());

        FachadaPersistencia.getInstance().guardar(clienteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public void darDeBajaCliente(int dniCliente) throws ClienteException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("dniCliente");
        dto.setOperacion("=");
        dto.setValor(dniCliente);

        criterioList.add(dto);
        DTOCriterio dto2 = new DTOCriterio();

        dto2.setAtributo("fechaHoraBajaCliente");
        dto2.setOperacion("=");
        dto2.setValor(null);

        criterioList.add(dto2);
        Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);
        int dniEncontrado = clienteEncontrado.getDniCliente();

        criterioList.clear();

        dto = new DTOCriterio();

        dto.setAtributo("fechaFinTramite");
        dto.setOperacion("!=");
        dto.setValor(null);

        criterioList.add(dto);
        
        dto2.setAtributo("fechaAnulacionTramite");
        dto2.setOperacion("!=");
        dto2.setValor(null);

        criterioList.add(dto2);

        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        for (Object x : objetoList) {

            Tramite tramite = (Tramite) x;
            if (tramite.getCliente()!= null){
            Cliente cliente = tramite.getCliente();
            int dni = cliente.getDniCliente();

            if (dniEncontrado == dni) {
                throw new ClienteException("Cliente no puede darse de baja por estar asignado en al menos a un tramite");
            }
            }
        }

        clienteEncontrado.setFechaHoraBajaCliente(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(clienteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
}
