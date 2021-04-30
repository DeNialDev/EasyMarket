package com.denialdev.easymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText email_input;
    private EditText pass_input;
    private TextView forgot_ps;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email_input = findViewById(R.id.email_input);
        pass_input = findViewById(R.id.pass_input);
        forgot_ps = findViewById(R.id.forgot_ps);
        login_btn = findViewById(R.id.login_btn);


    }
}