package com.example.lanocheappmoviles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnRegistrar;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        //Asigna a los ficheros
        txtEmail = (EditText) findViewById(R.id.txtEma);
        txtPassword = (EditText) findViewById(R.id.txtPass);
        btnLogin = (Button) findViewById(R.id.btnLog);
        btnRegistrar = (Button) findViewById(R.id.btnReg);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null){

                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intentLog = new Intent(MainActivity.this, LoginActivity.class);
                //MainActivity.this.startActivity(intentLog);

                startSignIn();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, RegistroActivity.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
    }

    public void onStart(){
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn() {

        String email = txtEmail.getText().toString().trim();
        String passw = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(passw)) {

            Toast.makeText(MainActivity.this, "Los campos están vacios",
                    Toast.LENGTH_LONG).show();

        } else {
            mAuth.signInWithEmailAndPassword(email, passw)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                Toast.makeText(MainActivity.this, "Problema al Ingresar",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }


    }
}
