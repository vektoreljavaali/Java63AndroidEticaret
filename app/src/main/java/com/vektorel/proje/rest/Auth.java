package com.vektorel.proje.rest;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EventListener;

public class Auth {
    Context context;
    public Auth(Context context){
        this.context = context;
    }

    public boolean doLogin(String username, String password){
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String url = "http://ec2-18-224-182-31.us-east-2.compute.amazonaws.com:8080"+
        "/user/dologin?username="+username+"&password="+password;
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            long id = jsonObject.getLong("id");
                            Log.d("TAG", "onResponse id..: : "+id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onResponse: "+ error.toString());
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
        return true;
    }


}
