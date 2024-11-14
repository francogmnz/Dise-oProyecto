
package ABMAgenda.beans;

import java.sql.Timestamp;
import java.util.Date;

public class SemanaIU {
    private int nroSemana;
    private Timestamp fecha;
    private int mes;
    private int anio;
    private Date fechaDesdeSemana;
    private Date fechaHastaSemana;

    public int getNroSemana() {
        return nroSemana;
    }

    public void setNroSemana(int nroSemana) {
        this.nroSemana = nroSemana;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechaDesdeSemana() {
        return fechaDesdeSemana;
    }

    public void setFechaDesdeSemana(Date fechaDesdeSemana) {
        this.fechaDesdeSemana = fechaDesdeSemana;
    }

    public Date getFechaHastaSemana() {
        return fechaHastaSemana;
    }

    public void setFechaHastaSemana(Date fechaHastaSemana) {
        this.fechaHastaSemana = fechaHastaSemana;
    }

    
    
    
}
