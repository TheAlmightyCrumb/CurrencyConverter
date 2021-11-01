import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EUR extends Coin {
    private static final double VALUE = 3.65;

    @Override
    public double getValue() {
        try {
            String url = "https://freecurrencyapi.net/api/v2/latest?base_currency=EUR&apikey=aa5cc890-3a34-11ec-be94-49cabbdc742b";
            URL urlForGetRequest = new URL(url);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                JSONObject jo = new JSONObject(response.toString());
                JSONObject data = jo.getJSONObject("data");
                double currentCurrency = Double.parseDouble(data.get("ILS").toString());
                return currentCurrency;
            } else {
                throw new Exception("Error in API Call");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Could not get rate from API, using currency rate - " + VALUE + " instead.");
        }
        return VALUE;
    }

    @Override
    public double calculate(double num) {
        return num * this.getValue();
    }
}

