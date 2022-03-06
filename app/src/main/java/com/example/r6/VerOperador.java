package com.example.r6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.r6.DB.DbOperadores;
import com.example.r6.entidades.Operadores;
import com.example.r6.entidades.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerOperador extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    ImageView foto;
    EditText apodo,nombre,org,habilidad,tipo;
    FloatingActionButton edit,delete,save;
    Button guardar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;
    Bundle datos;
    StringRequest stringRequest;

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
        request=Volley.newRequestQueue(this);
        edit = findViewById(R.id.fabEdit);
        delete = findViewById(R.id.fabdelete);
        save = findViewById(R.id.fabguardar);

        datos = getIntent().getExtras();
        id=datos.getInt("ID");

        cargar(id);
        editar();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerOperador.this);
                builder.setMessage("Desea Eliminar?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent =  new Intent(VerOperador.this,MenuPrincipal.class);
                        startActivity(intent);
                        borrar(id);
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
                noEditar();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizar();
            }
        });

    }

    private void borrar(int id) {
        progreso = new ProgressDialog(VerOperador.this);
        progreso.setMessage("Cargando");
        progreso.show();

        String url = "https://operadoresr6.000webhostapp.com/wsJSONEliminarOperador.php?id="+id;
        stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                if (response.trim().equalsIgnoreCase("elimina")) {
                    Toast.makeText(VerOperador.this, "Se ha eliminado con exito", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (response.trim().equalsIgnoreCase("noExiste")) {
                        Toast.makeText(VerOperador.this, "No se encuentra el registro", Toast.LENGTH_SHORT).show();
                        Log.i("Respuesta:", "" + response);
                    } else {
                        Toast.makeText(VerOperador.this, "NO se ha eliminado", Toast.LENGTH_SHORT).show();
                        Log.i("Respuesta:", "" + response);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerOperador.this,"No se pudo conectar", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        });
        request.add(stringRequest);
    }

    private void actualizar() {
        progreso=new ProgressDialog(VerOperador.this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String url="https://operadoresr6.000webhostapp.com/wsJSONActualizarOperador.php";
        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();
                if (response.trim().equalsIgnoreCase("actualiza")) {
                    Toast.makeText(VerOperador.this, "Se actuaizo correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VerOperador.this, "No se actualizo", Toast.LENGTH_SHORT).show();
                    Log.i("Respuesta", "" + response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VerOperador.this,"Error de conexion", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams()throws AuthFailureError{
                String Id = String.valueOf(id);
                String Apodo = apodo.getText().toString();
                String Nombre = nombre.getText().toString();
                String Habilidad = habilidad.getText().toString();
                String Tipo = tipo.getText().toString();
                String Org = org.getText().toString();
                String Imagen = foto.toString();

                Map<String, String> parametros=new HashMap<>();
                parametros.put("id",Id);
                parametros.put("apodo",Apodo);
                parametros.put("nombre",Nombre);
                parametros.put("org",Org);
                parametros.put("habilidad",Habilidad);
                parametros.put("tipo",Tipo);
                parametros.put("imagen",Imagen);
                return parametros;
            }
        };
        request.add(stringRequest);
    }

    private void noEditar() {
        nombre.setEnabled(true);
        apodo.setEnabled(true);
        tipo.setEnabled(true);
        habilidad.setEnabled(true);
        org.setEnabled(true);
        edit.setVisibility(View.GONE);
        save.setVisibility(View.VISIBLE);
    }

    private void editar() {
        nombre.setEnabled(false);
        apodo.setEnabled(false);
        tipo.setEnabled(false);
        habilidad.setEnabled(false);
        org.setEnabled(false);
        save.setVisibility(View.GONE);
    }


    private void cargar(int id) {
        progreso = new ProgressDialog(VerOperador.this);
        progreso.setMessage("Cargando");
        progreso.show();
        request = Volley.newRequestQueue(this);

        String url="https://operadoresr6.000webhostapp.com/wsJSONConsultarOperadorID.php?id="+id;
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(VerOperador.this,"No se pudo cargar error: "+error.toString(),Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        Operadores operadores = null;
        JSONArray json = response.optJSONArray("operador");
        JSONObject jsonObject = null;
        try {
            operadores = new Operadores();
            jsonObject=json.getJSONObject(0);

            operadores.setApodo(jsonObject.optString("apodo"));
            operadores.setNombre(jsonObject.optString("nombre"));
            operadores.setORG(jsonObject.optString("org"));
            operadores.setHabilidad(jsonObject.optString("habilidad"));
            operadores.setTipo(jsonObject.optString("tipo"));
            operadores.setImagen(jsonObject.optString("imagen"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        nombre.setText(operadores.getNombre());
        apodo.setText(operadores.getApodo());
        habilidad.setText(operadores.getHabilidad());
        tipo.setText(operadores.getTipo());
        org.setText(operadores.getORG());
        foto.setImageResource(R.drawable.jackal);


    }
}