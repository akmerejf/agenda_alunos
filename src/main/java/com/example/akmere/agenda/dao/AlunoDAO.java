package com.example.akmere.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.akmere.agenda.model.Aluno;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akmere on 12/25/16.
 */

public class AlunoDAO extends SQLiteOpenHelper{

    public AlunoDAO(Context context) {
        super(context,"Agenda", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, name TEXT NOT NULL, site TEXT, address TEXT, phone TEXT, gender, birthday TEXT, situation TEXT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insereAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos", null, dados);

    }

    @NonNull
    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();

        dados.put("name", aluno.getNome());
        dados.put("site", aluno.getSite());
        dados.put("phone", aluno.getPhone());
        dados.put("address", aluno.getAddress());
        dados.put("gender", aluno.getSexo());
        dados.put("birthday", aluno.getNascimento());
        dados.put("situation", aluno.getSituacao());
        return dados;
    }





    public List<Aluno> buscaAluno() {
        String sql = "SELECT * FROM Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<>();

        while (c.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(c.getLong(c.getColumnIndex("id")));
            aluno.setNome(c.getString(c.getColumnIndex("name")));
            aluno.setSite(c.getString(c.getColumnIndex("site")));
            aluno.setPhone(c.getString(c.getColumnIndex("phone")));
            aluno.setAddress(c.getString(c.getColumnIndex("address")));
            aluno.setNascimento(c.getString(c.getColumnIndex("birthday")));
            aluno.setSituacao(c.getString(c.getColumnIndex("situation")));
            aluno.setSexo(c.getString(c.getColumnIndex("gender")));

            alunos.add(aluno);
        }

        c.close();

        return alunos;
    }

    public void deleta(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {String.valueOf(aluno.getId())};
        db.delete("Alunos", "id = ?", params);
    }

    public void alteraAluno(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosDoAluno(aluno);

        String[] params = {String.valueOf(aluno.getId())};

        db.update("Alunos", dados, "id = ?", params);
    }
}
