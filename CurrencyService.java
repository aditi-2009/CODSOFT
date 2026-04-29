

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyService {

    public double getRate(String base, String target) {
        try {
            String api = "https://open.er-api.com/v6/latest/" + base;

            URL url = new URL(api);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String response = "";
            String line;

            while ((line = br.readLine()) != null) {
                response += line;
            }
            br.close();

            // VERY SIMPLE parsing (no JSON lib)
            String search = "\"" + target + "\":";
            int index = response.indexOf(search);

            if (index == -1) return -1;

            int start = index + search.length();
            int end = response.indexOf(",", start);

            String rateStr = response.substring(start, end);

            return Double.parseDouble(rateStr);

        } catch (Exception e) {
            return -1;
        }
    }
}