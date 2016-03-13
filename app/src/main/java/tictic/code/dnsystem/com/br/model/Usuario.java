package tictic.code.dnsystem.com.br.model;

/**
 * Created by dnsfirmino on 01/11/15.
 */
public class Usuario {
    private int codigo;
    private String nome;
    private String sobrenome;
    private int nascimentoDia;
    private int nascimentoMes;
    private String email;
    private String senha;


    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public int getNascimentoDia() {
        return nascimentoDia;
    }

    public void setNascimentoDia(int nascimentoDia) {
        this.nascimentoDia = nascimentoDia;
    }

    public int getNascimentoMes() {
        return nascimentoMes;
    }

    public void setNascimentoMes(int nascimentoMes) {
        this.nascimentoMes = nascimentoMes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return nome;
    }
}
