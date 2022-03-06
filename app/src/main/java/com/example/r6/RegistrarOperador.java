package com.example.r6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class RegistrarOperador extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private static final int PICK_IMAGE=100;
    ImageView agregarImagen;
    Spinner tipo;
    EditText apodo,org,nombre,habilidad,aaa;
    Button registrar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

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


        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
    }

    private void limpiar(){
        nombre.setText("");
        habilidad.setText("");
        apodo.setText("");
        org.setText("");
    }



    private void cargarWebService() {

        progreso = new ProgressDialog(RegistrarOperador.this);
        progreso.setMessage("Cargando");
        progreso.show();
        request = Volley.newRequestQueue(this);


        String url="https://operadoresr6.000webhostapp.com/wsJSONRegistrarOperador.php?" +
                "apodo=" + apodo.getText().toString()+
                "&nombre=" + nombre.getText().toString()+
                "&org=" + org.getText().toString()+
                "&habilidad=" + habilidad.getText().toString()+
                "&imagen=hola" +
                "&tipo=" +tipo.getSelectedItem().toString();

        url = url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);


    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(RegistrarOperador.this,"No se pudo registrar error: "+error.toString(),Toast.LENGTH_LONG).show();
        progreso.hide();

    }



    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();
        limpiar();
        Toast.makeText(RegistrarOperador.this,"Registro exitoso",Toast.LENGTH_LONG).show();

    }

    }
