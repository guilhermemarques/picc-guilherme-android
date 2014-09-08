package br.com.cast.treinamento.app.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDAO extends SQLiteOpenHelper{

	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSION = 1; //Mudando as versão é preciso implementar o onUpgrade para mostrar o que foi alterado da versão 1 pra 2 por exemplo;

	public BaseDAO(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create = "CREATE TABLE tb_contato (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT NOT NULL, site TEXT NOT NULL, telefone TEXT NOT NULL, relevancia REAL);";
		db.execSQL(create);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String drop = "DROP TABLE IF EXISTS tb_contato;";
		db.execSQL(drop);
		this.onCreate(db);	
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}

}
