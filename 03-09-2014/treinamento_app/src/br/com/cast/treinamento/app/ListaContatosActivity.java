package br.com.cast.treinamento.app;

import java.util.List;

import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import br.com.cast.treinamento.app.widget.ContatoAdapter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaContatosActivity extends LifeCicleActivity {
	private ListView listViewContatos;
	private Contato contatoSelecionado;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_lista_contatos);
		listViewContatos = (ListView) super.findViewById((R.id.listViewContatos));

		// Add comando pra lista poder clicar e criar menu
		super.registerForContextMenu(listViewContatos);

		/*
		 * listViewContatos.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) { String mensagem =
		 * String.format(getString(R.string.voce_clicou_na_posicao), posicao); Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_SHORT).show(); } });
		 */
		/*
		 * listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {
		 * 
		 * @Override public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) { String mensagem =
		 * getString(R.string.voce_clicou_na_posicao, adapter.getItemAtPosition(posicao)); Toast.makeText(view.getContext(), mensagem,
		 * Toast.LENGTH_SHORT).show(); return false; //(não para os outros eventos) //return true; //(só esse evento acontece, os outros são parados) } });
		 */

		listViewContatos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int posicao, long id) {
				contatoSelecionado = (Contato) adapter.getItemAtPosition(posicao);
				
				return false; // (não para os outros eventos)
				// return true; //(só esse evento acontece, os outros são parados)
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		carregarListView();
	}

	private void carregarListView() {
		List<Contato> contatos = ContatoService.getInstancia().listarTodos();
		ContatoAdapter adapter = new ContatoAdapter(this, contatos); 

		listViewContatos.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contato, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.lista_contato_context, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_novo:
			Intent intent = new Intent(this, ContatoActivity.class);
			super.startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_editar:
			Intent intent = new Intent(this, ContatoActivity.class);
			intent.putExtra(ContatoActivity.CHAVE_CONTATO, contatoSelecionado);			
			super.startActivity(intent);
			break;
		case R.id.action_excluir:
			new AlertDialog.Builder(this)
				.setTitle("Confirmação")
				.setMessage(getString(R.string.dialog_msg_excluir, contatoSelecionado.getNome()))
				.setPositiveButton(R.string.dialog_sim, new OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ContatoService.getInstancia().excluir(contatoSelecionado);
						carregarListView();
					}				
				})
				.setNeutralButton(R.string.dialog_nao, null)
				.create().show();
			break;

		default:
			break;
		}
		return super.onContextItemSelected(item);
	}
}
