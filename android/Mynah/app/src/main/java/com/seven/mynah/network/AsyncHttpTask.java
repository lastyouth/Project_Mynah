package com.seven.mynah.network;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;


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
import org.json.JSONObject;

import com.google.api.client.http.MultipartContent;
import com.seven.mynah.globalmanager.GlobalVariable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;


public class AsyncHttpTask extends AsyncTask<Void, Void, String> {
	
	private Handler mhandler;
	int DataContent;
	int handlernum = 1;

	String responseString;
	String fileName;
	String _url;
	Context mContext;
	JSONObject _jobj;
	
	private static String TAG = "AsyncHttpTask";
	
	public AsyncHttpTask(Context context, String urls, Handler handler,
			JSONObject jobj, int hnum, int Data) {

		mhandler = handler;
		mContext = context;
		_url = urls;
		
		handlernum = hnum;
		DataContent = Data;
		_jobj = jobj;


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
		// Log.i("URL", url);
		Log.d(TAG,"task 시작 : " + _url );
		return Task(_url,_jobj);

	}

	@Override
	public void onPreExecute() {
		// Log.i("Test", "onPreExecute Called on global");

	}

	@Override
	protected void onPostExecute(String responseData) {

		Log.d(TAG, "Handle Type : " + handlernum);
		Log.d(TAG, "Data Type : " + DataContent);
		Log.d(TAG, "Return Data : " + responseString);

		Message msg = mhandler.obtainMessage();
		msg.what = handlernum;
		msg.obj = responseString;
		msg.arg1 = DataContent;
		mhandler.sendMessage(msg);

		Log.d(TAG,"task 종료 : " + _url );

	}

	public String Task(String urlString, JSONObject jobj) {

		HttpClient httpClient = getHttpClient();
		
		try {
            URI _url = new URI(urlString);

            HttpPost httpPost = new HttpPost(_url);

            String encodedJSON = Base64.encodeToString(jobj.toString().getBytes(), 0);
            StringEntity entity = new StringEntity(encodedJSON, "UTF-8");


            System.out.println("send : " + jobj.toString());
            System.out.println("encoded : " + encodedJSON);
            
            entity.setContentType("application/json");

            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
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