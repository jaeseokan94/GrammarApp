package com.example.spanishgrammarapp.Data;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by JAESEOKAN on 16/03/2016.
 */
public class LevelAPI {

    private final DatabaseHelper database;


    public LevelAPI(DatabaseHelper db) {
        database = db;
    }

    private class request extends AsyncTask<String,String,JSONArray>

    {
        @Override
        protected JSONArray doInBackground (String...params){
        HttpURLConnection urlConnection = null;
        BufferedReader bReader = null;

        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream iStream = urlConnection.getInputStream();

            bReader = new BufferedReader(new InputStreamReader(iStream));

            StringBuilder sBuilder = new StringBuilder();

            String line = "";
            while ((line = bReader.readLine()) != null) {
                sBuilder.append(line);
            }

            JSONArray jsonArray = new JSONArray(sBuilder.toString());

            return jsonArray;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }



        private String levelListURL(LanguageData language) {
            return "http://lang-it-up.herokuapp.com/polls/api/"+language+"/levelList";
        }

        List<LanguageData> languageData = database.getLanguageList();

        public void getLevel() {
            for (int i = 0 ; i < languageData.size(); i++) {
                    new request().execute(levelListURL(languageData.get(i)));
                }
        }
/*
        @Override
        protected void onPostExecute (JSONArray s){

            try {
                JSONArray jsonArray = s.getJSONArray(1); //this link is temporary, it needs to be generalized

                for(int i = 0 ; i < jsonArray.length(); i++ ){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    String level_name= jsonObject.getString("level");
                    database.addLevel(langauge , level_name);
                }
            } catch (Exception e) {
                System.out.println("JSON EXCEPTION ERROR API LEVEL");
                e.printStackTrace();
            }

    }
*/
    }



}
