package com.vektorel.proje.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vektorel.proje.MainActivity;
import com.vektorel.proje.R;
import com.vektorel.proje.entitty.User;
import com.vektorel.proje.rest.Auth;
import com.vektorel.proje.utility.StaticValues;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.zip.Inflater;

public class LoginActivity extends AppCompatActivity {

    private Button btngiris;
    private EditText txtuser;
    private EditText txtpassword;
    private TextView btnkayit;
    private Auth auth;
    private  final String USER = "Admin";
    private  final String PASSWORD= "12345";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
                 // Görünüm içinden id sine göre
                // bileşeni al
        btngiris = findViewById(R.id.btnlogin);
        txtpassword = findViewById(R.id.txtpassword);
        txtuser = findViewById(R.id.txtuser);
        btnkayit = findViewById(R.id.btnkayit);
        btnkayit.setOnClickListener(view -> RegiterGit());
        btngiris.setOnClickListener(view -> GirisYap());
    }

    private void RegiterGit() {
        Intent inten = new Intent(this,RegisterActivity.class);
        startActivity(inten);
    }

    private  void GirisYap(){
        String username = txtuser.getText().toString();
        String password = txtpassword.getText().toString();
        Login(username,password);
        /*
        auth = new Auth(this);
        auth.doLogin(txtuser.getText().toString(),txtpassword.getText().toString());
        if(USER.equals(txtuser.getText().toString()) &&
            PASSWORD.equals(txtpassword.getText().toString())){
            // this-> Bulunduğum Sayfadan XXX.class -> Sınıfına geçiş yapacağım.
            Intent intent = new Intent(this,MainActivity.class);
            // Activitiyi başlat.
            startActivity(intent);
        }else{
            Toast.makeText(this, "Kullanıcı adı ya da Şifre Hatalı"
                    , Toast.LENGTH_LONG).show();
        }*/
    }

    private void Login(String username,String password){
        RequestQueue queue = Volley.newRequestQueue(this);
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
                            String username = jsonObject.getString("username");
                            String email   = jsonObject.getString("email");
                            StaticValues.user = new User(username,email,id);
                            if(id>0){
                             StartNew(true);
                           }else {
                               StartNew(false);
                           }
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
    }

    private void StartNew(boolean b) {
        if(b){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase
                    .getInstance("https://muhammet-java62-default-rtdb.europe-west1.firebasedatabase.app");
            DatabaseReference myRef = database.getReference("Kullanıcılar");

            myRef.setValue("Giriş Yapan...: "+ StaticValues.user.getUsername());
        }else {
            Toast.makeText(this, "Kullanıc Adı ya da Şifre Hatalı", Toast.LENGTH_SHORT).show();
        }
    }

}