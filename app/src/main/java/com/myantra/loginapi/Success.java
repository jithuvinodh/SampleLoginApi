package com.myantra.loginapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Success extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        UserModel data = (UserModel) getIntent().getSerializableExtra("Response");

        TextView userName = findViewById(R.id.userValue);
        TextView mobileNo =findViewById(R.id.mobileValue);
        TextView email = findViewById(R.id.emailValue);

        userName.setText(data.getUserName());
        email.setText(data.getEmailId());
        mobileNo.setText(data.getPhoneNo());

    }

    @Override
    public void onBackPressed() {
    }
}
