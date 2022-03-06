package com.example.r6;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.r6.entidades.Usuarios;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarUsuario extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    EditText usuario,nombre,apellidos,telefono,email,contraseña;
    Button registrar;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progreso;

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
                cargarWebService();
            }

        });
    }

    private void limpiar(){
        usuario.setText("");
        nombre.setText("");
        telefono.setText("");
        apellidos.setText("");
        email.setText("");
        usuario.setText("");
    }


    private void cargarWebService() {
        progreso = new ProgressDialog(RegistrarUsuario.this);
        progreso.setMessage("Cargando");
        progreso.show();
        request = Volley.newRequestQueue(this);
        String url="https://operadoresr6.000webhostapp.com/wsJSONRegistro.php?tipo=1" +
                "&usuario=" + usuario.getText().toString()+
                "&password=" + contraseña.getText().toString()+
                "&nombre=" + nombre.getText().toString()+
                "&apellidos=" + apellidos.getText().toString()+
                "&telefono=" +telefono.getText().toString()+
                "&email=" +email.getText().toString();
        url = url.replace(" ","%20");
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }

    /*private void REgistrarUsuario() {

        String url="http://192.168.100.134/bdremota/wsJSONRegistro.php?tipo=1" +
                "&usuario=" + usuario.getText().toString()+
                "&password=" + contraseña.getText().toString()+
                "&nombre=" + nombre.getText().toString()+
                "&apellidos=" + apellidos.getText().toString()+
                "&telefono=" +telefono.getText().toString()+
                "&email=" +email.getText().toString();

        url = url.replace(" ","%20");
        request = Volley.newRequestQueue(this);
        jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }*/

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(RegistrarUsuario.this,"No se pudo registrar error: "+error.toString(),Toast.LENGTH_LONG).show();
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

        progreso.hide();
        limpiar();
        Toast.makeText(RegistrarUsuario.this,"Registro exitoso",Toast.LENGTH_LONG).show();

        /*Usuarios usuarios = new Usuarios();
        JSONArray json;
        json = response.optJSONArray("usuario");
        JSONObject jsonObject = null;
        try {
            jsonObject = json.getJSONObject(0);
            usuarios.setUsuario(jsonObject.optString("usuario"));
            usuarios.setPassword(jsonObject.optString("password"));
            usuarios.setNombre(jsonObject.optString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(usuarios.getUsuario().equals(usuario.getText().toString())){
            Toast.makeText(RegistrarUsuario.this,"Usuario ya existe ",Toast.LENGTH_LONG).show();
        }else {
            REgistrarUsuario();
            Toast.makeText(RegistrarUsuario.this,"Usuario registrado ",Toast.LENGTH_LONG).show();
            limpiar();
        }*/

    }


}