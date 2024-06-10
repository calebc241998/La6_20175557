package com.example.la6_20175557;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.la6_20175557.databinding.ActivityLoginBinding;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Login_Activity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private final static String TAG = "msg-test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) { //user logged-in
            if (currentUser.isEmailVerified()) {
                Log.d(TAG, "Firebase uid: " + currentUser.getUid());
                goToMainActivity();
            }
        }

        binding.loginBtn.setOnClickListener(view -> {

            binding.loginBtn.setEnabled(false);

            AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.activity_main)
                    .setGoogleButtonId(R.id.btn_login_google)
                    .setEmailButtonId(R.id.btn_login_mail)
                    .build();

            //no hay sesión
            Intent intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setTheme(R.style.Base_Theme_La6_20175557)
                    .setIsSmartLockEnabled(false)
                    //.setAuthMethodPickerLayout(authMethodPickerLayout)
                    .setLogo(R.drawable.pucp)
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build()
                    ))
                    .build();

            signInLauncher.launch(intent);
        });
    }

    /* launchers tienen 2 partes {
        1: contrato,
        2: callback: que hacer luego de finalizado el contrato
    } */

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Log.d(TAG, "Firebase uid: " + user.getUid());
                        Log.d(TAG, "Display name: " + user.getDisplayName());
                        Log.d(TAG, "Email: " + user.getEmail());


                        user.reload().addOnCompleteListener(task -> {
                            if (user.isEmailVerified()) {
                                goToMainActivity();
                            } else {
                                user.sendEmailVerification().addOnCompleteListener(task2 -> {
                                    Toast.makeText(Login_Activity.this, "Se le ha enviado un correo para validar su cuenta", Toast.LENGTH_LONG).show();
                                    //
                                });
                            }
                        });
                    } else {
                        Log.d(TAG, "user == null");
                    }
                } else {
                    Log.d(TAG, "Canceló el Log-in");
                }
                binding.loginBtn.setEnabled(true);
            }
    );

    public void goToMainActivity() {
        Intent intent = new Intent(Login_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}