package br.com.matheusvieira.appleilaoii;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by android on 25/09/2017.
 */

public class EnviaLance extends AsyncTask<String, Void, Integer> {

    //Envia dados para a tela

    private TextView txtMensa;

    public EnviaLance(TextView txtMensa) {
        this.txtMensa = txtMensa;
    }

    //Enviando dados

    @Override
    protected Integer doInBackground(String... strings) {
        int codRetorno = 0;
        String ws = strings[0];
        String cliente = strings[1];
        String email = strings[2];
        String valor = strings[3];

        try {
            URL url = new URL(ws);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestMethod("POST");

                DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());

                String envio = "nome="+cliente+"&email="+email+"&lance="+valor;

                out.write(envio.getBytes(StandardCharsets.UTF_8));

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                if(in != null){
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    codRetorno = Integer.parseInt(br.readLine());
                }
            } finally {
                urlConnection.disconnect();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return codRetorno;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        if(integer == 0){
            txtMensa.setText("Erro... Não foi possível enviar lance. Tente mais tarde.");
        } else {
            txtMensa.setText("Ok! Lance Registado. Aguarde Contato.");
        }
    }

}
