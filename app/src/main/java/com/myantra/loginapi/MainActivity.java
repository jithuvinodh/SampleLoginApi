package com.myantra.loginapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.stetho.Stetho;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        Button signBtn = findViewById(R.id.signIn);

        DataModel dataModel = new DataModel(MainActivity.this);
            UserModel userModel1 = dataModel.getUser();
            if (userModel1.getUserName()!=null){
                Intent intent = new Intent(MainActivity.this, Success.class);
                intent.putExtra("Response", userModel1);
                startActivity(intent);
                finish();
            }


        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(myIntent);
            }
        });
    }
}
