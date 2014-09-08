package br.com.cast.treinamento.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import br.com.cast.treinamento.app.dao.mapping.ContatoEntity;
import br.com.cast.treinamento.app.domain.Contato;

public class ContatoDao extends BaseDAO {

	private static ContatoDao INSTANCIA;

	private ContatoDao(Context context) {
		super(context);
	}

	public static ContatoDao getInstancia(Context context) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoDao(context);
		}

		return INSTANCIA;
	}

	public List<Contato> listarTodos() throws SQLException {
		try {
			//Opção 1: db.query
			SQLiteDatabase db = super.getReadableDatabase();
			Cursor cursor = db.query(ContatoEntity.TABELA, ContatoEntity.COLUNAS, null, null, null, null, ContatoEntity.COLUNA_NOME+" ASC");			
			
			//Opção 2: db.rawQuery
//			String sqlTodos = String.format("SELECT * FROM %s ORDER BY %s", ContatoEntity.TABELA, ContatoEntity.COLUNA_NOME);
//			Cursor cursor = db.rawQuery(sqlTodos, null);
			
			return ContatoEntity.bindContatos(cursor);
		} catch (SQLException excecao) {
			Log.e("DAO", excecao.getMessage());
			throw excecao;
		} finally {
			super.close();
		}
	}

	public void salvar(Contato contato) throws SQLException {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(ContatoEntity.COLUNA_NOME, contato.getNome());
			values.put(ContatoEntity.COLUNA_ENDERECO, contato.getEndereco());
			values.put(ContatoEntity.COLUNA_SITE, contato.getSite());
			values.put(ContatoEntity.COLUNA_TELEFONE, contato.getTelefone());
			if (contato.getRelevancia().floatValue() == 0) {
				values.putNull(ContatoEntity.COLUNA_RELEVANCIA);
			} else {
				values.put(ContatoEntity.COLUNA_RELEVANCIA, contato.getRelevancia());
			}

			if (contato.getId() == null) {
				db.insert(ContatoEntity.TABELA, null, values);
			} else {
				String clausulaWhere = ContatoEntity.COLUNA_ID + "=?";
				String[] argumentosWhere =  new String[] { contato.getId().toString() };
				db.update(ContatoEntity.TABELA, values, clausulaWhere, argumentosWhere);
			}

			db.setTransactionSuccessful();
		} catch (SQLException excecao) {
			Log.e("DAO", excecao.getMessage());
			throw excecao;
		} finally {
			db.endTransaction();
			super.close();
		}

	}

	public void excluir(Contato contatoSelecionado) throws SQLException {
		SQLiteDatabase db = super.getWritableDatabase();
		try {
			db.beginTransaction();
			
			//Opção 1: db.delete
			String clausulaWhere = ContatoEntity.COLUNA_ID + "=?";
			String[] argumentosWhere = new String[] { contatoSelecionado.getId().toString() };			
			db.delete(ContatoEntity.TABELA, clausulaWhere, argumentosWhere);
			
			//Opção 1: db.compileStatement
//			String sqlDelete = String.format("DELETE FROM %s WHERE %s = ?", ContatoEntity.TABELA, ContatoEntity.COLUNA_ID);
//			SQLiteStatement comandoSQL = db.compileStatement(sqlDelete);
//			comandoSQL.bindLong(1, contatoSelecionado.getId());		
//			comandoSQL.execute();
			
			db.setTransactionSuccessful();
		} catch (SQLException excecao) {
			Log.e("DAO", excecao.getMessage());
			throw excecao;
		} finally {
			db.endTransaction();
			super.close();
		}
	}

}
