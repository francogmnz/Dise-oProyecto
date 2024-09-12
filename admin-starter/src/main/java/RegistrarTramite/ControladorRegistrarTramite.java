package RegistrarTramite;

import RegistrarTramite.dtos.ResumenTramiteDTO;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.TramiteDTO;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.Cliente;
import entidades.TipoTramite;
import java.sql.Timestamp;
import java.util.List;


public class ControladorRegistrarTramite {
    private ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();

    public List<TramiteDTO> buscarTramites(int nroTramite, int dni, Timestamp fechaRecepcionTramite, int codTipoTramite, String nombreEstado) {
        return expertoRegistrarTramite.buscarTramites(nroTramite, dni, fechaRecepcionTramite, codTipoTramite, nombreEstado);
    }

    public ResumenTramiteDTO mostrarResumenTramite(int nroTramite) {
        return expertoRegistrarTramite.mostrarResumenTramite(nroTramite);
    }

    public void anularTramite(int nroTramite) {
        expertoRegistrarTramite.anularTramite(nroTramite);
    }

    public Cliente obtenerCliente(int dni) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerCliente(dni);
    }

    public TipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerTipoTramite(codTipoTramite);
    }

    public void registrarTramite(int dni, int codTipoTramite) throws RegistrarTramiteException {
        expertoRegistrarTramite.registrarTramite(dni, codTipoTramite);
    }

    public List<TipoTramiteResumenDTO> buscarTipoTramite(String nomTipoTramite, String nomCategoria, String descTipoTramite) {
        return expertoRegistrarTramite.buscarTipoTramite(nomTipoTramite, nomCategoria, descTipoTramite);
    }
    
}
