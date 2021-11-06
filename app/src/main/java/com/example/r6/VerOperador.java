package com.example.r6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.r6.DB.DbOperadores;
import com.example.r6.entidades.Operadores;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerOperador extends AppCompatActivity {
    ImageView foto;
    EditText apodo,nombre,org,habilidad,tipo;
    FloatingActionButton edit,delete,save;
    Button guardar;

    Operadores operadores;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_operador);
        nombre = findViewById(R.id.textView15);
        apodo = findViewById(R.id.textView16);
        org = findViewById(R.id.textView18);
        habilidad = findViewById(R.id.textView14);
        tipo = findViewById(R.id.textView17);
        foto = findViewById(R.id.imageView2);
        edit = findViewById(R.id.fabEdit);
        delete = findViewById(R.id.fabdelete);
        save = findViewById(R.id.fabguardar);
        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if (extras==null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbOperadores dbOperadores = new DbOperadores(VerOperador.this);
        operadores = dbOperadores.mostrarOperador(id);

        if(operadores!=null){
            nombre.setText(operadores.getNombre());
            apodo.setText(operadores.getApodo());
            tipo.setText(operadores.getTipo());
            habilidad.setText(operadores.getHabilidad());
            org.setText(operadores.getORG());
            foto.setImageResource(R.drawable.jackal);
            nombre.setEnabled(false);
            apodo.setEnabled(false);
            tipo.setEnabled(false);
            habilidad.setEnabled(false);
            org.setEnabled(false);
            save.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerOperador.this);
                builder.setMessage("Desea Eliminar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(dbOperadores.eliminarOperador(id)){
                            Intent intent = new Intent(VerOperador.this,MenuPrincipal.class);
                            intent.putExtra("ID",id);
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();


            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(VerOperador.this,EditarOperador.class);
                    intent.putExtra("ID",id);
                    startActivity(intent);
                    finish();
            }
        });

    }
}