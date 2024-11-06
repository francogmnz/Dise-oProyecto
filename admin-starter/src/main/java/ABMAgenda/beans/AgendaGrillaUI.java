package ABMAgenda.beans;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.time.format.TextStyle;
import java.util.Locale;

public class AgendaGrillaUI {
    private int codigo;
    private String nombre;
    private int semanaDelAnio; // Se calcula automáticamente
    private Timestamp fechaAlta;
    private Timestamp fechaBaja;

    public AgendaGrillaUI() {
    }

    public AgendaGrillaUI(int codigo, String nombre, Timestamp fechaAlta, Timestamp fechaBaja) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.semanaDelAnio = calcularSemanaDelAnio(fechaAlta); 
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


    public int getSemanaDelAnio() {
        return semanaDelAnio;
    }

    // Getter y Setter para 'fechaAlta'
    public Timestamp getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Timestamp fechaAlta) {
        this.fechaAlta = fechaAlta;
        this.semanaDelAnio = calcularSemanaDelAnio(fechaAlta);  // Recalcula la semana del año cuando se cambia la fecha
    }

  
    public Timestamp getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Timestamp fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    // Método privado para calcular la semana del año a partir de una fecha
    private int calcularSemanaDelAnio(Timestamp fecha) {
        if (fecha == null) {
            return 0;  // Si no hay fecha, devuelve 0
        }
        LocalDate localDate = fecha.toLocalDateTime().toLocalDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return localDate.get(weekFields.weekOfWeekBasedYear());
    }
        public String getMes() {
        if (fechaAlta != null) {
            LocalDate localDate = fechaAlta.toLocalDateTime().toLocalDate();
            return localDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es"));
        }
        return "Sin fecha";
    }
}
