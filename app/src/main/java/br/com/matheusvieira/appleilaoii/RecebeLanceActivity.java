package br.com.matheusvieira.appleilaoii;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class RecebeLanceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtObra;
    private TextView txtLanceMin;
    private EditText edtCliente;
    private EditText edtEmail;
    private EditText edtLance;
    private Button btnEnviar;
    private double lanceMin;
    private TextView txtMensa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recebe_lance);

        txtObra = (TextView) findViewById(R.id.txtObra);
        txtLanceMin = (TextView) findViewById(R.id.txtLanceMin);
        edtCliente = (EditText) findViewById(R.id.edtCliente);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtLance = (EditText) findViewById(R.id.edtLance);
        btnEnviar = (Button) findViewById(R.id.btnEnviar);
        txtMensa = (TextView) findViewById(R.id.txtMensa);

        Intent it = getIntent();
        String obra = it.getStringExtra("obra");
        lanceMin = it.getDoubleExtra("lanceMin", -1);

        txtObra.setText("Obra em Leilão: " + obra);

        NumberFormat nfNr = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        txtLanceMin.setText("Lance Mínimo: "+nfNr.format(lanceMin));

        btnEnviar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String cliente = edtCliente.getText().toString();
        String email = edtEmail.getText().toString();
        String lance = edtLance.getText().toString();

        if(cliente.trim().isEmpty() || email.trim().isEmpty() || lance.trim().isEmpty()){
            Toast.makeText(this, "Erro... Preencha os campos!", Toast.LENGTH_LONG).show();
            edtCliente.requestFocus();
            return;
        }

        if(Double.parseDouble(lance)<lanceMin){
            Toast.makeText(this, "Erro... Lance inferiror ao minimo!", Toast.LENGTH_LONG).show();
            edtLance.requestFocus();
            return;
        }

        EnviaLance enviaLance = new EnviaLance(txtMensa);
        enviaLance.execute("http://192.168.200.3/edecio/gravalance.php", cliente, email, lance);
    }
}
