package tictic.code.dnsystem.com.br;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import tictic.code.dnsystem.com.br.model.Transmissao_Env;
import tictic.code.dnsystem.com.br.model.Transmissao_Rec;
import tictic.code.dnsystem.com.br.util.Funcoes;
import tictic.code.dnsystem.com.br.util.ToolBox;

public class MainActivity extends AppCompatActivity {
    //Variaveis que serão utilizadas para ligar Java com XML
    private EditText et_email;
    private EditText et_senha;
    private TextView tv_esqueci_senha;

    private Button btn_registrar;
    private Button btn_acessar;

    private ProgressDialog progressDialog;

    private Context mContext;
    private String logWs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogin);

        //Modificando o título da Activity tela de login
        setTitle(getString(R.string.telalogin_titulo_activity));

        //Métodos base
        inicializaVariavel();
        inicializaAcao();

    }

    //Inicializando as variaveis
    private void inicializaVariavel() {
        //Inicializando o context
        mContext = getBaseContext();

        //Edit Text
        et_email = (EditText) findViewById(R.id.telalogin_et_email);
        et_senha = (EditText) findViewById(R.id.telalogin_et_senha);

        //Buttons
        btn_registrar = (Button) findViewById(R.id.telalogin_btn_registrar);
        btn_acessar = (Button) findViewById(R.id.telalogin_btn_acessar_conta);

        //TextView
        tv_esqueci_senha = (TextView) findViewById(R.id.telalogin_tv_esqueci_senha);
    }

    //Método responsavel pelas ações da activity telalogin
    private void inicializaAcao() {
        //Ação do botão Registrar
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Criando uma intent e atribuindo a classe tela de registro do usuário
                //Encerrando a activity de login
                Intent mIntent = new Intent(mContext, TelaRegistrarUsuario.class);
                startActivity(mIntent);
                finish();
            }
        });

        //Método do botão acessar
        btn_acessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Verifica se os campos estão preenchidos
                if (validaCampos()) {

                    //Executa o método de sincronização/Autenticação do login
                    new SincronizaUsuario().execute();
                }
            }
        });

        //Método do TextView esqueci minha senha
        tv_esqueci_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Criando intent e atribuindo a clase Tela Esqueci Senha
                //Encerrando a activity da Tela de Login
                Intent mIntent = new Intent(mContext, TelaEsqueciSenha.class);
                startActivity(mIntent);
                finish();
            }
        });
    }

    //Método de validação dos campos da activity
    private boolean validaCampos() {
        //Resgatando os valores que estão nos edittext
        String email = et_email.getText().toString().trim();
        String senha = et_senha.getText().toString().trim();

        //Verifica se o campo email esta vazio
        if (email.length() == 0){
            et_email.setError(getString(R.string.telalogin_mgserro_et_email_campo_vazio));
            return false;
        }else{
            //Verifica se o email digitado esta no formato correto
            if(!Funcoes.validaEmail(email)){
                et_email.setError(getString(R.string.telalogin_msgerro_et_email_email_formato_incorreto));
                return false;
            }
        }

        //Verifica se o campo senha esta vazio
        if (senha.length() == 0){
            et_senha.setError(getString(R.string.telalogin_msgerro_et_senha_campo_vazio));
            return false;
        }

        return true;
    }

    private class SincronizaUsuario extends AsyncTask<Void, Integer, Void>{

        //Pega os dados digitados no Edit Text
        String email = et_email.getText().toString().trim();
        String senha = ToolBox.md5(et_senha.getText().toString().trim());//Converte senha em MD5
        String funcao = "funcao do ws";

        @Override
        protected void onPreExecute() {

            //Exibe um mensage de dialogo para usuário
            //notificando que os dados estão sendo validados no webservice
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setCancelable(false);//Não pode cancelar a dialog
            progressDialog.setTitle(getString(R.string.telalogin_msg_dialogo_titulo)); //Titulo
            progressDialog.setMessage(getString(R.string.telalogin_msg_dialogo_mensagem)); //Mensagem

            //Exibe dialog
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            //Inserindo toda a regra de conexão em um try/cath
            try{

                //Cria e inicializa um objeto Gson
                Gson gsonUsuario = new Gson();

                //Passa os dados que estão nas variaveis para o parametro
                Transmissao_Env transmissao_env = new Transmissao_Env();
                transmissao_env.setEmail(email);
                transmissao_env.setSenha(senha);
                transmissao_env.setFuncao(funcao);

                //Realiza conexão com o servidor webservice e passa os parametros junto
                String resultado = ToolBox.comunicacao("informe a url do ws", gsonUsuario.toJson(transmissao_env));

                //Pega o valor antes da tag WSTAG que foi recebida do webservice
                String parRes[] = resultado.split("#WSTAG#");

                //Verifica a quantidade de caracteres
                switch (parRes.length) {
                    case 2:

                        //Verifica se o resultado do webservice foi gerado corretamente(0)
                        if (parRes[0].equals("0")) {
                            //O webservice retornou alguma informação, desta forma
                            //os dados fornecido pelo usuário estão corretos
                            logWs = "0";

                            //Realiza a conversão do Json para um objeto do tipo Trasmissao_Rec
                            Transmissao_Rec rec = gsonUsuario.fromJson(parRes[1], Transmissao_Rec.class);

                        } else {
                            //O webservice retornou alguma informação, desta forma
                            //os dados fornecido pelo usuário não estão corretos
                            logWs = "1";
                        }
                        break;
                    default:
                        //Houve algum erro de conexão com o webservice
                        logWs = "2";
                        break;
                }

            }catch (Exception e){
                //Demonstra o erro do try/cath
                String erro = e.toString();

            }finally {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //Verificando o log do webservice
            if(logWs.equals("0") ){
                //Criando intent e atribuindo a clase Tela Principal
                //Encerrando a activity Tela de Login
                Intent mIntent = new Intent(mContext, TelaPrincipal.class);
                startActivity(mIntent);
                finish();
            }

            //Verifica se houve algum erro de autenticação email ou senha invalido
            if(logWs.equals("1")){
                Toast.makeText(mContext, R.string.telalogin_msgerro_emailesenha_incorretos,
                                Toast.LENGTH_LONG).show();
            }

            //Verifica se houve algum erro no conexão com o webservice
            if (logWs.equals("2")){
                Toast.makeText(mContext, R.string.telalogin_msgerro_conexao_com_servidor,
                        Toast.LENGTH_LONG).show();

            }

            //Finaliza a caixa de dialogo
            progressDialog.dismiss();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
