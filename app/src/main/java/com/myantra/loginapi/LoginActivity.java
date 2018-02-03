package com.myantra.loginapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


        public final static String STRING_URL="http://sit2.skooly.us/skooly/api/login/authenticate";

        EditText userName ;
        EditText userPassword;
        Button loginBtn ;
        String emailId;
        String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        loginBtn = findViewById(R.id.loginBtn);
        userName = findViewById(R.id.emailId);
        userPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new JsonResponse().execute(STRING_URL);
            }
        });


    }

    public class JsonResponse extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {
            emailId =  userName.getText().toString();
            password = userPassword.getText().toString();

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=utf8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Method", "POST");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("emailId",emailId);
                jsonObject.put("password",password);
                jsonObject.put("channel","W");

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(jsonObject.toString());
                writer.flush();
                writer.close();
                os.close();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer buffer = new StringBuffer();
                String line="";
                while ((line=reader.readLine())!=null){
                    buffer.append(line).toString();
                    String jsonResponse = new JSONObject(new String(buffer)).toString();
                    return jsonResponse;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(connection!=null){
                    connection.disconnect();
                }
                if (reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObj = new JSONObject(s);
                UserModel userModel = UserModel.fromJson(jsonObj);
                DataModel data = new DataModel(LoginActivity.this);
                if(!TextUtils.isEmpty(emailId)&&!TextUtils.isEmpty(password)) {

                    if(userModel.getCode().equals("200")) {
                        boolean isInserted = data.insertData(userModel.getUserName(), userModel.getEmailId(), userModel.getSchoolName(), userModel.getPhoneNo());

                        if (isInserted) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, Success.class);
                            intent.putExtra("Response", userModel);
                            startActivity(intent);
                        }
                    } else {
                            Toast.makeText(getApplicationContext(), "Email Or passWord is incooret", Toast.LENGTH_SHORT).show();
                        }

                }else {
                    Toast.makeText(getApplicationContext(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
}
