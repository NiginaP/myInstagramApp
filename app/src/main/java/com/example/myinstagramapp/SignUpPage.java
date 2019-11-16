package com.example.myinstagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpPage extends AppCompatActivity {
    public static final String TAG = "SignUpActivity";
    EditText etEmail;
    EditText etUsername;
    EditText etPassword;
    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signup(email, username, password);
            }
        });

    }

    private void signup(String email, String username, String password) {
        ParseUser user = new ParseUser();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(getApplicationContext(), "Successfully signed up!", Toast.LENGTH_LONG).show();
                    goLoginActivity();
                    Toast.makeText(getApplicationContext(), "You may login using your credentials", Toast.LENGTH_LONG).show();
                }else{
                    if (e.getCode() == 202){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(getApplicationContext(), "Something went wrong. Please try again...", Toast.LENGTH_LONG).show();
                    }
                    Log.e(TAG, "SignUp failed: " + e.getCode() + e.getMessage());

                }
            }
        });
    }

    private void goLoginActivity(){
        Log.d(TAG, "Navigating to MainActivity");
        Intent i = new Intent(this, LoginPage.class);
        startActivity(i);
        finish();

    }
}
