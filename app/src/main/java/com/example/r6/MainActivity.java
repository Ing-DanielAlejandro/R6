package com.example.r6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    ImageView logo;
    EditText user,pass;
    Button lgn;
    TextView txtReg;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        user = findViewById(R.id.editUsuario);
        pass = findViewById(R.id.editPass);
        lgn = findViewById(R.id.btnLogin);
        txtReg = findViewById(R.id.textRegistrar);
        ObjectAnimator animator = ObjectAnimator.ofFloat(logo, "translationY",-500f);
        animator.setDuration(3500);
        animator.start();
        AlphaAnimation fadeIn = new AlphaAnimation(0.0f,1.0f);
        fadeIn.setDuration(4000);
        fadeIn.setFillAfter(true);
        user.startAnimation(fadeIn);
        pass.startAnimation(fadeIn);
        lgn.startAnimation(fadeIn);

        cargarPreferencias();

        request = Volley.newRequestQueue(this);

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,RegistrarUsuario.class);
                startActivity(i);
            }
        });

        lgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(user.getText().toString().equals("")||pass.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"Campos vacios",Toast.LENGTH_SHORT).show();
                    }else{
                        guardarPreferencias();
                        cargarWebService();
                    }


            }
        });

    }

    private void cargarWebService() {
        String url="https://operadoresr6.000webhostapp.com/wsJSONConsultarUsuario.php?usuario="+user.getText().toString();
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(MainActivity.this,"Error: "+error.toString(),Toast.LENGTH_LONG).show();
    }


    @Override
    public void onResponse(JSONObject response) {

        Usuarios usuarios = new Usuarios();
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


        if( usuarios.getUsuario().equals(user.getText().toString()) && usuarios.getPassword().equals(pass.getText().toString()) ){
            Intent i = new Intent(MainActivity.this,MenuPrincipal.class);
            startActivity(i);
        }else{
            Toast.makeText(MainActivity.this,"Usuario y/o constraeña no coinicen ",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Desea salir?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
        return super.onKeyDown(keyCode, event);
    }

    private void cargarPreferencias() {
        SharedPreferences preferencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String usu = preferencias.getString("user","");
        String pas = preferencias.getString("pas","");
        user.setText(usu);
        pass.setText(pas);
    }

    private void guardarPreferencias(){
        SharedPreferences preferencias = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);

        String usu = user.getText().toString();
        String pas = pass.getText().toString();

        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString("user",usu);
        editor.putString("pas",pas);

        user.setText(usu);
        pass.setText(pas);

        editor.commit();

    }



}