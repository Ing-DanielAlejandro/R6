package com.example.r6.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "r6app.db";
    public static final String TABLE_USERS = "t_Usuaios";
    public static final String TABLE_OPERATORS = "t_Operadores";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_USERS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo INTEGER NOT NULL," +
                "usuario TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "apellidos TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "email TEXT NOT NULL)" );

        sqLiteDatabase.execSQL("create table " + TABLE_OPERATORS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "imagen BLOB NOT NULL," +
                "tipo TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "apodo TEXT NOT NULL," +
                "habilidad TEXT NOT NULL," +
                "org TEXT NOT NULL)" );
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

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USERS);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_OPERATORS);
        onCreate(sqLiteDatabase);
    }

    public Cursor ConsultarUsuPas(String usu,String pas) throws SQLException {
        Cursor mCursor = null;
        mCursor=this.getReadableDatabase().query("t_Usuaios",new String[]{"*"}," usuario like '" + usu +"' and password like '" + pas + "'",null,null,null,null);

        return mCursor;
    }
}
