package com.example.myapplication.retrofit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class GetUrl {

    private static final java.util.logging.Logger log =
            java.util.logging.Logger.getLogger(GetUrl.class.getName());
    private static HttpURLConnection conn;

    public static void test() throws JSONException {
        String responseBody = getJson(
                "https://api.data.gov.sg/v1/transport/carpark-availability");
        HashMap<String, String> my_hash_map = parse(responseBody);
        String[] result = getTotalLots(responseBody, my_hash_map, "U29");
        if (result != null) {
            for (String s : result) {
                System.out.println(s);
            }
        }
    }

    /**
     * "https://api.data.gov.sg/v1/transport/carpark-availability"
     */
    public static String getJson(String data_url) {
        BufferedReader reader;
        String line;
        StringBuilder responseContent = new StringBuilder();

        try {
            URL url = new URL(data_url);
            conn = (HttpURLConnection)url.openConnection();

            // Request setup
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000); // 5000 milliseconds = 5 seconds
            conn.setReadTimeout(5000);

            // Test if the response from the server is successful
            int status = conn.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            return responseContent.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            conn.disconnect();
        }
    }

    /**
     * @return HashMap<id, index>
     */
    public static HashMap<String, String> parse(String responseBody) throws JSONException {
        JSONObject albums = new JSONObject(responseBody);
        JSONArray carparks =
                albums.getJSONArray("items").getJSONObject(0).getJSONArray(
                        "carpark_data");

        HashMap<String, String> id_to_index_table =
                new HashMap<String, String>();

        for (int i = 0; i < carparks.length(); i++) {

            id_to_index_table.put(
                    carparks.getJSONObject(i).getString("carpark_number"),
                    //.getJSONArray("carpark_info")
                    String.valueOf(i));
        }

        return id_to_index_table;
    }

    /**
     * @return String[]
     * Stirng[0]: total_lots
     * Stirng[1]: lots_available
     * Stirng[2]: lot_type
     */
    public static String[] getTotalLots(
            String responseBody, HashMap<String, String> id_to_index_table,
            String id) throws JSONException {
        if (!id_to_index_table.containsKey(id)) {
            return null;
        }

        JSONObject albums = new JSONObject(responseBody);

        JSONObject carpark_info =
                albums.getJSONArray("items")
                        .getJSONObject(0)
                        .getJSONArray("carpark_data")
                        .getJSONObject(Integer.parseInt(id_to_index_table.get(id)))
                        .getJSONArray("carpark_info")
                        .getJSONObject(0);

        String result[] = {carpark_info.getString("total_lots"),
                carpark_info.getString("lots_available"),
                carpark_info.getString("lot_type")};

        return result;
    }
}
