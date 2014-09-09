package br.com.cast.treinamento.app;


import java.util.Map;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;
import br.com.cast.treinamento.app.service.ContatoService;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ContatoActivity extends LifeCicleActivity {

	public static final String CHAVE_CONTATO = "CHAVE_CONTATO";
	private EditText txtNome, txtEndereco, txtSite, txtTelefone;
	private RatingBar rtbRelevancia;
	private Button btnSalvar;
	private Contato contato;

	@Override
	public String getActivityName() {
		return this.getClass().getSimpleName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contato);

		bindingsElementosLayout();

		int recursoSubtitulo; 
		contato = (Contato) getIntent().getSerializableExtra(CHAVE_CONTATO);
		if (contato == null) {
			contato = new Contato();
			recursoSubtitulo = R.string.subtitle_incluir_contato;
		} else {
			carregarElementosLayout();
			recursoSubtitulo = R.string.subtitle_alterar_contato;
		}		
		
		getSupportActionBar().setSubtitle(recursoSubtitulo);

		btnSalvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				carregarContato();
				try {
					ContatoService.getInstancia(ContatoActivity.this).salvar(contato);
					ContatoActivity.this.finish();
				} catch (ExcecaoNegocio e) {
					for (Map.Entry<Integer, Integer> erro : e.getMapaErros().entrySet()) {
						EditText campoErro = (EditText) findViewById(erro.getKey());
						Drawable drawble = getResources().getDrawable(R.drawable.ic_launcher);
						drawble.setBounds(0, 0, 50, 50);
						campoErro.setError(getString(erro.getValue()), drawble);
					}
				}
			}
		});
	}

	private void carregarElementosLayout() {
		txtNome.setText(contato.getNome());
		txtEndereco.setText(contato.getEndereco());
		txtSite.setText(contato.getSite());
		txtTelefone.setText(contato.getTelefone());
		
		if(contato.getRelevancia() != null){
			rtbRelevancia.setRating(contato.getRelevancia());
		}
	}

	private void bindingsElementosLayout() {
		txtNome = (EditText) findViewById(R.id.txtNome);
		txtEndereco = (EditText) findViewById(R.id.txtEndereco);
		txtSite = (EditText) findViewById(R.id.txtSite);
		txtTelefone = (EditText) findViewById(R.id.txtTelefone);

		rtbRelevancia = (RatingBar) findViewById(R.id.rtbRelevancia);
		btnSalvar = (Button) findViewById(R.id.btnSalvar);
	}

	private void carregarContato() {
		contato.setNome(txtNome.getText().toString());
		contato.setEndereco(txtEndereco.getText().toString());
		contato.setSite(txtSite.getText().toString());
		contato.setTelefone(txtTelefone.getText().toString());
		contato.setRelevancia(rtbRelevancia.getRating());
	}

}
