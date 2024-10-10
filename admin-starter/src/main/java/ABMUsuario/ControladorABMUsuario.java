package ABMUsuario;

import ABMUsuario.dtos.NuevoUsuarioDTO;
import ABMUsuario.dtos.UsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;

public class ControladorABMUsuario {

    private ExpertoABMUsuario expertoABMUsuario = new ExpertoABMUsuario();

    public void registrarUsuario(NuevoUsuarioDTO nuevoUsuarioDTO) throws UsuarioException {
        expertoABMUsuario.registrarUsuario(nuevoUsuarioDTO);
    }

    public UsuarioDTO iniciarSesion(String username, String password) throws UsuarioException {
        return expertoABMUsuario.iniciarSesion(username, password);
    }

    public boolean existeCliente(int dni) throws UsuarioException {
       return expertoABMUsuario.existeCliente(dni);
    }
}
