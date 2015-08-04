package com.thesocialcoin.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.plus.Plus;
import com.squareup.otto.Subscribe;
import com.thesocialcoin.R;
import com.thesocialcoin.controllers.AccountManager;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.tasks.RefreshCredentialsTask;
import com.thesocialcoin.utils.FontUtils;
import com.thesocialcoin.utils.ToastUtils;

import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends PlusBaseActivity implements LoaderCallbacks<Cursor>, AccountManager.OnRegisterResponseListener  {

    private final static String TAG = "LoginActivity";

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RefreshCredentialsTask mAuthTask = null;


    // List of additional write permissions being requested
    private static final List<String> PERMISSIONS = Arrays.asList("user_status, email");

    // UI references.
    private View mProgressView;
    private RelativeLayout mPlusSignInButton;
    private View mLoginFormView;
    private RelativeLayout authButton;
    CallbackManager callbackManager;
    private AccessToken facebookAccessToken;

    private AccountManager.LoginType loginRequestType;



    @Nullable
    @Bind(R.id.main_phrase)
    TextView main_phrase;
    @Bind(R.id.linkedin_login_webview)
    WebView linkedin_login_webview;


    private Activity mActivity;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");

        RequestManager.EventBus.register(this);
        RequestManager.ResponseBuffer.stopAndProcess();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");

        RequestManager.ResponseBuffer.startSaving();
        RequestManager.EventBus.unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); StrictMode.setThreadPolicy(policy);
        }
        ButterKnife.bind(this);

        mActivity = this;

        //init facebook sdk and crashlitics
        FacebookSdk.sdkInitialize(getApplicationContext());

        loginRequestType = AccountManager.LoginType.NONE;

        main_phrase.setTypeface(FontUtils.getAppsRegularFont(this));

        // Find the Google+ sign in button.
        mPlusSignInButton = (RelativeLayout) findViewById(R.id.btn_gplus_auth);
        if (supportsGooglePlayServices()) {
            // Set a listener to connect the user when the G+ button is clicked.
            mPlusSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    loginRequestType = AccountManager.LoginType.GOOGLE;
                    googleSignIn();
                }
            });
        } else {
            // Don't offer G+ sign in if the app's version is too low to support Google Play
            // Services.
            mPlusSignInButton.setVisibility(View.GONE);
            return;
        }

        // Set up the login form.

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        callbackManager = CallbackManager.Factory.create();
        authButton = (RelativeLayout) findViewById(R.id.btn_facebook_auth);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                loginRequestType = AccountManager.LoginType.FACEBOOK;
                LoginManager.getInstance().logInWithReadPermissions(mActivity, PERMISSIONS);
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                authButton.setVisibility(View.GONE);
                facebookAccessToken = loginResult.getAccessToken();
                showProgress(false);
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                showProgress(true);
                                if (response.getError() != null) {
                                    // handle error
                                    ToastUtils.show(mActivity, getString(R.string.facebook_login_error));
                                    authButton.setVisibility(View.VISIBLE);
                                } else {
                                    Log.d(TAG, facebookAccessToken.getToken());  // App code
                                    //email verification
                                    String email = object.optString("email");
                                    if (email == "" || email == null) {
                                        LoginManager.getInstance().logOut();
                                        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                                (Request.Method.DELETE, "https://graph.facebook.com/v2.3/me/permissions?access_token=" + facebookAccessToken.getToken(), "", new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d(TAG, "Token: " + response.toString());
                                                        ToastUtils.show(mActivity, getString(R.string.facebook_email_validation));
                                                        showProgress(false);
                                                        authButton.setVisibility(View.VISIBLE);
                                                    }
                                                }, new Response.ErrorListener() {

                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        ToastUtils.show(mActivity, getString(R.string.facebook_login_error));
                                                        showProgress(false);
                                                        authButton.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                        // Access the RequestQueue through your singleton class.
                                        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                                        requestQueue.add(jsObjRequest);

                                    } else {
                                        AccountManager.getInstance(LoginActivity.this).authenticateWithFacebook(facebookAccessToken.getToken(), LoginActivity.this);
                                    }
                                }
                            }
                        });
                showProgress(false);
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "Mal");
            }
        });

    }

    private OAuthService mLinkedinService;
    // Linkedin Login Button
    @OnClick(R.id.btn_linkedin_auth)
    public void linkedInLogin() {

        //Show a progress dialog to the user
        showProgress(true);

        if (AccountManager.getInstance(LoginActivity.this).getLinkedinSessionToken() != null && !AccountManager.getInstance(LoginActivity.this).getLinkedinSessionToken().isEmpty()) {
            // We already have a session!
            getLinkedinSession(AccountManager.getInstance(LoginActivity.this).getLinkedinSessionToken());
            return;
        }

        mLinkedinService = new ServiceBuilder()
                .provider(LinkedInApi.class)
                .apiKey(getString(R.string.bc_linkedin_client_id))
                .apiSecret(getString(R.string.bc_linkedin_client_secret))
                .callback("oauth://linkedin")
                .scope("r_basicprofile")
                .scope("r_emailaddress")
                .build();

        String authUrl = "http://api.linkedin.com/";
        Token mLinkedinRequestToken = null;
        try {
            mLinkedinRequestToken = mLinkedinService.getRequestToken();
            authUrl = mLinkedinService.getAuthorizationUrl(mLinkedinRequestToken);
        }
        catch ( OAuthException e ) {
            e.printStackTrace();
        }


        Log.i(TAG, "Linkedin request token: " + mLinkedinRequestToken);

        // Request an Authorization Code

        linkedin_login_webview.setVisibility(View.VISIBLE);
        //Request focus for the webview
        linkedin_login_webview.requestFocus(View.FOCUS_DOWN);

        final Token finalMLinkedinRequestToken = mLinkedinRequestToken;
        linkedin_login_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                showProgress(false);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);

                if( url.startsWith(mActivity.getResources().getString(R.string.bc_linkedin_url_redirect)) ) {
                    linkedin_login_webview.setVisibility(WebView.GONE);


                            Uri uri = Uri.parse(url);

                            String verifier = uri.getQueryParameter("oauth_verifier");
                            String stateToken = uri.getQueryParameter(STATE_PARAM);
                            if(stateToken==null || !stateToken.equals(mActivity.getResources().getString(R.string.bc_linkedin_url_state))){
                                Log.e("Authorize", "State token doesn't match");
                                return true;
                            }

                            //If the user doesn't allow authorization to our application, the authorizationToken Will be null.
                            String authorizationToken = uri.getQueryParameter(RESPONSE_TYPE_VALUE);
                            if(authorizationToken==null){
                                Log.i("Authorize", "The user doesn't allow authorization.");
                                return true;
                            }
                            Log.i("Authorize", "Auth token received: "+authorizationToken);


                            // Exchange Authorization Code for a Request Token
                            getLinkedinSession(authorizationToken);


//                                        Intent intent = new Intent();
//                                        intent.putExtra("access_token", accessToken.getToken());
//                                        intent.putExtra("access_secret", accessToken.getSecret());
//                                        setResult(RESULT_OK, intent);
//                                        finish();


                }

                return false;
            }
        });

        linkedin_login_webview.loadUrl(getAuthorizationUrl());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


   /* *//**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     *//*
    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }*/

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    protected void onPlusClientSignIn() {
        AccountManager.getInstance(LoginActivity.this).authenticateWithGoogle(String.valueOf(Plus.AccountApi.getAccountName(getPlusClient())), LoginActivity.this);
    }

    @Override
    protected void onPlusClientBlockingUI(boolean show) {
        showProgress(show);
    }

    @Override
    protected void updateConnectButtonState() {
        //TODO: Update this logic to also handle the user logged in by email.
        boolean connected = (getPlusClient() != null)?getPlusClient().isConnected():false;

        //mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        //we have a google account connected so we call our API
        if(connected)
            getGoogleSession();
    }

    @Override
    protected void onPlusClientRevokeAccess() {
        // TODO: Access to the user's G+ account has been revoked.  Per the developer terms, delete
        // any stored user data here.
    }

    @Override
    protected void onPlusClientSignOut() {

    }

    /**
     * Check if the device supports Google Play Services.  It's best
     * practice to check first rather than handling this as an error case.
     *
     * @return whether the device supports Google Play Services
     */
    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) ==
                ConnectionResult.SUCCESS;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        //addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void onConnectionSuspended(int i) {
        googleSignIn();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    @Override
    public void onRegisterSucceed(APILoginResponse response) {

        //try {

            if (response.getToken() != null) {

                alert(response.getToken(), 1);
            }

            showProgress(false);
        //} catch (JSONException je) {
        /*    showProgress(false);
            alert(getString(R.string.auth_failed), 1);
            Log.e(TAG, "JSONException RegisterNewUser " + je.toString());
            je.printStackTrace();
        }*/
    }

    @Override
    public void onRegisterFailed(String error) {
        showProgress(false);
        String msgToShow = "";
        if (error != null) {
            if (getResources().getIdentifier(error, "string", LoginActivity.this.getPackageName()) != 0) {
                msgToShow = (String) getResources().getText(getResources().getIdentifier(error, "string", LoginActivity.this.getPackageName()));
            }
            if (msgToShow.equals("")) {
                msgToShow = error;
            }
        }
        ToastUtils.show(mActivity, getString(R.string.error_login_failed) + ": " + msgToShow);
    }

    /**
     * Alert amb acceptació i finalitzat de l'activity en funció dels
     * parametres.
     *
     * @param section Missatge que es mostra en l'alert.
     * @param finish Si finish val 1, treu l'usari de l'activity i si és 0 el
     *            manté a l'activity.
     */
    private void alert(String msg, final int finish) {
        AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
        myAlertDialog.setTitle(getString(R.string.action_sign_in));
        myAlertDialog.setMessage(msg);

        myAlertDialog.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        if (finish == 1)
                            finish();
                    }
                });

        myAlertDialog.show();
    }



    /**
     * **********************************
     * <p/>
     * Event Subscription handling
     */
    @Subscribe
    public void onAuthenticationEvent(AuthenticateUserEvent event) {
        showProgress(false);
        if (event.getType().equals(AuthenticateUserEvent.Type.ERROR)) {
            switch (loginRequestType) {

                case GOOGLE:
                    googleSignOut();
                    ToastUtils.show(mActivity, getString(R.string.error_login_failed) + ": " + event.getError().getErrorMessage());
                    break;
                case FACEBOOK:
                    authButton.setVisibility(View.VISIBLE);
                    ToastUtils.show(mActivity, getString(R.string.error_login_failed) + ": " + event.getError().getErrorMessage());
                    detachFacebookAccount();
                    break;
                case LINKEDIN:
                    ToastUtils.show(mActivity, getString(R.string.error_login_failed) + ": " + event.getError().getErrorMessage());
                    break;
                default:
                    break;

            }

        }

        if (event.getType().equals(AuthenticateUserEvent.Type.SUCCESS)) {
            startApp();
        }
    }


    /**
    * Starting the app
    */
    private void startApp() {
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        Bundle data = new Bundle();
        intent.putExtras(data);
        LoginActivity.this.startActivity(intent);
        LoginActivity.this.finish();
    }

    @Override
    public void onFetchSocialTokenComplete(final String token, AccountManager.LoginType identityType)
    {
        loginRequestType = identityType;
        if(token != null) {
            switch (loginRequestType) {

                case GOOGLE:
                    AccountManager.getInstance(LoginActivity.this).setGoogleSessionToken(token);
                    AccountManager.getInstance(LoginActivity.this).authenticateWithGoogle(AccountManager.getGoogleSessionToken(), LoginActivity.this);
                    break;
                case FACEBOOK:
                    //AccountManager.getGoogleSessionToken
                    break;
                case LINKEDIN:
                    AccountManager.setLinkedinSessionToken(token);
                    AccountManager.getInstance(LoginActivity.this).authenticateWithLinkedin(AccountManager.getGoogleSessionToken(), LoginActivity.this);

                    break;
                default:
                    break;

            }
        }else{
            showProgress(false);
            switch (loginRequestType) {

                case GOOGLE:
                    googleSignOut();
                case FACEBOOK:

                    break;
                case LINKEDIN:

                    break;
                default:
                    break;

            }
        }
    }

    /**
     * Function to remove the linked facebook account to the app, Ex. when APi response fails we need to detach account or IMPORTANT! when user diddn't selected email authorisation
     *
     */
    private void detachFacebookAccount() {
        LoginManager.getInstance().logOut();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.DELETE, "https://graph.facebook.com/v2.3/me/permissions?access_token=" + facebookAccessToken.getToken(), "", new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Token: " + response.toString());
                        ToastUtils.show(mActivity, getString(R.string.facebook_email_validation));
                        authButton.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastUtils.show(mActivity, getString(R.string.facebook_login_error));
                        authButton.setVisibility(View.VISIBLE);
                    }
                });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(jsObjRequest);
    }


    //These are constants used for build the urls
    private static final String AUTHORIZATION_URL = "https://www.linkedin.com/uas/oauth2/authorization";
    private static final String ACCESS_TOKEN_URL = "https://www.linkedin.com/uas/oauth2/accessToken";
    private static final String SECRET_KEY_PARAM = "client_secret";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String RESPONSE_TYPE_VALUE ="code";
    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String STATE_PARAM = "state";
    private static final String SCOPE_PARAM = "scope";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";
    /*---------------------------------------*/
    private static final String QUESTION_MARK = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUALS = "=";

    /**
     * Method that generates the url for get the access token from the Service
     * @return Url
     */
    private String getAccessTokenUrl(String authorizationToken){
        String encodedurl = "";
        try {
            encodedurl = URLEncoder.encode(mActivity.getResources().getString(R.string.bc_linkedin_url_redirect), "UTF-8");
            Log.d("TEST", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return ACCESS_TOKEN_URL
                +QUESTION_MARK
                +GRANT_TYPE_PARAM+EQUALS+GRANT_TYPE
                +AMPERSAND
                +RESPONSE_TYPE_VALUE+EQUALS+authorizationToken
                +AMPERSAND
                +CLIENT_ID_PARAM+EQUALS+ mActivity.getResources().getString(R.string.bc_linkedin_client_id)
                +AMPERSAND
                +REDIRECT_URI_PARAM+EQUALS+ encodedurl
                +AMPERSAND
                +SECRET_KEY_PARAM+EQUALS+ mActivity.getResources().getString(R.string.bc_linkedin_client_secret);
    }
    /**
     * Method that generates the url for get the authorization token from the Service
     * @return Url
     */
    private String getAuthorizationUrl(){
        String encodedurl = "";
        try {
            encodedurl = URLEncoder.encode(mActivity.getResources().getString(R.string.bc_linkedin_url_redirect), "UTF-8");
            Log.d("TEST", encodedurl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return AUTHORIZATION_URL
                +QUESTION_MARK+RESPONSE_TYPE_PARAM+EQUALS+RESPONSE_TYPE_VALUE
                +AMPERSAND+CLIENT_ID_PARAM+EQUALS+ mActivity.getResources().getString(R.string.bc_linkedin_client_id)
                +AMPERSAND+STATE_PARAM+EQUALS+ mActivity.getResources().getString(R.string.bc_linkedin_url_state)
                +AMPERSAND+REDIRECT_URI_PARAM+EQUALS+ encodedurl
                +AMPERSAND+SCOPE_PARAM+EQUALS+ mActivity.getResources().getString(R.string.bc_linkedin_url_scopes);
    }


    /**
     * retrieving the linkedin ID token, and sending it to the server.
     *
     */
    private void getLinkedinSession(String authorizationToken) {
        // Exchange Authorization Code for a Request Token

        //Generate URL for requesting Access Token
        String accessTokenUrl = getAccessTokenUrl(authorizationToken);
        //We make the request in a AsyncTask
        mAuthTask = new RefreshCredentialsTask(AccountManager.LoginType.LINKEDIN, mActivity, accessTokenUrl, this);
        mAuthTask.execute();
    }
// Excha

}

