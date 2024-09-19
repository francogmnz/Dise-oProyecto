package RegistrarTramite;

import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.dtos.TipoTramiteResumenDTO;
import RegistrarTramite.dtos.DTOTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import entidades.Cliente;
import entidades.TipoTramite;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


public class ControladorRegistrarTramite {
    
    private ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();

    public List<DTOTramite> mostrarTramites(int nroTramite, Date fechaRecepcionTramite, int dniCliente, int codTipoTramite, String nombreEstadoTramite) {
        return expertoRegistrarTramite.mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, nombreEstadoTramite);
    }

    public DTOTramiteElegido mostrarResumenTramite(int nroTramite) {
        return expertoRegistrarTramite.mostrarResumenTramite(nroTramite);
    }

    public void anularTramite(int nroTramite) {
        expertoRegistrarTramite.anularTramite(nroTramite);
    }

    public DTOCliente obtenerCliente(int dni) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerCliente(dni);
    }

    public TipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerTipoTramite(codTipoTramite);
    }

    public void registrarTramite(int dni, int codTipoTramite) throws RegistrarTramiteException {
        expertoRegistrarTramite.registrarTramite(dni, codTipoTramite);
    }

    public List<TipoTramiteResumenDTO> buscarTipoTramite(int codTipoTramite, String nomTipoTramite, String nomCategoria, String descTipoTramite) {
        return expertoRegistrarTramite.buscarTipoTramite(codTipoTramite, nomTipoTramite, nomCategoria, descTipoTramite);
    }

    public List<DTOEstadoTramite> mostrarComboEstados() {
        return expertoRegistrarTramite.mostrarComboEstados();
    }
    
    
}
