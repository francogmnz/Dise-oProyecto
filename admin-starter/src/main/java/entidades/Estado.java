package entidades;

import java.sql.Timestamp;

public class Estado extends Entidad {

    private int codigo;
    private String nombre;
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;

    public Estado() {
    }

    public Estado(int i, String estado_1, String descripción_1, Object object, Object object0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

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
