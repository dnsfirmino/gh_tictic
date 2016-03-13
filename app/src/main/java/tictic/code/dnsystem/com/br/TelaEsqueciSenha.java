package tictic.code.dnsystem.com.br;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tictic.code.dnsystem.com.br.util.Funcoes;

/**
 * Created by dnsfirmino on 31/10/15.
 */
public class TelaEsqueciSenha extends AppCompatActivity {
    Context mContext;

    private EditText et_sobrenome;
    private EditText et_dtnascimento;
    private EditText et_email;

    private Button btn_redefinir_senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaesquecisenha);

        //Modificando o título da activity
        setTitle("Esqueci a senha");

        //Métodos de inicialização e ação da activity
        inicializaVariavel();
        inicializaAcao();
    }

    private void inicializaVariavel() {
        mContext = getBaseContext();

        et_sobrenome = (EditText) findViewById(R.id.telaesquecisenha_et_sobrenome);
        et_email = (EditText) findViewById(R.id.telaesquecisenha_et_email);

        btn_redefinir_senha = (Button) findViewById(R.id.telaesquecisenha_btn_definir_senha);
    }

    private void inicializaAcao() {
        //Método do botão redefinir senha
        btn_redefinir_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //
    private boolean validaCampos() {
        //
        String sobrenome = et_sobrenome.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String dtnascimento = et_dtnascimento.getText().toString().trim();

        //Verifica se o sobrenome esta vazio
        if (sobrenome.length() == 0){
            et_sobrenome.setError(getString(R.string.telaregistrarusuario_et_sobrenome));
            return false;
        }

        //Verifica se o email esta vazio e o formato esta correto
        if (email.length() == 0){
            et_email.setError(getString(R.string.telaregistrarusuario_et_email));
            return false;
        }else{
            if (!Funcoes.validaEmail(email)){
                et_email.setError(getString(R.string.telaregistrarusuario_msgerro_et_email));
                return false;
            }
        }

        return true;
    }

    //Método que será executado quando o usuário clicar no
    //botão principal voltar do aparelho
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //Criando uma intent e atribuindo a classe MainActivity
        //Encerrando a activity Registrar Usuário
        Intent mIntent = new Intent(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }
}
