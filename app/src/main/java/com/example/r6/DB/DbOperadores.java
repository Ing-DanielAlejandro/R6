package com.example.r6.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.r6.entidades.Operadores;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DbOperadores extends DBHelper{
    Context context;
    public DbOperadores(@Nullable Context context) {
        super(context);
        this.context = context;
    }



    public boolean insertarImagen(String x , Integer i){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            FileInputStream fs = new FileInputStream(x);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues contentValues = new ContentValues();
            contentValues.put("id",i);
            contentValues.put("img",imgbyte);
            db.insert(TABLE_OPERATORS,null,contentValues);
            fs.close();
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public long insertarOperador(String imagen,String tipo,  String nombre,String apodo, String habilidad, String org) {

        long id = 0;

        try {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();


           /* FileInputStream fs = new FileInputStream(imagen);
            byte[] imgbyte = new byte[fs.available()];
            fs.read(imgbyte);*/


            ContentValues values = new ContentValues();
            values.put("imagen", imagen);
            values.put("tipo", tipo);
            values.put("nombre", nombre);
            values.put("apodo", apodo);
            values.put("habilidad", habilidad);
            values.put("org", org);


            id = db.insert(TABLE_OPERATORS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }
    public boolean eliminarOperador(int id) {

        boolean correcto= false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("DELETE FROM " + TABLE_OPERATORS + " WHERE id = '" + id + "' ");
            correcto=true;
        } catch (Exception ex) {
            ex.toString();
            correcto=false;
        }finally {
            db.close();
        }

        return correcto;
    }

    public boolean editarOperador(int id, String imagen,String tipo,  String nombre,String apodo, String habilidad, String org) {

        boolean correcto= false;
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            db.execSQL("UPDATE " + TABLE_OPERATORS + " SET nombre = '"+nombre+"' , apodo = '"+apodo+"' ,org = '"+org+"' , habilidad = '"+habilidad+"' , tipo = '"+tipo+"' , imagen = '"+imagen+"' WHERE id = '"+id+"' ");
            correcto=true;
        } catch (Exception ex) {
            ex.toString();
            correcto=false;
        }finally {
            db.close();
        }

        return correcto;
    }

    public Operadores mostrarOperador(int id){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Operadores operadores = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_OPERATORS +  " WHERE id = '" + id +"' LIMIT 1",null);
        if(cursor.moveToFirst()){
                operadores = new Operadores();
                operadores.setId(cursor.getInt(0));
                operadores.setImagen(cursor.getString(1));
                operadores.setApodo(cursor.getString(4));
                operadores.setORG(cursor.getString(6));
                operadores.setNombre(cursor.getString(3));
                operadores.setTipo(cursor.getString(2));
                operadores.setHabilidad(cursor.getString(5));
        }
        cursor.close();
        return operadores;
    }

    public ArrayList<Operadores> mostrarOperadoresDef(){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Operadores> listaOperadores = new ArrayList<>();
        Operadores operadores = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_OPERATORS +  " WHERE tipo = 'Defensor' ",null);
        if(cursor.moveToFirst()){
            do {
                operadores = new Operadores();
                operadores.setId(cursor.getInt(0));
                operadores.setImagen(cursor.getString(1));
                operadores.setApodo(cursor.getString(4));
                operadores.setORG(cursor.getString(6));
                listaOperadores.add(operadores);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listaOperadores;
    }

    public ArrayList<Operadores> mostrarOperadoresAt(){

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Operadores> listaOperadores = new ArrayList<>();
        Operadores operadores = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_OPERATORS +  " WHERE tipo = 'Atacante' ",null);
        if(cursor.moveToFirst()){
            do {
                operadores = new Operadores();
                operadores.setId(cursor.getInt(0));
                operadores.setImagen(cursor.getString(1));
                operadores.setApodo(cursor.getString(4));
                operadores.setORG(cursor.getString(6));
                listaOperadores.add(operadores);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listaOperadores;
    }

}
