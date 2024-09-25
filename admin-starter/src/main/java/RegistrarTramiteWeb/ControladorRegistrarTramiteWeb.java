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
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author licciardi
 */
@Named
@SessionScoped
public class ControladorRegistrarTramiteWeb implements Serializable{
    
    private ExpertoRegistrarTramiteWeb expertoRegistrarTramiteWeb = new ExpertoRegistrarTramiteWeb();


    public DTOCliente buscarClienteIngresado(int dniCliente) throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.buscarClienteIngresado(dniCliente);
    }
    
    
    public List<DTOCategoriaTipoTramite> listarCategoriasTipoTramite() throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.listarCategoriasTipoTramtite();
    }

    public List<DTOTipoTramite> listarTipoTramites(int codCategoriaTipoTramite) throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.listarTipoTramites(codCategoriaTipoTramite);
    }

    public DTOResumen mostrarResumenTipoTramite(int codTipoTramite) throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.mostrarResumenTipoTramite(codTipoTramite);
    }
 
    public DTONumeroTramite registrarTramite() throws RegistrarTramiteWebException {
        return expertoRegistrarTramiteWeb.registrarTramite();
    }   
    
    public void resetearEstado() {
        expertoRegistrarTramiteWeb.resetearEstado();
    }
}
