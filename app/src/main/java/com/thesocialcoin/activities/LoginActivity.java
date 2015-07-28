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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
import com.thesocialcoin.controllers.UserManager;
import com.thesocialcoin.events.AuthenticateUserEvent;
import com.thesocialcoin.models.pojos.APILoginResponse;
import com.thesocialcoin.networking.core.RequestManager;
import com.thesocialcoin.utils.ToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends PlusBaseActivity implements LoaderCallbacks<Cursor>, UserManager.OnRegisterResponseListener  {

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
    private UserLoginTask mAuthTask = null;


    // List of additional write permissions being requested
    private static final List<String> PERMISSIONS = Arrays.asList("user_status, email");

    // UI references.
    private View mProgressView;
//    private View mEmailLoginFormView;
    private RelativeLayout mPlusSignInButton;
//    private View mSignOutButtons;
    private View mLoginFormView;
    private RelativeLayout authButton;
    CallbackManager callbackManager;
    private AccessToken facebookAccessToken;
    //private UiLifecycleHelper uiHelper;
    //private String facebookAccessToken = "";
    private ProgressBar progressBar;

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

        mActivity = this;

        //init facebook sdk and crashlitics
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

        // Find the Google+ sign in button.
        mPlusSignInButton = (RelativeLayout) findViewById(R.id.btn_gplus_auth);
        if (supportsGooglePlayServices()) {
            // Set a listener to connect the user when the G+ button is clicked.
            mPlusSignInButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });
        } else {
            // Don't offer G+ sign in if the app's version is too low to support Google Play
            // Services.
            mPlusSignInButton.setVisibility(View.GONE);
            return;
        }

        // Set up the login form.

        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        callbackManager = CallbackManager.Factory.create();
        authButton = (RelativeLayout) findViewById(R.id.btn_facebook_auth);
        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                        UserManager.getInstance(LoginActivity.this).authenticateWithFacebook(facebookAccessToken.getToken(), LoginActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }


    /*private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            Log.i(LogTags.FACEBOOK_LOGIN, "User ID ");
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(LogTags.FACEBOOK_LOGIN, "Logged in...");
            facebookAccessToken = session.getAccessToken();
            Request.newMeRequest(session,
                    new Request.GraphUserCallback() {
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                showProgress(true);
                                String id = user.getId();
                                Log.i(LogTags.FACEBOOK_LOGIN, "User ID " + id);
                                Object o = user.asMap().get("email");
                                String email = "";
                                if (o != null) {
                                    email = o.toString();

                                    Log.i(LogTags.FACEBOOK_LOGIN, "Email " + email);
                                }

                                //SingleRequestManager.authenticateWithFacebook(LoginActivity.this, email, id, facebookAccessToken);
                                UserManager.getInstance(LoginActivity.this).authenticateWithFacebook(email, id, facebookAccessToken);
                            }
                        }
                    }).executeAsync();

        } else if (state.isClosed()) {
            facebookAccessToken = "";
            Log.i(LogTags.FACEBOOK_LOGIN, "Logged out...");
        }
    }
*/

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
        UserManager.getInstance(LoginActivity.this).authenticateWithGoogle(String.valueOf(Plus.AccountApi.getAccountName(getPlusClient())), LoginActivity.this);
        //Set up sign out and disconnect buttons.
//        Button signOutButton = (Button) findViewById(R.id.plus_sign_out_button);
//        signOutButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });
//        Button disconnectButton = (Button) findViewById(R.id.plus_disconnect_button);
//        disconnectButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                revokeAccess();
//            }
//        });
    }

    @Override
    protected void onPlusClientBlockingUI(boolean show) {
        showProgress(show);
    }

    @Override
    protected void updateConnectButtonState() {
        //TODO: Update this logic to also handle the user logged in by email.
        boolean connected = getPlusClient().isConnected();

//        mSignOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
        mPlusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
//        mEmailLoginFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
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
        signIn();
    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


//    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(LoginActivity.this,
//                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);
//
//        mEmailView.setAdapter(adapter);
//    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
//                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
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
     * Event Subsctiption handling
     */
    @Subscribe
    public void onAuthenticationEvent(AuthenticateUserEvent event) {
        showProgress(false);
        if (event.getType().equals(AuthenticateUserEvent.Type.ERROR)) {
            authButton.setVisibility(View.VISIBLE);
            ToastUtils.show(mActivity, getString(R.string.error_login_failed) + ": " + event.getError().getErrorMessage());
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

        if (event.getType().equals(AuthenticateUserEvent.Type.SUCCESS)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}

