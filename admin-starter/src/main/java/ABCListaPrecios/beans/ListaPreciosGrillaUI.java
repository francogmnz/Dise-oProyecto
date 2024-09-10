
package ABCListaPrecios.beans;

import java.sql.Timestamp;


public class ListaPreciosGrillaUI {
    private int codListaPrecios;
    private Timestamp fechaHoraBajaListaPrecios;
    private Timestamp fechaHoraDesdeListaPrecios;
    private Timestamp fechaHoraHastaListaPrecios;

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
    
    
}
