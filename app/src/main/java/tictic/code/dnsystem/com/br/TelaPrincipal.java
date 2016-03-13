package tictic.code.dnsystem.com.br;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by dnsfirmino on 30/10/15.
 */
public class TelaPrincipal extends AppCompatActivity {
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Identifica qual será o xml que será aberto quando for chamado a classe
        //TelaRegistrar usuário
        setContentView(R.layout.telaprincipal);

        //
        mContext = getBaseContext();

        //Modificando o título da activity de TelaPrincipal
        setTitle("TicTic");
    }
}
