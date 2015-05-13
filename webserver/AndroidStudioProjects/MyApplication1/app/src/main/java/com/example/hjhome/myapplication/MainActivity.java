package com.example.hjhome.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    Button sendToServerButton;
    EditText idInputTextfield;
    EditText passwdInputTextfield;
    Button loginButton;

    EditText ttsSentenseTextfield;
    Button sendTTSButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Run");



        sendToServerButton = (Button) findViewById(R.id.send_to_server_button);
        sendToServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               System.out.println("click Send To Server Button");
            }
        });



        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                idInputTextfield = (EditText) findViewById(R.id.id_input_textfield);
                passwdInputTextfield = (EditText) findViewById(R.id.passwd_input_textfield);

                final String idInputStr = idInputTextfield.getText()+"";
                final String passwdInputStr = passwdInputTextfield.getText()+"";


                System.out.println("id : " + idInputStr + " / passwd : " + passwdInputStr);
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
                                jobj.put("messagetype", "login");
                                jobj.put("id", idInputStr);
                                jobj.put("passwd", passwdInputStr);
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

        sendTTSButton = (Button) findViewById(R.id.send_tts_button);
        sendTTSButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ttsSentenseTextfield = (EditText) findViewById(R.id.tts_sentence_textfield);
                final String ttsSentense = ttsSentenseTextfield.getText() + "";
                System.out.println("sentense : " + ttsSentense);
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
                                jobj.put("messagetype", "send_tts");
                                jobj.put("sentense", ttsSentense);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
