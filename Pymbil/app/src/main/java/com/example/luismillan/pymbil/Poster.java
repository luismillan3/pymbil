package com.example.luismillan.pymbil;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by luismillan on 9/20/15.
 */
public class Poster extends AsyncTask<JSONArray, Void, String> {
    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);

    }

    protected String doInBackground(JSONArray... urls) {
        JSONObject toSendJson = new JSONObject();
        List<NameValuePair> nameValuePairs;
        String  _response = "No lo cogio bruh";
        try{
            HttpPost post = new HttpPost("http://54.175.238.70:8080/api/login");
            HttpClient httpclient = new DefaultHttpClient();

            toSendJson.put("username","duude");
            toSendJson.put("password","duuude");

            StringEntity se = new StringEntity(toSendJson.toString());
            se.setContentType("application/json");
            post.setEntity(se);
            post.setHeader("Content-type", "application/json");

            HttpResponse response = httpclient.execute(post);
            _response= EntityUtils.toString(response.getEntity());}
        catch(IOException e){
            Log.v("IO error", e.getMessage() + "-" + _response);
        }
        catch (JSONException o){

            Log.v("IO error", o.getMessage());
        }



        return _response;
    }
}
