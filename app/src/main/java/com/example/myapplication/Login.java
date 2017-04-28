package com.example.myapplication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import android.content.Intent;
import android.app.FragmentManager;
import android.net.http.AndroidHttpClient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import javax.crypto.Cipher;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import android.util.Base64;

public class Login extends AppCompatActivity {


    private String username;
    private String password;
    private FragmentManager manager;
    private int sessionID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = (Button) findViewById(R.id.login_button);
       /* login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Login.this, MainActivity.class));
            }
        });
        */
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText usernameObj = (EditText) findViewById(R.id.userName);
                username = usernameObj.getText().toString();
                EditText passwordObj = (EditText) findViewById(R.id.password);
                password = passwordObj.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    manager = getFragmentManager();
                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.show(manager, "fragment_login_error");
                } else if (username.equals("155042163326969") && password.equals("3$KmPJSFe*YtjzmlJ")) {

                    sessionID = LoginAuthenticate(username, password);
                    Intent i = new Intent(Login.this, MainActivity.class);
                    i.putExtra("KEY", username);
                    i.putExtra("SessionID", sessionID);
                    startActivity(i);
                } else {
                    manager = getFragmentManager();
                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.show(manager, "fragment_login_error");
                }
            }
        });
    }

        /*Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText usernameObj = (EditText) findViewById(R.id.userName);
                username = usernameObj.getText().toString();
                EditText passwordObj = (EditText) findViewById(R.id.password);
                password = passwordObj.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    manager = getFragmentManager();
                    AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                    alertDialogFragment.show(manager, "fragment_logout_error");
                } else {
                    LoginAuthenticate(username, password);
                    Intent i = new Intent(Logout.this, MainActivity.class);
                    i.putExtra("KEY", username);
                    i.putExtra("SessionID", sessionID);
                    startActivity(i);
                }
            }
        });*/


    //}

    public static String getRequest() throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://54.218.252.173/sms/getkey.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST"); //was GET
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        String[] results = result.toString().split("-----");
        return results[2];
    }

    public static byte[] encrypt(String text, PublicKey key) {
        byte[] cipherText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");
            // encrypt the plain text using the public key
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    public int LoginAuthenticate(String username, String password) {
        try {
            /*URL url = new URL("http://54.218.252.173/sms/login.php");
            String ServerPubkey = getRequest();
            byte[] publicBytes = Base64.decode(ServerPubkey, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey ServerpubKey = keyFactory.generatePublic(keySpec);
            byte[] usernameE, passwordE;
            usernameE = encrypt(username, ServerpubKey);
            passwordE = encrypt(username, ServerpubKey);
            String urlParameters = ("user=AyMfxgP2/vXFnZx2l9AulT2yIrvV9Uh4MIgNW3cgNE3HeQ9kqgZiywOmpKeGQXCvHsxZ7cE8z3iNl+fCivfhKoPyc1YTz/lJSYgUN6b6RPq2nMw6fR3AOIwUKcLTyYV1RHYCpYQL3WWOZub5oJkm2+mBAEanKNYn5kaFBfItL0YoslPirA6mmuchiEEU4MjNEwUMoXqHVukBp9lD5oTptQu22OElIQzeXjFZC2lOCMIXFQ0eIIZnl8GUTKcI4vntP+YcKF8zdgxqk+IdsbdPL5Aue3dovN1TVHX0fyx6pbrUxCaQdm9QeRJ8EhZQqvN0p0XnM/+3RCFoGc2aqFiiqQ==" +
                    "&pass=abcAJAVHH5a/E3QQlDmykFyd0ioPoKBUZRVhWYoVrnXQ4jIlUoyxDzxv8WIhM5wRv5bLAE8DSbEAWNvR7RpPw1Mye4WC9339ksIsfS7UNKMyrMkY43RTx2yobcQ2F6sL2TPuiibPIiyRFMh8ZGM/7gQ1Z9K2QTtOG3n7t80c7ik/BfjpUZ/11jCOI92+5a33W3JGdmjvp7NvbSUw25SLDDSztczsZviBcQLL/N6+q4JXHvqvRS9FS0OwlTtaywodM9IZc9H9Cyd/GmuU5mJUcjhlNnw+nDZqIv5yh4JI7CGhqIhjItHos2H4JlIgUC1DLgDov00LCqKCrnwDK4Xtg==");
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
            int postDataLength = postData.length;

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            connection.setUseCaches(false);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postData);
            }

            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(content));
            String line = "";
            while (line != null) {

                line = in.readLine();
                System.out.println(line);
            }
            System.out.println("MADE IT");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sessionID;*/

            //}

           /* URL urlToRequest = new URL("http://54.218.252.173/sms/login.php");
            HttpURLConnection urlConnection =
                    (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            String postParameters = ("user=AyMfxgP2/vXFnZx2l9AulT2yIrvV9Uh4MIgNW3cgNE3HeQ9kqgZiywOmpKeGQXCvHsxZ7cE8z3iNl+fCivfhKoPyc1YTz/lJSYgUN6b6RPq2nMw6fR3AOIwUKcLTyYV1RHYCpYQL3WWOZub5oJkm2+mBAEanKNYn5kaFBfItL0YoslPirA6mmuchiEEU4MjNEwUMoXqHVukBp9lD5oTptQu22OElIQzeXjFZC2lOCMIXFQ0eIIZnl8GUTKcI4vntP+YcKF8zdgxqk+IdsbdPL5Aue3dovN1TVHX0fyx6pbrUxCaQdm9QeRJ8EhZQqvN0p0XnM/+3RCFoGc2aqFiiqQ==" +
                    "&pass=abcAJAVHH5a/E3QQlDmykFyd0ioPoKBUZRVhWYoVrnXQ4jIlUoyxDzxv8WIhM5wRv5bLAE8DSbEAWNvR7RpPw1Mye4WC9339ksIsfS7UNKMyrMkY43RTx2yobcQ2F6sL2TPuiibPIiyRFMh8ZGM/7gQ1Z9K2QTtOG3n7t80c7ik/BfjpUZ/11jCOI92+5a33W3JGdmjvp7NvbSUw25SLDDSztczsZviBcQLL/N6+q4JXHvqvRS9FS0OwlTtaywodM9IZc9H9Cyd/GmuU5mJUcjhlNnw+nDZqIv5yh4JI7CGhqIhjItHos2H4JlIgUC1DLgDov00LCqKCrnwDK4Xtg==");
            urlConnection.setFixedLengthStreamingMode(
                    postParameters.getBytes().length);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(postParameters);
            out.close();

        */
        } catch (Exception e) {
        }

    return 0;
}
}


