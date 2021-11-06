package com.example.r6;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.r6.DB.DBHelper;
import com.example.r6.DB.DbUsuarios;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    EditText user,pass;
    Button lgn;
    TextView txtReg;
    DbUsuarios dbUsuarios;

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
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbUsuarios = new DbUsuarios(this);


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
                String u = user.getText().toString(), p =pass.getText().toString();
                    if(user.getText().toString().equals("")||pass.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this,"Campos vacios",Toast.LENGTH_SHORT).show();
                    }else if(dbUsuarios.login(user.getText().toString(),pass.getText().toString())==1){
                        guardarPreferencias();
                        Intent i = new Intent(MainActivity.this,MenuPrincipal.class);
                        startActivity(i);
                    }


            }
        });

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