package currencyStatistics;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonParser {

    public CurrencyComparator parseData(String year, String currency1, String currency2 ){

        String url;
        if(!year.equals("2020")){ //Predifined start and end of the year url request
            url = "https://api.exchangeratesapi.io/history?start_at=" + year + "-01-01&end_at=" + year + "-12-31&symbols=" + currency1 +"&base=" + currency2;
            System.out.println(url);
        }
        else { //Formated url request for start of the year and current date today
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            url = "https://api.exchangeratesapi.io/history?start_at=" + year + "-01-01&end_at=" + dtf.format(now) + "&symbols=" + currency1 +"&base=" + currency2;
        }
        //System.out.println(url);

        URL urlObject;

        try {
            urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            int responseCode = connection.getResponseCode();

            if(responseCode > 299){
                //failed response or wrong request URL - usually they have no data on
                // one of the currencies for the requested year
                //System.out.println(responseCode);
                return new CurrencyComparator(); //return a null object with status = 0 == Error on request
            }
            else{
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while((line = in.readLine()) != null){
                    response.append(line);
                }
                in.close();

                //get the object containing dates + values array without base, start_at and end_at
                JSONObject object =new JSONObject ((new JSONObject(response.toString())).getJSONObject("rates").toString());
                //System.out.println(object.toString());

                HashMap<String,Double> hashMap = new HashMap<>(); //Container for the returned values;

                Iterator<String> keys = object.keys();
                while(keys.hasNext()){
                    //Iterate thought the array of the object and get the values of the key (date)
                    String key = keys.next();
                    JSONObject aux = new JSONObject(object.getJSONObject(key).toString());

                    hashMap.put(key,aux.getDouble(currency1));
                    //System.out.println("Key: " + key +" Value:" + hashMap.get(key));
                }

                //Order the hashMap by keys(Dates)
                //The api provides the years' dates in a random order
                TreeMap<String, Double> sorted = new TreeMap<>(hashMap);

                return new CurrencyComparator(sorted); //Return object with ordered dates + values
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CurrencyComparator(); //return a null object with status = 0 == Error on request
    }



}
