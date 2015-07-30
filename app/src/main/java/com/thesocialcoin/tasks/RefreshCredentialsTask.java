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

import java.io.IOException;

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

    public RefreshCredentialsTask(AccountManager.LoginType identityType, Context context, String googleAccountName, iRefreshCredentialsTask listener) {
        super();
        this.identityType = identityType;
        this.context = context;
        this.googleAccountName = googleAccountName;
        this.listener=listener;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(Void... params) {


        Log.i(TAG, "google clientid: " + context.getResources().getString(R.string.bc_google_client_id));

        String accessToken = null;
        switch (identityType) {

            case GOOGLE:
                try {
                    accessToken = fetchGoogleToken();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i(TAG, "google token: " + accessToken);
                //Add Google Access Token to Cognito Sessions Provider

                break;
            case FACEBOOK:
                //Log.i(TAG, "facebook token: " + session.getAccessToken());
                //Add Facebook Session Token to Cognito Sessions Provider

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
