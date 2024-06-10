package com.example.la6_20175557;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.la6_20175557.databinding.ActivityMainBinding;
import com.example.la6_20175557.dto.egresos;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser currentUser;
    ActivityMainBinding binding;
    ListenerRegistration snapshotListener;
    LinearLayout lista_ingresos, lista_egresos, inicio, log_out;

    private static final int TOAST_DURATION = Toast.LENGTH_LONG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        lista_ingresos = findViewById(R.id.lista_ingresos_nav);
        lista_egresos = findViewById(R.id.lista_egresos_nav);
        inicio = findViewById(R.id.inicio_nav);
        log_out = findViewById(R.id.cerrar_sesion);

        String correo_usuario = getIntent().getStringExtra("correo");
        lista_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ingresos_Activity.class);
                intent.putExtra("correo", correo_usuario); // Reemplaza "clave" y "valor" con la información que quieras pasar
                startActivity(intent);
            }
        });
        lista_egresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, egresos_Activity.class);
                intent.putExtra("correo", correo_usuario); // Reemplaza "clave" y "valor" con la información que quieras pasar
                startActivity(intent);
            }
        });
        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cerrar sesión y redirigir a MainActivity
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recreate();
            }
        });


        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();

    }
}