package ABMAgenda.beans;

import utils.BeansUtils;
import ABMAgenda.ControladorABMAgenda;
import ABMAgenda.dtos.AgendaDTO;
import ABMAgenda.exceptions.AgendaException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.omnifaces.util.Messages;


@Named("uiabmAgendaLista")
@ViewScoped
public class UIABMAgendaLista implements Serializable {

    private ControladorABMAgenda controladorABMAgenda = new ControladorABMAgenda();
    private int anio;
    private int mes;
    private List<Integer> listaAnios;
    private boolean agendaEncontrada;
    private String primerDiaMes;
    private String ultimoDiaMes;

   
    public UIABMAgendaLista() {
        // Establece el mes y año actuales como valores por defecto si no están ingresados
        Calendar calendar = Calendar.getInstance();
        this.mes = calendar.get(Calendar.MONTH) + 1; // +1 porque MONTH es cero
        this.anio = calendar.get(Calendar.YEAR);
    }
    public List<Integer> getListaAnios() {
        if (listaAnios == null) {
            listaAnios = new ArrayList<>();
            for (int i = 2020; i <= 2120; i++) {
                listaAnios.add(i);
            }
        }
        return listaAnios;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public boolean isAgendaEncontrada() {
        return agendaEncontrada;
    }

    public void setAgendaEncontrada(boolean agendaEncontrada) {
        this.agendaEncontrada = agendaEncontrada;
    }

    public String getPrimerDiaMes() {
        return primerDiaMes;
    }

    public void setPrimerDiaMes(String primerDiaMes) {
        this.primerDiaMes = primerDiaMes;
    }

    public String getUltimoDiaMes() {
        return ultimoDiaMes;
    }

    public void setUltimoDiaMes(String ultimoDiaMes) {
        this.ultimoDiaMes = ultimoDiaMes;
    }
    
    
    public String getMesNombre() {
        Map<Integer, String> meses = new HashMap<>();
        meses.put(1, "Enero");
        meses.put(2, "Febrero");
        meses.put(3, "Marzo");
        meses.put(4, "Abril");
        meses.put(5, "Mayo");
        meses.put(6, "Junio");
        meses.put(7, "Julio");
        meses.put(8, "Agosto");
        meses.put(9, "Septiembre");
        meses.put(10, "Octubre");
        meses.put(11, "Noviembre");
        meses.put(12, "Diciembre");

        return meses.get(mes); // Devuelve el nombre del mes correspondiente
    }
    
    public void buscarAgenda() {
        agendaEncontrada = controladorABMAgenda.existeAgendaParaMesYAnio(mes, anio);

        if (agendaEncontrada) {
            // Obtener el primer y último día del mes desde el experto
            Map<String, Date> fechas = controladorABMAgenda.obtenerPrimerYUltimoDiaDelMes(mes, anio);

            // Obtener las fechas del mapa
            Date primerDia = fechas.get("primerDia");
            Date ultimoDia = fechas.get("ultimoDia");

            // Formatear las fechas
            primerDiaMes = formatFecha(primerDia);
            ultimoDiaMes = formatFecha(ultimoDia);
        } else {
            primerDiaMes = null;
            ultimoDiaMes = null;
        }
    }
    
    public String irCrearAgenda() {
        return "abmAgenda?faces-redirect=true&mes=" + mes + "&anio=" + anio;
    }

    // Método para formatear fechas en un formato amigable
    private String formatFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fecha);
    }

    // Getters y Setters
    public ControladorABMAgenda getControladorABMAgenda() {
        return controladorABMAgenda;
    }

    public void setControladorABMAgenda(ControladorABMAgenda controladorABMAgenda) {
        this.controladorABMAgenda = controladorABMAgenda;
    }


}
