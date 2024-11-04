
package ABMAgenda.beans;

import java.sql.Timestamp;

public class SemanaIU {
    private int nroSemana;
    private Timestamp fecha;
    private int mes;
    private int anio;

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
}
