package com.traviswu.gravitydroid;

import android.content.Context;
import android.util.Log;
import android.os.AsyncTask;
import com.twilio.client.Connection;
import com.twilio.client.Connection.State;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by traviswu on 2015-03-12.
 */
public class MonkeyPhone implements Twilio.InitListener{
    private static final String TAG = "MonkeyPhone";
    private Device device;
    private Connection connection;
    public MonkeyPhone(Context context)
    {
        Twilio.initialize(context, this /* Twilio.InitListener */);
    }

    /* Twilio.InitListener method */
    @Override
//    public void onInitialized()
//    {
//
//        Log.d(TAG, "Twilio SDK is ready");
//        try {
//            Log.d(TAG, "test");
//            String capabilityToken;
//            capabilityToken = HttpHelper.httpGet("http://gdroid.herokuapp.com/token");
//            //String capabilityToken = "eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.eyJzY29wZSI6ICJzY29wZTpjbGllbnQ6b3V0Z29pbmc_YXBwU2lkPUFQNTJiMzkyMzQ2ZDIyNTEzYmNiYmIyNmI4OTIwNzlhNjIiLCAiaXNzIjogIkFDMTg1YWE3ZDdmMDNkYWQ1YWE4YzEwMjk4MzhiZTExMjYiLCAiZXhwIjogMTQyNzIzNTI0NH0.6zl6nPV4-i2YS-xb_CA5TsMRohWVxFkjS6Ghre6H2Jk";
//            device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
//
//        } catch (Exception e) {
//            Log.e(TAG, "Failed to obtain capability token: " + e.getLocalizedMessage());
//        }
//    }
public void onInitialized()

{

    Log.d(TAG, "Twilio SDK is ready");

// This runs asynchronously!

  new GetAuthTokenAsyncTask().execute("http://gdroid.herokuapp.com/token");

}
    public void onTokenObtained (String capabilityToken)

    {

        device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);

    }
    /* Twilio.InitListener method */

    @Override
    public void onError(Exception e)
    {
        Log.e(TAG, "Twilio SDK couldn't start: " + e.getLocalizedMessage());
    }

    @Override
    protected void finalize()
    {
        if (connection != null)
            connection.disconnect();
        if (device != null)
            device.release();
    }

    public void connect(String phoneNumber) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("To", phoneNumber);
        connection = device.connect(parameters, null /* ConnectionListener */);
        if (connection == null)
            Log.w(TAG, "Failed to create new connection");

    }

    public void disconnect()
    {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
        Twilio.shutdown();
    }

    public State status()
    {
        connection.getState();
        State statusHere = connection.getState();
        return statusHere;
    }
    private class GetAuthTokenAsyncTask extends AsyncTask<String, Void, String> {

        @Override

        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            MonkeyPhone.this.onTokenObtained(result);

        }

        @Override

        protected String doInBackground(String... params) {

            String capabilityToken = null;

            try {

                capabilityToken = HttpHelper.httpGet(params[0]);

            } catch (Exception e) {

                e.printStackTrace();

            }

            return capabilityToken;

        }

    }

}




