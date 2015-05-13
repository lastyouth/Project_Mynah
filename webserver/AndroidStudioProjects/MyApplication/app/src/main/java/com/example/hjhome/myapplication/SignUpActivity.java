package com.example.hjhome.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hjhome.myapplication.common.SFSSLSocketFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;

/**
 * Created by HJHOME on 2015-05-11.
 */
public class SignUpActivity extends ActionBarActivity {

    EditText signUpIdTextfield;
    Button signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                signUpIdTextfield = (EditText)findViewById(R.id.sign_up_id_textfield);
                final String signUpIdStr = signUpIdTextfield.getText() + "";

                System.out.println("sign up input id : " + signUpIdStr);
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        HttpClient httpClient = getHttpClient();
                        //HttpClient httpClient = new DefaultHttpClient();

                        String urlString = "https://192.168.35.75";
                        try {
                            URI url = new URI(urlString);

                            HttpPost httpPost = new HttpPost(urlString);
                            //httpPost.setURI(url);

                            JSONObject jobj = new JSONObject();
                            try{
                                jobj.put("messagetype", "signup");
                                jobj.put("product_id", "product2");
                                jobj.put("family_id", "family02");
                                jobj.put("user_id", signUpIdStr);
                                jobj.put("registration_id", "23QWE323EW3");
                                jobj.put("user_name", signUpIdStr+"_name");
                                jobj.put("RRN", "123456-0987654");
                                jobj.put("gender_flag", "1");
                                jobj.put("representative_flag", "0");
                                jobj.put("in_home_flag", "1");
                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                            /*
                            List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(2);
                            nameValuePairs.add(new BasicNameValuePair("id", idInputStr));
                            nameValuePairs.add(new BasicNameValuePair("passwd", passwdInputStr));
                            */

                            String encodedJSON = Base64.encodeToString(jobj.toString().getBytes(), 0);
                            StringEntity entity = new StringEntity(encodedJSON, "UTF-8");
                            System.out.println("send : " + jobj.toString());
                            System.out.println("encoded : " + encodedJSON);
                            entity.setContentType("application/json");

                            httpPost.setEntity(entity);

                            HttpResponse response = httpClient.execute(httpPost);
                            String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
                            System.out.println(responseString);

                        }
                        catch(URISyntaxException e) {
                            System.out.println("1");
                            e.printStackTrace();
                        }

                        catch (ClientProtocolException e) {
                            System.out.println("2");
                            e.printStackTrace();
                        }

                        catch (IOException e) {
                            System.out.println("3");
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });

    }

    private HttpClient getHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SFSSLSocketFactory(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 13337));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
