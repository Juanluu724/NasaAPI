package com.example.nasaapodapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Clase que consume la API APOD de la NASA
 */
public class NasaApodClient {

    private static final String NASA_APOD_API_URL =
            "https://api.nasa.gov/planetary/apod";

    // 🔴 PON TU API KEY REAL AQUÍ
    private static final String API_KEY = "IhobDnSBZ2CnJ9GdLPlvWuHJ9hOcTECaKGylSLqF";

    /**
     * Realiza la llamada HTTP a la API NASA
     */
    public JSONObject getApodData() throws IOException, JSONException {

        String randomDate = getRandomDate();

        String urlWithParams = NASA_APOD_API_URL
                + "?api_key=" + API_KEY
                + "&date=" + randomDate;

        URL url = new URL(urlWithParams);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Error en la solicitud: " + responseCode);
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }

    /**
     * Genera una fecha aleatoria dentro de los últimos 10 años
     */
    private String getRandomDate() {

        Calendar calendar = Calendar.getInstance();
        Random random = new Random();

        int daysBack = random.nextInt(365 * 10);
        calendar.add(Calendar.DAY_OF_YEAR, -daysBack);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }
}
