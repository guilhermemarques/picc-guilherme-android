package br.com.cast.treinamento.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button btnExemplo;
	private EditText edtText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		edtText = (EditText) findViewById(R.id.edtText);
		btnExemplo = (Button) findViewById(R.id.btnExemplo);
		btnExemplo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Toast.makeText(view.getContext(), String.format("Você digitou: %s", edtText.getText().toString()), Toast.LENGTH_LONG).show();
				Log.d("MEU DEBUG", "Você digitou: " + edtText.getText());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
