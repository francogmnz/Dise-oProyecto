package utils;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.primefaces.shaded.json.JSONObject;

@Named
@ViewScoped
public class fechaHoraActual implements Serializable {

    private static Timestamp cachedTimestamp = null;
    private static long lastFetchTime = 0;
    private static final long CACHE_DURATION = 60000; // 60 segundos (60000 ms)

    public static Timestamp obtenerFechaHoraActual() {
        long currentTime = System.currentTimeMillis();

        // Verificar si el cache es válido
        if (cachedTimestamp != null && (currentTime - lastFetchTime < CACHE_DURATION)) {
            return cachedTimestamp; // Devolver el timestamp cacheado
        }

        String url = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Mendoza";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Comprobamos el estado de la respuesta HTTP
            if (response.statusCode() != 200) {
                throw new Exception("Error al obtener la fecha y hora: código de estado " + response.statusCode());
            }

            // Parseamos la respuesta JSON para extraer la fecha y hora
            JSONObject jsonResponse = new JSONObject(response.body());
            String dateTimeString = jsonResponse.getString("dateTime");

            // Convertimos el String a LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);

            // Convertimos LocalDateTime a Timestamp
            cachedTimestamp = Timestamp.valueOf(localDateTime);
            lastFetchTime = currentTime; // Actualizar el tiempo de última obtención

            return cachedTimestamp;

        } catch (Exception e) {
            e.printStackTrace();
            // Redirigir a la página de error y retornar un valor predeterminado
            redirigirPaginaDeError();
            return Timestamp.valueOf("1000-1-1 00:00:00"); // Valor predeterminado que evita el NullPointerException
        }
    }

    // Método de redirección usando un string de navegación
    private static void redirigirPaginaDeError() {
        try {
            String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(contextPath + "/pagina_error.xhtml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
