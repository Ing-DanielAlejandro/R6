package com.example.r6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r6.Adaptadores.listaOperadoresAdapter;
import com.example.r6.DB.DbOperadores;
import com.example.r6.entidades.Operadores;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VerDefensores extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{

    RecyclerView recyclerOperadores;
    ArrayList<Operadores> listaOperadores;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_defensores);

        listaOperadores = new ArrayList<>();
        recyclerOperadores=(RecyclerView) findViewById(R.id.listaOperadores);
        recyclerOperadores.setLayoutManager(new LinearLayoutManager(this));
        recyclerOperadores.setHasFixedSize(true);
        request = Volley.newRequestQueue(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cargarWebService();
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(VerDefensores.this);
        progreso.setMessage("Cargando");
        progreso.show();
        request = Volley.newRequestQueue(this);

        String url="https://operadoresr6.000webhostapp.com/wsJSONConsultarDefensor.php?tipo=Defensor";
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
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
            Intent intent =  new Intent(VerDefensores.this,RegistrarOperador.class);
            startActivity(intent);
            finish();
        }else  if (id==R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Cerrar Sesion").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent =  new Intent(VerDefensores.this,MainActivity.class);
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

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(VerDefensores.this,"No se pudo cargar error: "+error.toString(),Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Operadores operadores = null;
        JSONArray json = response.optJSONArray("operador");

        try{
            for(int i = 0;i<json.length();i++){
                operadores = new Operadores();
                JSONObject jsonObject = null;
                jsonObject=json.getJSONObject(i);
                operadores.setId(jsonObject.optInt("id"));
                operadores.setApodo(jsonObject.optString("apodo"));
                operadores.setORG(jsonObject.optString("org"));
                operadores.setImagen(jsonObject.optString("imagen"));
                operadores.setTipo(jsonObject.optString("tipo"));
                operadores.setHabilidad(jsonObject.optString("habilidad"));
                listaOperadores.add(operadores);
            }
            listaOperadoresAdapter adapter = new listaOperadoresAdapter(listaOperadores);
            recyclerOperadores.setAdapter(adapter);


        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}