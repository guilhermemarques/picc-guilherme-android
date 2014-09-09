package br.com.cast.treinamento.app;


import br.com.cast.treinamento.app.service.ContatoService;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FiltraContatosActivity extends LifeCicleActivity {

	private EditText txtNome, txtTelefone;
	private Button btnFiltrar;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_filtro_contatos);
		super.getSupportActionBar().setSubtitle(R.string.subtitle_pesquisar_contatos_activity);
		
		bindingElementosLayout();
		btnFiltrar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(), ListaContatosActivity.class);
				Bundle params = new Bundle();

				String nome = txtNome.getText().toString();
				String telefone = txtTelefone.getText().toString();
				params.putString("nome", nome);
				params.putString("telefone", telefone);
				intent.putExtras(params);
				startActivity(intent);
			}
		});

	}

	private void bindingElementosLayout() {
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtTelefone = (EditText) findViewById(R.id.txtTelefone);
		btnFiltrar = (Button) findViewById(R.id.btnFiltrar);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.lista_contatos, menu);
		menu.findItem(R.id.action_editar).setVisible(false);
		menu.findItem(R.id.action_excluir).setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		executarAcaoMenuSelecionado(item.getItemId());
		return super.onOptionsItemSelected(item);
	}
	
	private void executarAcaoMenuSelecionado(int id) {
		switch (id) {
		case R.id.action_novo:
			Intent intentIncluir = new Intent(this, ContatoActivity.class);
			super.startActivity(intentIncluir);
			break;		
		default:
			break;
		}
	}
}
