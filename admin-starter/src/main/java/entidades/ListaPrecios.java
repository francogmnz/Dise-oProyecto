package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class ListaPrecios extends Entidad {
    
    
    //Lo mismo ver si usar Date o timestamp
    private int codListaPrecios;
    private Timestamp fechaHoraBajaListaPrecios;
    private Timestamp fechaHoraDesdeListaPrecios;
    private Timestamp fechaHoraHastaListaPrecios;
    private List<TipoTramiteListaPrecios> tipoTramiteListaPrecios = new ArrayList<>();  // Relación OneToMany con TipoTramiteListaPrecios

    public ListaPrecios() {
    }

    public int getCodListaPrecios() {
        return codListaPrecios;
    }

    public void setCodListaPrecios(int codListaPrecios) {
        this.codListaPrecios = codListaPrecios;
    }

    public Timestamp getFechaHoraBajaListaPrecios() {
        return fechaHoraBajaListaPrecios;
    }

    public void setFechaHoraBajaListaPrecios(Timestamp fechaHoraBajaListaPrecios) {
        this.fechaHoraBajaListaPrecios = fechaHoraBajaListaPrecios;
    }

    public Timestamp getFechaHoraDesdeListaPrecios() {
        return fechaHoraDesdeListaPrecios;
    }

    public void setFechaHoraDesdeListaPrecios(Timestamp fechaHoraDesdeListaPrecios) {
        this.fechaHoraDesdeListaPrecios = fechaHoraDesdeListaPrecios;
    }

    public Timestamp getFechaHoraHastaListaPrecios() {
        return fechaHoraHastaListaPrecios;
    }

    public void setFechaHoraHastaListaPrecios(Timestamp fechaHoraHastaListaPrecios) {
        this.fechaHoraHastaListaPrecios = fechaHoraHastaListaPrecios;
    }

    public List<TipoTramiteListaPrecios> getTipoTramiteListaPrecios() {
        return tipoTramiteListaPrecios;
    }

    public void setTipoTramiteListaPrecios(List<TipoTramiteListaPrecios> tipoTramiteListaPrecios) {
        this.tipoTramiteListaPrecios = tipoTramiteListaPrecios;
    }
    
    public void addTipoTramiteListaPrecios(TipoTramiteListaPrecios ttlp) {
        tipoTramiteListaPrecios.add(ttlp);   
}
}
