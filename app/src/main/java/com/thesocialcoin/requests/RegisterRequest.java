package com.thesocialcoin.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.mobdala.acapulcoopen.R;
import com.mobdala.acapulcoopen.utils.Codes;
import com.mobdala.application.models.SessionData;
import com.mobdala.application.models.pojo.MDBeacon;
import com.mobdala.application.models.pojo.Register;
import com.mobdala.controllers.MyApplication;
import com.mobdala.networking.core.RequestInterface;
import com.mobdala.networking.core.RequestManager;
import com.mobdala.networking.error.OttoErrorListenerFactory;
import com.mobdala.networking.ottovolley.core.OttoGsonPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dcacenabes on 27/10/14.
 */
public class RegisterRequest extends RequestInterface {

    protected static String TAG = RegisterRequest.class.getSimpleName();

    private static JSONObject jsonObjectParams;
    private static String URL;
    private static Context context;
    private Response.Listener mListener;
    private Response.ErrorListener mErrorListener;

    private HashMap<String,String> _params;

    public RegisterRequest() {
        _requestErrorListener = OttoErrorListenerFactory.LOGIN_ERROR_LISTENER;
    }

    public Request create(List<MDBeacon> params)
    {
        return this.create();
    }

    public Request create(HashMap<String,String> params)
    {
        this.context = MyApplication.getAppContext();
        this._params = params;

        URL = context.getResources().getString(R.string.bc_api_server_url)+"users";

        String myVersion = android.os.Build.VERSION.RELEASE; // e.g. myVersion := "1.6"
        int sdkVersion = android.os.Build.VERSION.SDK_INT;

        SessionData sessionData = new SessionData(context);


        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("email", params.get(Codes.EMAIL_VALUE));
            jsonParams.put("password", params.get(Codes.PASSWORD_VALUE));
            jsonParams.put("ttl", 1209600000);
            jsonParams.put("projectId", params.get(Codes.PROJECT_IDENTIFIER_VALUE));
            jsonParams.put("realm", params.get(Codes.REALM_VALUE));
            jsonObjectParams = jsonParams;
            String json = jsonParams.toString();
            Log.d(TAG, json);
        } catch (JSONException e){
            Log.e(TAG, e.toString());
        }

        // JSON Post Request receiving GSON pojo model
        //LoginRequest ha de ser el POJO creado
        MyApplication.getInstance().setPassword(params.get(Codes.PASSWORD_VALUE));
        OttoGsonPostRequest<Register> request = new OttoGsonPostRequest<Register>(RequestManager.EventBus, jsonObjectParams, AppRequestHelper.getInstance(context).getAuthorizationToken(), URL, Register.class, _requestErrorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(RequestManager.REQUEST_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        return request;
    }
    public Request create(){
        HashMap<String,String> params = new HashMap<String,String>();
        return this.create(params);
    }

}
