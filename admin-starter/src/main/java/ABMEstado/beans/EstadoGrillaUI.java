package ABMEstado.beans;

import java.sql.Timestamp;

// Clase usada para mostrar los estados en la grilla al iniciar el CU ABMEstados
public class EstadoGrillaUI {
    private int codigo;
    private String nombre;
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Timestamp getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}
