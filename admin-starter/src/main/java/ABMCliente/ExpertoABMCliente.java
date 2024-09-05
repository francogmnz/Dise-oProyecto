package ABMCliente;

import ABMCliente.*;
import ABMCliente.dtos.DTOCliente;
import ABMCliente.dtos.DTOIngresoDatos;
import ABMCliente.dtos.DTOModificacionDatos;
import ABMCliente.dtos.DTOModificacionDatosIn;
import ABMCliente.exceptions.ClienteException;
import entidades.Cliente;
import entidades.Tramite;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;

public class ExpertoABMCliente {

    public List<DTOCliente> buscarClientes(int dniCliente, String apellidoCliente) {
        List<DTOCriterio> lCriterio = new ArrayList<DTOCriterio>();
        if (dniCliente > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("dniCliente");
            unCriterio.setOperacion("=");
            unCriterio.setValor(dniCliente);
            lCriterio.add(unCriterio);
        }
        if (apellidoCliente.trim().length() > 0) {
            DTOCriterio unCriterio = new DTOCriterio();
            unCriterio.setAtributo("nombreCliente");
            unCriterio.setOperacion("like");
            unCriterio.setValor(apellidoCliente);
            lCriterio.add(unCriterio);
        }
        //if (numMaximoTramites > 0) {

          //  DTOCriterio unCriterio = new DTOCriterio();
            //unCriterio.setAtributo("numMaximoTramites");
            //unCriterio.setOperacion("=");
            //unCriterio.setValor(numMaximoTramites);
            //lCriterio.add(unCriterio);

        //}
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

    public DTOModificacionDatos buscarClienteAModificar(int dniCliente) {
        List<DTOCriterio> criterioList = new ArrayList<>();
        DTOCriterio dto = new DTOCriterio();

        dto.setAtributo("dniCliente");
        dto.setOperacion("=");
        dto.setValor(dniCliente);
        

        criterioList.add(dto);

        Cliente clienteEncontrado = (Cliente) FachadaPersistencia.getInstance().buscar("Cliente", criterioList).get(0);

        DTOModificacionDatos dtoModificacionDatos = new DTOModificacionDatos();
        dtoModificacionDatos.setNombreCliente(clienteEncontrado.getNombreCliente());
        dtoModificacionDatos.setDniCliente(clienteEncontrado.getDniCliente());
        dtoModificacionDatos.setApellidoCliente(clienteEncontrado.getApellidoCliente());
        dtoModificacionDatos.setMailCliente(clienteEncontrado.getMailCliente());
        return dtoModificacionDatos;
    }

    public void modificarCliente(DTOModificacionDatosIn dtoModificacionDatosIn) {
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

        List objetoList = FachadaPersistencia.getInstance().buscar("Tramite", criterioList);

        for (Object x : objetoList) {

            Tramite tramite = (Tramite) x;
            Cliente cliente = tramite.getCliente();
            int dni = cliente.getDniCliente();

            if (dniEncontrado == dni) {
                throw new ClienteException("Cliente no puede darse de baja por estar asignado en al menos a un tramite");
            }

        }

        clienteEncontrado.setFechaHoraBajaCliente(new Timestamp(System.currentTimeMillis()));

        FachadaPersistencia.getInstance().guardar(clienteEncontrado);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }
}
