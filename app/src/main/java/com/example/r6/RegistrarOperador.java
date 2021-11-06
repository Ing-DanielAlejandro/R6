package com.example.r6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.r6.DB.DBHelper;
import com.example.r6.DB.DbOperadores;
import com.example.r6.DB.DbUsuarios;

public class RegistrarOperador extends AppCompatActivity {
    private static final int PICK_IMAGE=100;
    ImageView agregarImagen;
    Spinner tipo;
    EditText apodo,org,nombre,habilidad,aaa;
    Button registrar;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_operador);
        tipo = (Spinner) findViewById(R.id.spinner);
        apodo = findViewById(R.id.editTextTextApodo);
        org = findViewById(R.id.editTextTextorg);
        nombre = findViewById(R.id.editTextTextNombre);
        habilidad = findViewById(R.id.editTextTextHabilidad);
        agregarImagen = findViewById(R.id.agregarImagen);
        registrar = findViewById(R.id.button);
        aaa = findViewById(R.id.aaa);
        String[] tipos = {"Defensor","Atacante"};
        ArrayAdapter <String>  adapter = new ArrayAdapter<String> (this , android.R.layout.simple_spinner_item,tipos);
        tipo.setAdapter(adapter);



        db = new DBHelper(this);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);



        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Intent intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
                startActivityForResult(intent,PICK_IMAGE);*/


                DbOperadores dbOperadores = new DbOperadores(RegistrarOperador.this);
                int u = 1;
                Long id = dbOperadores.insertarOperador(aaa.getText().toString(),tipo.getSelectedItem().toString(),nombre.getText().toString(),apodo.getText().toString(),habilidad.getText().toString(),org.getText().toString());
                if(id>0){
                    Toast.makeText(RegistrarOperador.this, "Operador Agregado", Toast.LENGTH_SHORT).show();
                    limpiar();
                    Intent i = new Intent(RegistrarOperador.this,MenuPrincipal.class);
                    startActivity(i);
                }else {
                    Toast.makeText(RegistrarOperador.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        agregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagenes();
            }
        });

    }

    private void limpiar(){
        nombre.setText("");
        habilidad.setText("");
        apodo.setText("");
        org.setText("");
    }

    public void cargarImagenes(){
        Intent  intent = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
        startActivityForResult(intent,PICK_IMAGE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&& requestCode==PICK_IMAGE){
            Uri path = data.getData();
            String x = getPath(path);
            Integer num = Integer.parseInt(aaa.getText().toString());
            if(db.insertarImagen(x,num)){
                Toast.makeText(getApplicationContext(),"Imagen agregada",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Imagen ERROR",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public  String getPath(Uri uri){
        if(uri==null)return null;
        String[] projection = {MediaStore.Images.Media.DATE_TAKEN};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return  cursor.getString(column_index);
        }
        return uri.getPath();
    }

}