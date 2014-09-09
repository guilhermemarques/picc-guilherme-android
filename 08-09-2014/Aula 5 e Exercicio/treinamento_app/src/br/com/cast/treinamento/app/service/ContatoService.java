package br.com.cast.treinamento.app.service;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import br.com.cast.treinamento.app.R;
import br.com.cast.treinamento.app.dao.ContatoDao;
import br.com.cast.treinamento.app.domain.Contato;
import br.com.cast.treinamento.app.domain.exception.ExcecaoNegocio;

public class ContatoService {
	private static ContatoService INSTANCIA;
	private Context contexto;
	private ContatoService(Context context){
		super();
		this.contexto = context;
	}
	
	public static ContatoService getInstancia(Context context) {
		if (INSTANCIA == null) {
			INSTANCIA = new ContatoService(context);
			
		}

		return INSTANCIA;
	}
	
	public List<Contato> listarTodos() {
		return ContatoDao.getInstancia(contexto).listarTodos();
	}
	
	public void salvar(Contato contato) throws ExcecaoNegocio{
		ExcecaoNegocio excecao = new ExcecaoNegocio();
		
		if(TextUtils.isEmpty(contato.getNome())){		
			excecao.getMapaErros().put(R.id.txtNome, R.string.erro_obrigatorio);
		}
		if(TextUtils.isEmpty(contato.getEndereco())){		
			excecao.getMapaErros().put(R.id.txtEndereco, R.string.erro_obrigatorio);
		}
		if(TextUtils.isEmpty(contato.getSite())){		
			excecao.getMapaErros().put(R.id.txtSite, R.string.erro_obrigatorio);
		}
		if(TextUtils.isEmpty(contato.getTelefone())){		
			excecao.getMapaErros().put(R.id.txtTelefone, R.string.erro_obrigatorio);
		}
		
		if(!excecao.getMapaErros().isEmpty()){
			throw excecao;
		}
		ContatoDao.getInstancia(contexto).salvar(contato);
	}

	public void excluir(Contato contatoSelecionado) {
		ContatoDao.getInstancia(contexto).excluir(contatoSelecionado);
	}

}
