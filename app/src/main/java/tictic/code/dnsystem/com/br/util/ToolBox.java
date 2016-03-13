package tictic.code.dnsystem.com.br.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dnsfirmino on 01/11/15.
 */
public class ToolBox {

    //Criptografa String para MD5
    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //Realiza comunicação com servico externo
    public static final String comunicacao(String targetURL, String params) {
        StringBuilder sb = new StringBuilder();
        //
        URL url;
        HttpURLConnection conn = null;
        //
        try {
            url = new URL(targetURL);
            conn = (HttpURLConnection) url.openConnection();
            //
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //
            StringBuilder resultParams = new StringBuilder();
            resultParams
                    .append(URLEncoder.encode("json", "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(params, "UTF-8"));
            //
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(resultParams.toString());
            writer.flush();
            writer.close();
            os.close();
            //
            sb.append(readStream(conn.getInputStream()));
        } catch (Exception e) {
            sb.append("Erro:")
                    .append(e.toString());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        //
        return sb.toString();
    }

    private static String readStream(InputStream inputStream) {
        Reader reader = null;
        //
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        //
        try {
            reader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            //
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
        //
        return writer.toString();
    }
}
