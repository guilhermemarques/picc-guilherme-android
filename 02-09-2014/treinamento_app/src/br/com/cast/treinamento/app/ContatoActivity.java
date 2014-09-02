package br.com.cast.treinamento.app;

import br.com.cast.treinamento.app.entity.Contato;
import br.com.cast.treinamento.app.service.ContatoService;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.os.Bundle;

public class ContatoActivity extends ActionBarActivity {

	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar rtbRelevancia;
	private Button btnSalvar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);
		
		bindingsElementosLayout();
		
		btnSalvar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Contato contato = new Contato();
				contato.setNome(txtNome.getText().toString());
				contato.setEndereco(txtEndereco.getText().toString());
				contato.setSite(txtSite.getText().toString());
				contato.setTelefone(txtTelefone.getText().toString());
				contato.setRelevancia(rtbRelevancia.getRating());
				ContatoService.getInstancia().salvar(contato);
				
				ContatoActivity.this.finish();
			}
		});
	}

	private void bindingsElementosLayout() {
		txtNome = (EditText)findViewById(R.id.txtNome);
		txtEndereco = (EditText)findViewById(R.id.txtEndereco);
		txtSite = (EditText)findViewById(R.id.txtSite);
		txtTelefone = (EditText)findViewById(R.id.txtTelefone);
		
		rtbRelevancia = (RatingBar)findViewById(R.id.rtbRelevancia);
		btnSalvar = (Button)findViewById(R.id.btnSalvar);
	}

}
