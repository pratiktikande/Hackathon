package com.example.databytes;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class bio extends AppCompatActivity {
    EditText pin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        TextView msgtex = findViewById(R.id.msgtext);
        final Button loginbutton = findViewById(R.id.login);
        final Button pinbutton = findViewById(R.id.button7);
        pin = findViewById(R.id.editTextTextPassword6);
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {

            case BiometricManager.BIOMETRIC_SUCCESS:
                msgtex.setText("You can use the fingerprint sensor to login");
                msgtex.setTextColor(Color.parseColor("#fafafa"));
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                msgtex.setText("This device doesnot have a fingerprint sensor");
                loginbutton.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                msgtex.setText("The biometric sensor is currently unavailable");
                loginbutton.setVisibility(View.GONE);
                break;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                msgtex.setText("Your device doesn't have fingerprint saved,please check your security settings");
                loginbutton.setVisibility(View.GONE);
                break;
        }
        Executor executor = ContextCompat.getMainExecutor(this);
        final BiometricPrompt biometricPrompt = new BiometricPrompt(bio.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                String log = preferences.getString("login","");
                if(log.equals("patient")){
                    Intent intent = new Intent(bio.this,patient.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(bio.this,hlogin.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });
        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("EMERGON")
                .setDescription("Use your fingerprint to login ").setNegativeButtonText("Cancel").build();
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);

            }
        });
        pinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinbutton.setText("press to check password");
                pin.setVisibility(View.VISIBLE);
                String pas = String.valueOf(pin.getText());
                SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                String log = preferences.getString("login","");
                if(log.equals("patient")){
                    String savepas = preferences.getString("patient_pass","");
                    if(pas.equals(savepas)){
                        pin.setVisibility(View.INVISIBLE);
                        pinbutton.setText("Use Login Password instead");
                        Intent intent = new Intent(bio.this,patient.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(bio.this,"incorrect password",Toast.LENGTH_SHORT).show();
                    }}
                else if(log.equals("hospital")){
                    String savepas = preferences.getString("hospital_password","");
                    if(pas.equals(savepas)){
                        pin.setVisibility(View.INVISIBLE);
                        pinbutton.setText("Use Login Password instead");
                        Intent intent = new Intent(bio.this,hlogin.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(bio.this,"incorrect password",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
