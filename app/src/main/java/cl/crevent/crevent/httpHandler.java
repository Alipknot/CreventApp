package cl.crevent.crevent;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by JOKO on 05/06/2015.
 */


public class httpHandler {
    Context mcontext;
    // fetch data


    public httpHandler(Context mcontext) {
        this.mcontext = mcontext;
    }

    public String get(String geturl) {//metodo get para utilizar mysql
        ConnectivityManager connMgr = (ConnectivityManager)
                mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())

        {
            try {

                BufferedReader inputStream = null;

                URL myurl = new URL(geturl);

                URLConnection dc = myurl.openConnection();
                dc.setConnectTimeout(5000);
                dc.setReadTimeout(15000);

                inputStream = new BufferedReader(new InputStreamReader(
                        dc.getInputStream()));

                // read the JSON results into a string
                String result = inputStream.readLine();
                return result;
            } catch (Exception e) {
                Log.d("error", e.getMessage());
                return "ERROR";
            }
        } else {
            // display error
            return "null";
        }


    }

}
