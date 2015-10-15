package com.seven.mynah.network;

/**
 * Created by PSJ on 2015-08-19.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

import com.seven.mynah.artifacts.SessionUserInfo;
import com.seven.mynah.database.DBManager;
import com.seven.mynah.globalmanager.GlobalVariable;

import java.io.DataOutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.KeyStore;



import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.seven.mynah.globalmanager.GlobalVariable;

public class AsyncHttpUpload extends AsyncTask<Void, Void, String> {

    private static String TAG = "AsyncHttpUpload";

    public static final int TYPE_TTS = 1;
    public static final int TYPE_REC = 2;

    private Handler mhandler;
    int DataContent;
    int handlernum = 1;

    String responseString;
    String fileName;
    String _url;
    Context mContext;
    String _filepath;
    int serverResponseCode = 0;


    public AsyncHttpUpload(Context context, String urls, Handler handler, String filepath, int hnum, int Data) {

        mhandler = handler;
        mContext = context;
        _url = urls;

        handlernum = hnum;
        DataContent = Data;
        _filepath = filepath;

        if(!GlobalVariable.isServerOn)
        {
            Log.d(TAG,"서버가 종료되어 있어 task를 수행하지 않습니다.");
            return;
        }

        super.execute();
    }



    @Override
    protected String doInBackground(Void... urls) {

        // urls[0]의 URL부터 데이터를 읽어와 String으로 리턴

        Log.d(TAG,"upload 시작 : " + _url );
        return Task(_url,_filepath);

    }

    @Override
    public void onPreExecute() {
        // Log.i("Test", "onPreExecute Called on global");


    }

    @Override
    protected void onPostExecute(String responseData) {

        Log.d(TAG, "Handle Type : " + handlernum);
        Log.d(TAG, "Data Type : " + DataContent);
        Log.d(TAG,"Return Data : " + responseString);


        Message msg = mhandler.obtainMessage();
        msg.what = handlernum;
        msg.obj = responseString;
        msg.arg1 = DataContent;
        mhandler.sendMessage(msg);

        Log.d(TAG, "upload 종료 : " + _url);

    }

    public String Task(String urlString, String filepath) {

        File sourceFile = new File(filepath);
        String filename;

        if (!sourceFile.isFile()) {

            Log.e("uploadFile", "Source File not exist :"
                    + filepath);
            return null;
        }
        else
        {
            filename = sourceFile.getName();
            try {
                HttpClient httpClient = getHttpClient();

                URI _url = new URI(urlString + "/recording");
                HttpPost httpPost = new HttpPost(_url);

                SessionUserInfo siuinfo  = DBManager.getManager(mContext).getSessionUserDB();
                MultipartEntity entity = new MultipartEntity();

                entity.addPart("messagetype", new StringBody("upload_tts", Charset.forName("UTF-8")));
                entity.addPart("device_id", new StringBody(siuinfo.deviceId, Charset.forName("UTF-8")));
                if(DataContent == TYPE_REC)
                {
                    entity.addPart("rec_flag",new StringBody("1", Charset.forName("UTF-8")));
                }
                else if (DataContent == TYPE_TTS)
                {
                    entity.addPart("rec_flag",new StringBody("0", Charset.forName("UTF-8")));
                }
                entity.addPart("tts_file", new FileBody(sourceFile));

                httpPost.setEntity(entity);

                Log.d(TAG, ("send : " + entity.toString()));

                HttpResponse response = httpClient.execute(httpPost);
                responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                Log.d(TAG, "record : " + responseString);

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                Log.d(TAG, "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                Log.d(TAG, "Exception : " + e.getMessage(), e);
            }
        } // End else block
        return null;
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
            registry.register(new Scheme("https", sf, GlobalVariable.HTTPS_PORT));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }

    }

}