package org.userbot;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LeadClassifierClient {

    private final String serverUrl;

    public LeadClassifierClient() {
        this.serverUrl = ConfigStore.getConfig().getFlaskServerUrl();
    }

    public double isLeadCheck(String text) throws IOException {
        URL url = new URL(serverUrl + "/predict");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        // Создаем JSON с текстом
        JSONObject json = new JSONObject();
        json.put("text", text);

        // Отправляем тело запроса
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Получаем ответ
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line.trim());
            }
        }

        // Парсим JSON ответ
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse.getDouble("probability");

    }
}
