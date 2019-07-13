package com.example.lanocheappmoviles;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtcorr, txtpass;
    private Button btnContinuar;
    private ProgressDialog proDia;

    //Declaramos la variable de la clase FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Inicializamos el obj FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        //validamos los componentes
        txtcorr = findViewById(R.id.txtCorr);
        txtpass = findViewById(R.id.txtPass);
        btnContinuar = findViewById(R.id.btnCont);

        //instanciamos obj de progressDialog
        proDia = new ProgressDialog(this);

        btnContinuar.setOnClickListener(this);

    }

    public void registrarUsuario() {

        //Obtenemos el correo y contraseña desde las cajas de texto
        String correo = txtcorr.getText().toString();
        String pass = txtpass.getText().toString();

        //Verificamos que las cajas de texto no esten vacias
        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Se debe ingresar un email",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Se debe ingresar una contraseña",
                    Toast.LENGTH_LONG).show();
            return;
        }

        proDia.setMessage("Realizando registro en linea...");
        proDia.show();

        mAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Validar todo bien
                        if (task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                            builder.setIcon(R.mipmap.ic_launcher).
                                    setTitle("¡Felicidades!").
                                    setMessage("Su registro se efectuó satisfactoriamente.").
                                    setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intentContMain = new Intent(RegistroActivity.this, MainActivity.class);
                                            RegistroActivity.this.startActivity(intentContMain);
                                        }
                                    });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                                builder.setIcon(R.mipmap.ic_launcher).
                                        setTitle("¡Ocurrió un problema!").
                                        setMessage("¡El usuario ya existe! ").
                                        setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intentContMain = new Intent(RegistroActivity.this, RegistroActivity.class);
                                                RegistroActivity.this.startActivity(intentContMain);
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

                                builder.setIcon(R.mipmap.ic_launcher).
                                        setTitle("¡Ocurrió un problema!").
                                        setMessage("No se puede registrar el usuario: ").
                                        setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intentContMain = new Intent(RegistroActivity.this, RegistroActivity.class);
                                                RegistroActivity.this.startActivity(intentContMain);
                                            }
                                        });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                        proDia.dismiss();
                    }
                });
        /*
        if (correo.length() == 0) {
            Toast.makeText(this, "Debes ingresar un correo", Toast.LENGTH_LONG).show();
        }
        if (pass.length() == 0) {
            Toast.makeText(this, "Debes ingresar una contraseña", Toast.LENGTH_LONG).show();
        }
        if (correo.length() != 0 && pass.length() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

            builder.setIcon(R.mipmap.ic_launcher).
                    setTitle("¡Felicidades!").
                    setMessage("Su registro se efectuó satisfactoriamente.").
                    setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intentContMain = new Intent(RegistroActivity.this, MainActivity.class);
                            RegistroActivity.this.startActivity(intentContMain);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }*/

    }

    public void onClick(View view) {
        //invocamos metodo
        registrarUsuario();
    }
}

