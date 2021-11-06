package com.example.r6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.r6.DB.DbUsuarios;

public class RegistrarUsuario extends AppCompatActivity {

    EditText usuario,nombre,apellidos,telefono,email,contraseña;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        usuario = findViewById(R.id.editTextUsuario);
        nombre = findViewById(R.id.editTextNombres);
        apellidos = findViewById(R.id.editTextApellidos);
        telefono = findViewById(R.id.editTextTel);
        email = findViewById(R.id.editTextEmail);
        registrar = findViewById(R.id.btnRegistrar);
        contraseña = findViewById(R.id.editTextPassword);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbUsuarios dbUsuarios = new DbUsuarios(RegistrarUsuario.this);
                int u = 1;
                Long id = dbUsuarios.insertarUsuario(1,usuario.getText().toString(),contraseña.getText().toString(),nombre.getText().toString(),apellidos.getText().toString(),telefono.getText().toString(),email.getText().toString());
                if(id>0){
                    Toast.makeText(RegistrarUsuario.this, "Contacto Agregado", Toast.LENGTH_SHORT).show();
                    limpiar();
                    Intent i = new Intent(RegistrarUsuario.this,MainActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(RegistrarUsuario.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpiar(){
        nombre.setText("");
        telefono.setText("");
        apellidos.setText("");
        email.setText("");
        usuario.setText("");
    }
}