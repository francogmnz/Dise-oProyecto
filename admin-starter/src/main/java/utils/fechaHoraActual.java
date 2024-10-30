package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.primefaces.shaded.json.JSONObject;

public class fechaHoraActual {

    public static Timestamp obtenerFechaHoraActual() {
        String url = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Mendoza";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parseamos la respuesta JSON para extraer la fecha y hora
            JSONObject jsonResponse = new JSONObject(response.body());
            String dateTimeString = jsonResponse.getString("dateTime");

            // Convertimos el String a LocalDateTime
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);

            // Convertimos LocalDateTime a Timestamp
            return Timestamp.valueOf(localDateTime);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}