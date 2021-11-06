package com.example.r6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.r6.DB.DbOperadores;
import com.example.r6.entidades.Operadores;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarOperador extends AppCompatActivity {
    ImageView foto;
    EditText apodo,nombre,org,habilidad,tipo;
    Button guardar;
    FloatingActionButton delete,save,edit;
    boolean correcto = false;
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
        save = findViewById(R.id.fabguardar);
        edit = findViewById(R.id.fabEdit);
        delete = findViewById(R.id.fabdelete);
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
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

        DbOperadores dbOperadores = new DbOperadores(EditarOperador.this);
        operadores = dbOperadores.mostrarOperador(id);

        if(operadores!=null){
            nombre.setText(operadores.getNombre());
            apodo.setText(operadores.getApodo());
            tipo.setText(operadores.getTipo());
            habilidad.setText(operadores.getHabilidad());
            org.setText(operadores.getORG());
            foto.setImageResource(R.drawable.jackal);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nombre.getText().toString().equals("")&& !apodo.getText().toString().equals("")&& !habilidad.getText().toString().equals("")&& !org.getText().toString().equals("")&& !tipo.getText().toString().equals("")){
                    correcto = dbOperadores.editarOperador(id, String.valueOf(foto.getTextDirection()),tipo.getText().toString(),nombre.getText().toString(),apodo.getText().toString(),habilidad.getText().toString(),org.getText().toString());
                    if(correcto){
                        Toast.makeText(EditarOperador.this,"Operador modificado",Toast.LENGTH_SHORT).show();
                        verRegistro();
                        finish();
                    }else{
                        Toast.makeText(EditarOperador.this,"Error al modificar",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(EditarOperador.this,"Campos vacios",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verRegistro(){
        Intent intent = new Intent(this,VerDefensores.class);
        intent.putExtra("ID",id);
        startActivity(intent);
    }
}