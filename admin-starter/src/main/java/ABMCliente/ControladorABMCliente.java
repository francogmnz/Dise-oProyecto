
package ABMCliente;

import ABMCliente.*;
import ABMCliente.dtos.DTOCliente;
import ABMCliente.dtos.DTOIngresoDatos;
import ABMCliente.dtos.DTOModificacionDatos;
import ABMCliente.dtos.DTOModificacionDatosIn;
import ABMCliente.exceptions.ClienteException;
import java.util.List;



public class ControladorABMCliente {
    private ExpertoABMCliente expertoABMCliente = new ExpertoABMCliente();
    public List<DTOCliente> buscarClientes(int dni, String apellido){
        return expertoABMCliente.buscarClientes(dni,apellido);
    }

    public void agregarCliente(DTOIngresoDatos dtoIngresoDatos) throws ClienteException{
        expertoABMCliente.agregarCliente(dtoIngresoDatos);
    }

    public void modificarCliente(DTOModificacionDatosIn dtoModificacionDatosIn){
        expertoABMCliente.modificarCliente(dtoModificacionDatosIn);
    }

    public DTOModificacionDatos buscarClienteAModificar(int dni){
        return expertoABMCliente.buscarClienteAModificar(dni);
    }

    public void darDeBajaCliente(int dni) throws ClienteException {
        expertoABMCliente.darDeBajaCliente(dni);
    }
}
