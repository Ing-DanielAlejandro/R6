package com.example.r6;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r6.Adaptadores.listaOperadoresAdapter;
import com.example.r6.DB.DbOperadores;
import com.example.r6.entidades.Operadores;

import java.util.ArrayList;

public class VerAtacantes extends AppCompatActivity {

    RecyclerView listaOperadores;
    ArrayList<Operadores> listaArrayOperadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_defensores);
        listaOperadores = findViewById(R.id.listaOperadores);
        listaOperadores.setLayoutManager(new LinearLayoutManager(this));
        DbOperadores dbOperadores = new DbOperadores(VerAtacantes.this);
        listaArrayOperadores = new ArrayList<>();
        listaOperadoresAdapter adapter = new listaOperadoresAdapter(dbOperadores.mostrarOperadoresAt());
        listaOperadores.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.add){
            Intent intent =  new Intent(VerAtacantes.this,RegistrarOperador.class);
            startActivity(intent);
            finish();
        }else  if (id==R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cerrar Sesion").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =  new Intent(VerAtacantes.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }
        return true;
    }
}