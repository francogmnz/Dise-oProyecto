package InformeTT.dtos;

import entidades.Consultor;
import entidades.TipoTramite;
import entidades.Tramite;
import entidades.Cliente;
import java.sql.Timestamp;

public class TramiteDTO {

    private int nroTramite;
    private double precioTramite;
    private Timestamp fechaFinTramite;
    private Timestamp fechaInicioTramite;
    private Consultor consultor;
    private TipoTramite tipoTramite;
    private int dniCliente;
    private String nombreCliente;
    private String apellidoCliente;

    public int getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(int dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public int getNroTramite() {
        return nroTramite;
    }

    public double getPrecioTramite() {
        return precioTramite;
    }

    public Timestamp getFechaFinTramite() {
        return fechaFinTramite;
    }

    public Timestamp getFechaInicioTramite() {
        return fechaInicioTramite;
    }

    public Consultor getConsultor() {
        return consultor;
    }

    public TipoTramite getTipoTramite() {
        return tipoTramite;
    }

    public void setNroTramite(int nroTramite) {
        this.nroTramite = nroTramite;
    }

    public void setPrecioTramite(double precioTramite) {
        this.precioTramite = precioTramite;
    }

    public void setFechaFinTramite(Timestamp fechaFinTramite) {
        this.fechaFinTramite = fechaFinTramite;
    }

    public void setFechaInicioTramite(Timestamp fechaInicioTramite) {
        this.fechaInicioTramite = fechaInicioTramite;
    }

    public void setConsultor(Consultor consultor) {
        this.consultor = consultor;
    }

    public void setTipoTramite(TipoTramite tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

}
