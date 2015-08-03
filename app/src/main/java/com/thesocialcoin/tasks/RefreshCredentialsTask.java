package com.thesocialcoin.tasks;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 29/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */

import android.accounts.Account;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.Scopes;
import com.thesocialcoin.R;
import com.thesocialcoin.controllers.AccountManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

/**
 * A task to refresh credentials after adding facebook logins
 */
	/*
	 * Added Google login to RefreshCredentials Overloaded constructor
	 */
public class RefreshCredentialsTask extends AsyncTask<Void, Void, String> {

    private static String TAG = RefreshCredentialsTask.class.getSimpleName();

    private iRefreshCredentialsTask listener;

    //ProgressDialog dialog;
    //Session session;
    AccountManager.LoginType identityType;
    Context context = null;
    String googleAccountName;
    String accesTokenUrl;


//    public RefreshCredentialsTask(Session session) {
//        this.session = session;
//        this.identityType = AccountManager.LoginType.FACEBOOK;
//    }

    public RefreshCredentialsTask(AccountManager.LoginType identityType, Context context, iRefreshCredentialsTask listener) {
        super();
        this.identityType = identityType;
        this.context = context;
        this.listener=listener;
    }

    public RefreshCredentialsTask(AccountManager.LoginType identityType, iRefreshCredentialsTask listener) {
        super();
        this.identityType = identityType;
        this.listener=listener;
    }

    // Google & Linkedin Token fetcher
    public RefreshCredentialsTask(AccountManager.LoginType identityType, Context context, String accountNameAccesUrl, iRefreshCredentialsTask listener) {
        super();
        this.identityType = identityType;
        this.context = context;
        switch (identityType) {
            case GOOGLE:
                this.googleAccountName = accountNameAccesUrl;
                break;
            case LINKEDIN:
                this.accesTokenUrl = accountNameAccesUrl;
                break;
        }

        this.listener=listener;
    }


    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... params) {

        String accessToken = null;
        switch (identityType) {

            case GOOGLE:

                Log.i(TAG, "google clientid: " + context.getResources().getString(R.string.bc_google_client_id));

                try {
                    accessToken = fetchGoogleToken();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "Google Authorization token: " + accessToken);
                //Add Google Access Token to Cognito Sessions Provider

                break;
            case FACEBOOK:
                //Log.i(TAG, "Facebook token: " + accessToken);
                //Add Facebook Session Token to Cognito Sessions Provider

                break;
            case LINKEDIN:

                    String url = this.accesTokenUrl;
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpost = new HttpPost(url);
                    try{
                        HttpResponse response = httpClient.execute(httpost);
                        if(response!=null){
                            //If status is OK 200
                            if(response.getStatusLine().getStatusCode()==200){
                                String result = EntityUtils.toString(response.getEntity());
                                //Convert the string result to a JSON Object
                                JSONObject resultJson = new JSONObject(result);
                                //Extract data from JSON Response
                                int expiresIn = resultJson.has("expires_in") ? resultJson.getInt("expires_in") : 0;

                                accessToken = resultJson.has("access_token") ? resultJson.getString("access_token") : null;
                                Log.e("Tokenm", ""+accessToken);
                                if(expiresIn>0 && accessToken!=null){
                                    Log.i("Authorize", "This is the access Token: "+accessToken+". It will expires in "+expiresIn+" secs");

                                    //Calculate date of expiration
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.add(Calendar.SECOND, expiresIn);
                                    long expireDate = calendar.getTimeInMillis();


                                }
                            }
                        }
                    }catch(IOException e){
                        Log.e("Authorize","Error Http response "+e.getLocalizedMessage());
                    } catch (JSONException e) {
                        Log.e("Authorize", "Error Parsing Http response " + e.getLocalizedMessage());
                    }


                Log.i(TAG, "Linkedin Authorization token: " + accessToken);

                break;
            default:
                break;

        }
        return accessToken;

    }

    @Override
    protected void onPostExecute(String token) {
        // we communicate de token and force the social type
        listener.onFetchSocialTokenComplete(token, identityType);
    }

    /**
     * Gets an authentication token from Google and handles any
     * GoogleAuthException that may occur.
     */
    protected String fetchGoogleToken() throws IOException {

        String accountName = this.googleAccountName;
        Account account = new Account(accountName, GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String scopes = "oauth2:" + Scopes.PLUS_LOGIN;

        try {
            return GoogleAuthUtil.getToken(context, account, scopes);
        } catch (UserRecoverableAuthException userRecoverableException) {
            // GooglePlayServices.apk is either old, disabled, or not present
            // so we need to show the user some UI in the activity to recover.
            //mActivity.handleException(userRecoverableException);
            userRecoverableException.printStackTrace();
        } catch (GoogleAuthException fatalException) {
            // Some other type of unrecoverable exception has occurred.
            // Report and log the error as appropriate for your app.
            fatalException.printStackTrace();
        }
        return null;
    }

}
