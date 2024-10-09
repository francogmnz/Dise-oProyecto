package RegistrarTramite;

import RegistrarTramite.dtos.DTOCliente;
import RegistrarTramite.dtos.DTOEstadoTramite;
import RegistrarTramite.dtos.DTOFile;
import RegistrarTramite.dtos.DTOTipoTramite;
import RegistrarTramite.dtos.DTOTramiteElegido;
import RegistrarTramite.dtos.DTOTramite;
import RegistrarTramite.exceptions.RegistrarTramiteException;
import java.util.Date;
import java.util.List;

public class ControladorRegistrarTramite {

    private ExpertoRegistrarTramite expertoRegistrarTramite = new ExpertoRegistrarTramite();

    public void eliminarDocumentacion(int codTD, int nroTramite) throws Exception {
        expertoRegistrarTramite.eliminarDocumentacion(codTD, nroTramite);
    }

    
    public List<DTOEstadoTramite> mostrarComboEstados() {
        return expertoRegistrarTramite.mostrarComboEstados();
    }

    public List<DTOTramite> mostrarTramites(int nroTramite, Date fechaRecepcionTramite, int dniCliente, int codTipoTramite, String nombreEstadoTramite) {
        return expertoRegistrarTramite.mostrarTramites(nroTramite, fechaRecepcionTramite, dniCliente, codTipoTramite, nombreEstadoTramite);
    }

    public DTOCliente obtenerCliente(int dni) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerCliente(dni);
    }

    public DTOTipoTramite obtenerTipoTramite(int codTipoTramite) throws RegistrarTramiteException {
        return expertoRegistrarTramite.obtenerTipoTramite(codTipoTramite);
    }

    public void registrarTramite() throws RegistrarTramiteException {
        expertoRegistrarTramite.registrarTramite();
    }

    public DTOTramiteElegido mostrarResumenTramite(int nroTramite) {
        return expertoRegistrarTramite.mostrarResumenTramite(nroTramite);
    }

    public void anularTramite(int nroTramite) throws RegistrarTramiteException {
        expertoRegistrarTramite.anularTramite(nroTramite);
    }

    public List<DTOTipoTramite> buscarTipoTramite(int codTipoTramite, String nombreTipoTramite, String nombreCategoria, String descTipoTramite) {
        return expertoRegistrarTramite.buscarTipoTramite(codTipoTramite, nombreTipoTramite, nombreCategoria, descTipoTramite);
    }
    
    public void registrarDocumentacion(int codTD, DTOFile archivoTD, int nroTramite){
        expertoRegistrarTramite.registrarDocumentacion(codTD, archivoTD, nroTramite);
    }
    
}
