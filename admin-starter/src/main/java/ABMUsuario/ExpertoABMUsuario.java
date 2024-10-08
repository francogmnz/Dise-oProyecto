/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ABMUsuario;

import ABMUsuario.dtos.NuevoUsuarioDTO;
import ABMUsuario.dtos.UsuarioDTO;
import ABMUsuario.exceptions.UsuarioException;
import entidades.Rol;
import entidades.Usuario;
import java.util.ArrayList;
import java.util.List;
import utils.DTOCriterio;
import utils.FachadaPersistencia;
import utils.PasswordUtils;

/**
 *
 * @author licciardi
 */
public class ExpertoABMUsuario {

    public void registrarUsuario(NuevoUsuarioDTO nuevoUsuarioDTO) throws UsuarioException {
        FachadaPersistencia.getInstance().iniciarTransaccion();

        // Verificar si el usuario ya existe
        List<DTOCriterio> criterios = new ArrayList<>();
        DTOCriterio criterio = new DTOCriterio();
        criterio.setAtributo("username");
        criterio.setOperacion("=");
        criterio.setValor(nuevoUsuarioDTO.getUsername());
        criterios.add(criterio);

        List usuarios = FachadaPersistencia.getInstance().buscar("Usuario", criterios);

        if (!usuarios.isEmpty()) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new UsuarioException("El nombre de usuario ya existe");
        }

        // Buscar el rol
        criterios.clear();
        DTOCriterio criterioRol = new DTOCriterio();
        criterioRol.setAtributo("nombreRol");
        criterioRol.setOperacion("="); 
        criterioRol.setValor(nuevoUsuarioDTO.getRolNombre());
        criterios.add(criterioRol); 
        List roles = FachadaPersistencia.getInstance().buscar("Rol", criterios);

        if (roles.isEmpty()) {
            FachadaPersistencia.getInstance().finalizarTransaccion();
            throw new UsuarioException("El rol especificado no existe");
        }


        Rol rol = (Rol) roles.get(0);
        System.out.println("TENGO EL ROL" + rol);
        
        
        Usuario usuario = new Usuario();
        usuario.setUsername(nuevoUsuarioDTO.getUsername());
        usuario.setPassword(PasswordUtils.hashPassword(nuevoUsuarioDTO.getPassword()));
        usuario.setRol(rol);
        System.out.println("TENGO EL ROL seteado" + rol);

        // Actualizar la lista de usuarios en el rol
        if (rol.getListaUsuarios() == null) {
            rol.setListaUsuarios(new ArrayList<>());
        }
        rol.getListaUsuarios().add(usuario);

        FachadaPersistencia.getInstance().guardar(usuario);
        FachadaPersistencia.getInstance().guardar(rol);
        FachadaPersistencia.getInstance().finalizarTransaccion();
    }

    public UsuarioDTO iniciarSesion(String username, String password) throws UsuarioException {
        List<DTOCriterio> criterios = new ArrayList<>();
        DTOCriterio criterio = new DTOCriterio();
        criterio.setAtributo("username");
        criterio.setOperacion("=");
        criterio.setValor(username);
        criterios.add(criterio);

        List usuarios = FachadaPersistencia.getInstance().buscar("Usuario", criterios);

        if (usuarios.isEmpty()) {
            throw new UsuarioException("Usuario no encontrado");
        }

        Usuario usuario = (Usuario) usuarios.get(0);

        if (usuario.getFechaHoraBajaUsuario() != null) {
            throw new UsuarioException("El usuario está dado de baja");
        }

        if (!PasswordUtils.verifyPassword(password, usuario.getPassword())) {
            throw new UsuarioException("Contraseña incorrecta");
        }

        // Crear y devolver el UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername(usuario.getUsername());
        usuarioDTO.setRolNombre(usuario.getRol().getNombreRol());

        return usuarioDTO;
    }
}

