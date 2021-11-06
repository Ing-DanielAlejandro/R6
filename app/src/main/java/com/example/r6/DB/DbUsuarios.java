package com.example.r6.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbUsuarios extends DBHelper{

    Context context;

    public DbUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public int login(String user,String pass){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int i = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE usuario = '" + user + "' and password = '" + pass + "' ",null);
        if(cursor.moveToFirst()){
            do {
                    i++;
            }while(cursor.moveToNext());
        }


        return i;
    }

    public long insertarUsuario(int tipo,String usuario, String contraseña, String nombre, String apellidos, String telefono, String email) {

        long id = 0;

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("tipo", tipo);
            values.put("usuario", usuario);
            values.put("password", contraseña);
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("telefono", telefono);
            values.put("email", email);


            id = db.insert(TABLE_USERS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
}
