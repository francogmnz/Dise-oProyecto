package entidades;

import java.sql.Timestamp;

public class TramiteEstadoTramite extends Entidad {

    private Timestamp fechaDesdeTET;
    private Timestamp fechaHastaTET;
    private EstadoTramite estadoTramite;
    private int contadorTET;

    public TramiteEstadoTramite() {
    }

    public int getContadorTET() {
        return contadorTET;
    }

    public void setContadorTET(int contadorTET) {
        this.contadorTET = contadorTET;
    }

    public Timestamp getFechaDesdeTET() {
        return fechaDesdeTET;
    }

    public void setFechaDesdeTET(Timestamp fechaDesdeTET) {
        this.fechaDesdeTET = fechaDesdeTET;
    }

    public Timestamp getFechaHastaTET() {
        return fechaHastaTET;
    }

    public void setFechaHastaTET(Timestamp fechaHastaTET) {
        this.fechaHastaTET = fechaHastaTET;
    }

    public EstadoTramite getEstadoTramite() {
        return estadoTramite;
    }

    public void setEstadoTramite(EstadoTramite estadoTramite) {
        this.estadoTramite = estadoTramite;
    }

}
