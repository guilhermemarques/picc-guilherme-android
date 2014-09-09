package br.com.cast.treinamento.app.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;

public abstract class BaseDAO extends SQLiteOpenHelper {

	private static final String DB_NAME = "TreinamentoAndroid.db";
	private static final int DB_VERSION = 4;
	private Context contexto;
	
	public BaseDAO(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		this.contexto = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	
		try {
			executeScriptAssets(db, "creates.sql");
		} catch (Exception e) {
			db.execSQL(ContatoEntity.CREATE);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try {
			executeScriptAssets(db, "drops.sql");
		} catch (Exception e) {
			db.execSQL(ContatoEntity.DROP);
		}
		this.onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		this.onUpgrade(db, oldVersion, newVersion);
	}
	
	private void executeScriptAssets(SQLiteDatabase db, String createSQL) throws IOException, SQLException {
		db.beginTransaction();
		try {
			InputStream inpStr = contexto.getAssets().open(createSQL);
			BufferedReader br = new BufferedReader(new InputStreamReader(inpStr, "UTF-8"));
			String linha;
			
			while ((linha = br.readLine()) != null) {
				SQLiteStatement statement = db.compileStatement(linha);
				statement.execute();
			}
			db.setTransactionSuccessful();
		} 
		catch (IOException e) {
			Log.i("DAO", "I/O:" + e.getMessage());
			throw e;
		}
		catch (SQLException e) {
			Log.i("DAO", "SQL:" + e.getMessage());
			throw e;
		}
		finally{
			db.endTransaction();
		}
	}
}
