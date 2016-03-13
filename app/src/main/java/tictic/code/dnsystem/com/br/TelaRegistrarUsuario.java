package tictic.code.dnsystem.com.br;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import tictic.code.dnsystem.com.br.util.Funcoes;

/**
 * Created by dnsfirmino on 30/10/15.
 */
public class TelaRegistrarUsuario extends AppCompatActivity {
    //Variaveis que serão utilizadas para ligar Java com XML
    Context mContext;

    private EditText et_nome;
    private EditText et_sobrenome;
    private EditText et_telefone;
    private EditText et_dtnascimento_dia;
    private EditText et_dtnascimento_mes;
    private EditText et_email;
    private EditText et_senha;
    private EditText et_confirmacao_senha;

    private Button btn_salvar;

    //Variavel responsavel por guarda o log do webservice
    private String logWs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Identifica qual será o xml que será aberto quando for chamado a classe
        //TelaRegistrar usuário
        setContentView(R.layout.telaregistrarusuario);

        //Modificando o título da Activity
        setTitle(getString(R.string.telaregistrarusuario_titulo));


        //Métodos base da activity
        inicializaVariavel();
        inicializaAcao();

    }

    //Inicializando as variaveis
    private void inicializaVariavel() {
        mContext = getBaseContext();

        //EditText
        et_nome = (EditText) findViewById(R.id.telaregistrarusuario_et_nome);
        et_sobrenome = (EditText) findViewById(R.id.telaregistrarusuario_et_sobrenome);
        et_email = (EditText) findViewById(R.id.telaregistrarusuario_et_email);
        et_senha = (EditText) findViewById(R.id.telaregistrarusuario_et_senha);
        et_confirmacao_senha = (EditText) findViewById(R.id.telaregistrarusuario_et_confirmacao_senha);
        et_telefone = (EditText) findViewById(R.id.telaregistrarusuario_et_telefone);
        et_dtnascimento_dia = (EditText) findViewById(R.id.telaregistrarusuario_et_dtnascimento_dia);
        et_dtnascimento_mes = (EditText) findViewById(R.id.telaregistrarusuario_et_dtnascimento_mes);

        //Button
        btn_salvar = (Button) findViewById(R.id.telaregistrarusuario_btn_salvar);
    }

    //Método responsavel pelas ações da activity telalogin
    private void inicializaAcao() {
        //Ação do botão salvar
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validaCampos()){

                }
            }
        });
    }

    //Método de validação dos campos
    private boolean validaCampos() {
        //Resgatando os valores que estão no EdiText
        String nome = et_nome.getText().toString().trim();
        String sobrenome = et_sobrenome.getText().toString().trim();
        String telefone = et_telefone.getText().toString().trim();
        String dtnascimentoDia =  et_dtnascimento_dia.getText().toString().trim();
        String dtnascimentoMes = et_dtnascimento_mes.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String senha = et_senha.getText().toString().trim();
        String confirmacaoSenha = et_confirmacao_senha.getText().toString().trim();

        //Verificando se o nome esta vazio
        if (nome.length() == 0){
            et_nome.setError(getString(R.string.telaregistrarusuario_msgerro_et_nome));
            return false;
        }

        //Verificando se o campo sobrenome esta vazio
        if (sobrenome.length() == 0){
            et_sobrenome.setError(getString(R.string.telaregistrarusuario_msgerro_et_sobrenome));
            return false;
        }

        //Verificando se o campo email esta vazio e o formato do email esta correto
        if (email.length() == 0){
            et_email.setError(getString(R.string.telaregistrarusuario_msgerro_et_email));
            return false;
        }else{
            if (!Funcoes.validaEmail(email)){
                et_email.setError(getString(R.string.telalogin_msgerro_et_email_email_formato_incorreto));
                return false;
            }
        }

        //Verificando se o campo telefone esta vazio
        if (telefone.length() == 0){
            et_telefone.setError("O número de telefone é obrigatório!");
            return false;
        }

        //Verificando se o campo dia do nascimento esta vazio
        if (dtnascimentoDia.length() == 0){
            et_dtnascimento_dia.setError("O dia do nascimento é obrigatório!");
            return false;
        }else{
            try {
                //
                int dia = Integer.parseInt(dtnascimentoDia.toString().trim());

                //Verifica se o número digitado esta entre 1 até 31
                if ((dia == 0) || (dia > 31)){
                    et_dtnascimento_dia.setError("O número digitado não é válido!");
                    return false;
                }

            }catch (Exception e ){

            }
        }

        //Verificando se o campo mês do nascimento esta vazio
        if (dtnascimentoMes.length() == 0){
            et_dtnascimento_mes.setError("O mês do nascimento é obrigatório!");
            return false;
        }else{
            try {
                //
                int mes = Integer.parseInt(dtnascimentoMes.toString().trim());

                //
                if (mes == 0 || mes > 12){
                    et_dtnascimento_mes.setError("O número digitado não é válido!");
                    return false;
                }
            }catch (Exception e){

            }
        }

        //Verificando se o campo senha esta vazio
        if (senha.length() == 0){
            et_senha.setError(getString(R.string.telaregistrarusuario_msgerro_et_senha));
            return false;
        }else{
            if (confirmacaoSenha.length() == 0){
                et_confirmacao_senha.setError(getString(R.string.telaregistrarusuario_msgerro_et_confirmacao_senha));
                return false;
            }else{
                //Verificando se o conteúdo do campo senha e confirmação não são iguais
                if (!senha.equals(confirmacaoSenha)){
                    Toast.makeText(mContext, R.string.telaregistrarusuario_msgerro_senhasnaosaoiguais,
                                   Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    //Método responsável por
    private class GravaUsuario extends AsyncTask<Void, Integer, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    //Método que será executado quando o usuário clicar no
    //botão principal voltar do aparelho
    @Override
    public void onBackPressed() {
        //Criando uma intent e atribuindo a classe MainActivity
        //Encerrando a activity Registrar Usuário
        Intent mIntent = new Intent(mContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }
}
