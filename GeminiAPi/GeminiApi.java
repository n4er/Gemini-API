package GeminiAPi;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiApi {

    public static void main(String[] args) throws Exception {
        String apiKey = "MY-API-KEY";
        String urlStr = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey;

        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter your query (press enter to continue): \n");
        String query = userInput.nextLine();
        String json = "{\"contents\":[{\"parts\":[{\"text\":\"" + query + "\"}]}]}";

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        OutputStream os = con.getOutputStream();
        os.write(json.getBytes());
        os.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }
        br.close();

        // Parse the response JSON
        JSONObject jsonResponse = new JSONObject(responseBuilder.toString());
        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        if (candidates.length() > 0) {
            JSONObject firstCandidate = candidates.getJSONObject(0);
            JSONArray parts = firstCandidate.getJSONObject("content").getJSONArray("parts");
            for (int i = 0; i < parts.length(); i++) {
                System.out.println(parts.getJSONObject(i).getString("text"));
            }
        } else {
            System.out.println("No response content.");
        }
    }
}
