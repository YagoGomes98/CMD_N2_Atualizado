package com.example.apptrabalhon1;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class FilmeDAO {

    public static void inserir(Filme filme, Context context){
        ContentValues valores = new ContentValues();
        valores.put("nome", filme.nome );
        valores.put("ano", filme.getAno() );
        valores.put("categoria", filme.getCategoria().toString());
        valores.put("analise", filme.getAnalise().toString());

        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.insert("filme", null, valores);
    }

    public static void editar(Filme filme, Context context){
        ContentValues valores = new ContentValues();
        valores.put("nome", filme.nome );
        valores.put("ano", filme.getAno() );
        valores.put("categoria", filme.getCategoria().toString());
        valores.put("analise", filme.getAnalise().toString());
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();

        db.update("filme", valores, " id = " + filme.id , null );
    }

    public static void excluir(int id, Context context){
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("filme", " id = " + id , null);
    }

    public static List<Filme> getFilmes(Context context){
        List<Filme> lista = new ArrayList<>();
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nome, ano, categoria, analise FROM filme ORDER BY nome", null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do{
                Filme filme = new Filme();
                filme.id = cursor.getInt( 0);
                filme.nome = cursor.getString(1);
                filme.setAno( cursor.getInt(2) );
                filme.setCategoria(cursor.getString(3));
                filme.setAnalise(cursor.getString(4));
                lista.add( filme );
            }while( cursor.moveToNext() );
        }
        return lista;
    }

    public static Filme getFilmeById(Context context, int id){
        Banco banco = new Banco(context);
        SQLiteDatabase db = banco.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nome, ano, categoria, analise FROM filme WHERE id = " + id, null );
        if( cursor.getCount() > 0 ){
            cursor.moveToFirst();
            Filme filme = new Filme();
            filme.id = cursor.getInt( 0);
            filme.nome = cursor.getString(1);
            filme.setAno( cursor.getInt(2) );
            filme.setCategoria(cursor.getString(3));
            filme.setAnalise(cursor.getString(4));
            return filme;
        }else{
            return null;
        }
    }



}