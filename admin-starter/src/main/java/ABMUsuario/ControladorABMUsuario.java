/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMUsuario;

import ABMUsuario.dtos.NuevoUsuarioDTO;
import ABMUsuario.dtos.UsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;

/**
 *
 * @author licciardi
 */
public class ControladorABMUsuario {
    private ExpertoABMUsuario expertoABMUsuario = new ExpertoABMUsuario();

    public void registrarUsuario(NuevoUsuarioDTO nuevoUsuarioDTO) throws UsuarioException {
        expertoABMUsuario.registrarUsuario(nuevoUsuarioDTO);
    }

    public UsuarioDTO iniciarSesion(String username, String password) throws UsuarioException {
        return expertoABMUsuario.iniciarSesion(username, password);
    }
}
