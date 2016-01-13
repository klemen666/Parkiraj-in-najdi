package si.uni_lj.fe.tnvu.parkirajinnajdi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.appdatasearch.GetRecentContextCall;

import org.apache.http.HttpResponse;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class GPS_REST extends AsyncTask<String, Void, String>{

    private String urlStoritve="http://10.0.2.2:8080/Parkiraj/api/v3/inventory";

    private MainActivity mActivity;

    public GPS_REST(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        ConnectivityManager connMgr = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                return connect(params[0], params[1], params[2], params[3]);
            } catch (IOException e) {
                return mActivity.getResources().getString(R.string.napaka_storitev);
            }
        }
        else{
            return mActivity.getResources().getString(R.string.napaka_omrezje);
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Toast toast = Toast.makeText(mActivity, result, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
    // the content as a InputStream, which it returns as a string.
    private String connect(String latitude, String longitude, String locationAddress, String parkingTime) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved content.
        int len = 500;

        try {
            URL url = new URL(urlStoritve);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000 /* milliseconds */);
            conn.setConnectTimeout(10000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
            conn.setDoInput(true);

            HashMap<String,String> params = new LinkedHashMap<>();
            params.put("latitude", latitude);
            params.put("longitude", longitude);
            params.put("address", locationAddress);
            params.put("startTime", parkingTime);

            // Starts the query
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();

            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

}