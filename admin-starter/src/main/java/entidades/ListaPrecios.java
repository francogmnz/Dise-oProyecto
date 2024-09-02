/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author licciardi
 */
public class ListaPrecios extends Entidad {
    
    
    //Lo mismo ver si usar Date o timestamp
    private int codListaPrecios;
    private Timestamp fechaHoraBajaListaPrecios;
    private Date fechaHoraDesdeListaPrecios;
    private Date fechaHoraHastaListaPrecios;
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

    public Date getFechaHoraDesdeListaPrecios() {
        return fechaHoraDesdeListaPrecios;
    }

    public void setFechaHoraDesdeListaPrecios(Date fechaHoraDesdeListaPrecios) {
        this.fechaHoraDesdeListaPrecios = fechaHoraDesdeListaPrecios;
    }

    public Date getFechaHoraHastaListaPrecios() {
        return fechaHoraHastaListaPrecios;
    }

    public void setFechaHoraHastaListaPrecios(Date fechaHoraHastaListaPrecios) {
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
