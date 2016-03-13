package tictic.code.dnsystem.com.br.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dnsfirmino on 30/10/15.
 */
public class Funcoes {

    //Método responsável por validar se o email digitado esta correto
    public static boolean validaEmail(String email){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    };
}
