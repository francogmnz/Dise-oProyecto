
package ABMCliente;

import ABMCliente.dtos.DTOCliente;
import ABMCliente.dtos.DTOIngresoDatos;
import ABMCliente.dtos.DTOModificacionDatos;
import ABMCliente.dtos.DTOModificacionDatosIn;
import ABMCliente.exceptions.ClienteException;
import java.io.IOException;
import java.util.List;



public class ControladorABMCliente {
    private ExpertoABMCliente expertoABMCliente = new ExpertoABMCliente();

    public List<DTOCliente> buscarClientes(int dni, String nombre, String apellido, String mail){
        return expertoABMCliente.buscarClientes(dni, nombre, apellido, mail);
    }

    public void agregarCliente(DTOIngresoDatos dtoIngresoDatos) throws ClienteException{
        expertoABMCliente.agregarCliente(dtoIngresoDatos);
    }

    public void modificarCliente(DTOModificacionDatosIn dtoModificacionDatosIn) throws ClienteException, IOException{
        expertoABMCliente.modificarCliente(dtoModificacionDatosIn);
    }

    public DTOModificacionDatos buscarClienteAModificar(int dni) throws IOException, ClienteException{
        return expertoABMCliente.buscarClienteAModificar(dni);
    }

    public void darDeBajaCliente(int dni) throws ClienteException {
        expertoABMCliente.darDeBajaCliente(dni);
    }
}
