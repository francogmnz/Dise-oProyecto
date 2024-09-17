/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RegistrarTramiteWeb;

import RegistrarTramiteWeb.dtos.DTOCategoriaTipoTramite;
import RegistrarTramiteWeb.dtos.DTOCliente;
import RegistrarTramiteWeb.dtos.DTONumeroTramite;
import RegistrarTramiteWeb.dtos.DTOResumen;
import RegistrarTramiteWeb.dtos.DTOTipoTramite;
import RegistrarTramiteWeb.exceptions.RegistrarTramiteWebException;
import entidades.Tramite;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class ControladorRegistrarTramiteWeb {
    
    private ExpertoRegistrarTramiteWeb expertoRegistrarTramiteWeb = new ExpertoRegistrarTramiteWeb();

/*    
    public DTOCliente buscarClienteIngresado(int dniCliente) {
        return expertoRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
    }
*/
    public DTOCliente buscarClienteIngresado(int dniCliente) throws RegistrarTramiteWebException {
        DTOCliente cliente = expertoRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);

        if (cliente == null) {
            throw new RegistrarTramiteWebException("Cliente no encontrado. Reintentar.");
        }

        return cliente;
    }
    
    
    public List<DTOCategoriaTipoTramite> listarCategoriasTipoTramite() {
        return expertoRegistrarTramiteWeb.listarCategoriasTipoTramtite();
    }

    public List<DTOTipoTramite> listarTipoTramites(int codCategoriaTipoTramite) {
        return expertoRegistrarTramiteWeb.listarTipoTramites(codCategoriaTipoTramite);
    }

    public DTOResumen confirmarTipoTramite(int codTipoTramite) throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.confirmacionTipoTramite(codTipoTramite);
    }

    public DTONumeroTramite registrarTramite(Tramite nuevoTramite) throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.registrarTramite(nuevoTramite);
    }
    
}
