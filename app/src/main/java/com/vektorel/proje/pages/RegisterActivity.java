package com.vektorel.proje.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vektorel.proje.R;
import com.vektorel.proje.entitty.User;
import com.vektorel.proje.utility.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText txtusername,txtpasword,txtrepassword,txtemail;
    Button btnkayit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtemail = findViewById(R.id.txtemail);
        txtpasword = findViewById(R.id.txtregisterpassword);
        txtrepassword = findViewById(R.id.txtregisterrepassword);
        txtusername = findViewById(R.id.txtusername);
        btnkayit = findViewById(R.id.btnregister);
        btnkayit.setOnClickListener(view -> UyeOl());
    }

    private void UyeOl() {
        if(txtrepassword.getText().toString().equals(txtpasword.getText().toString())){
            register();
        }else{
            Toast.makeText(this, "Gİrdiğiniz şifreler uyuşmuyor.", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ec2-18-224-182-31.us-east-2.compute.amazonaws.com:8080/user/register?" +
                "email="+txtemail.getText().toString()+"&" +
                "password="+txtpasword.getText().toString()+"&" +
                "username="+txtusername.getText().toString();
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        openLogin();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "onResponse: "+ error.toString());
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }

    private void openLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}