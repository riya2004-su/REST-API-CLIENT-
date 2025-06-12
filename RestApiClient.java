import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class RestApiClient {
    public static void main(String[] args) {
        try {
            // Replace this with your actual API Key from OpenWeatherMap
            String apiKey = "YOUR_API_KEY"; // Replace YOUR_API_KEY
            String city = "London";
            String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out.println("HTTP Error: " + responseCode);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json = new JSONObject(response.toString());
                String weather = json.getJSONArray("weather").getJSONObject(0).getString("description");
                JSONObject main = json.getJSONObject("main");
                double temperature = main.getDouble("temp");
                int humidity = main.getInt("humidity");

                System.out.println("Weather Data for " + city + ":");
                System.out.println("Description: " + weather);
                System.out.println("Temperature: " + temperature + "°C");
                System.out.println("Humidity: " + humidity + "%");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
