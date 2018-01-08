package br.com.matheusvieira.appleilaoii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private TextView txtObra;
    private TextView txtPath;
    private ImageView imgObra;
    private Button btnVerObra;
    private Button btnDarLance;
    private String tituloObra;
    private double lanceMinimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtObra = (TextView) findViewById(R.id.txtObra);
        txtPath = (TextView) findViewById(R.id.txtPath);
        imgObra = (ImageView) findViewById(R.id.imgObra);
        btnVerObra = (Button) findViewById(R.id.btnVerObra);
        btnDarLance = (Button) findViewById(R.id.btnDarLance);

        AsyncResponse asyncResponse = new AsyncResponse() {
            @Override
            public void retornaDados(String titulo, double lance) {
                tituloObra = titulo;
                lanceMinimo = lance;
            }
        };

        BuscaLeilao buscaLeilao = new BuscaLeilao(txtObra, txtPath, btnVerObra, asyncResponse);
        buscaLeilao.execute("http://187.7.106.14/edecio/obradasemana.php");
    }

    public void verObra(View view) {
        String path = txtPath.getText().toString();
        Picasso.with(this).load(path).into(imgObra);
        btnDarLance.setVisibility(View.VISIBLE);
    }

    public void darLance(View view) {
        Intent it = new Intent(this, RecebeLanceActivity.class);
        it.putExtra("obra", tituloObra);
        it.putExtra("lanceMin", lanceMinimo);

        startActivity(it);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuLista) {
            Toast.makeText(this, "Ok!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}