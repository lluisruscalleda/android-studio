package com.thesocialcoin.models.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class SessionData {

    /** The m preferences. */
    protected SharedPreferences mPreferences;

    /** The m editor. */
    protected SharedPreferences.Editor mEditor;

    /** The Constant SHARED_PREFERENCES_FILE. */
    private static final String SHARED_PREFERENCES_FILE = "thesocialcoin_session_data";

    /** The Constant LOGGED_IN. */
    private static final String KEY_LOGGED_IN = "logged_in";

    /** The Constant USER_ID. */
    private static final String KEY_USER_ID = "user_id";
    /** The Constant EMAIL. */
    private static final String KEY_USER_EMAIL = "email";
    /** The Constant USER_USERNAME. */
    private static final String KEY_USER_USERNAME = "user_username";
    /** The Constant USER_FIRSTNAME. */
    private static final String KEY_USER_FIRSTNAME = "user_firstname";
    /** The Constant USER_LASTNAME. */
    private static final String KEY_USER_LASTNAME = "user_lastname";
    /** The Constant USER_ROLE. */
    private static final String KEY_USER_ROLE = "user_role";
    /** The Constant USER_IMAGE. */
    private static final String KEY_USER_IMAGE = "user_image";

    /** The Constant PASSWORD. */
    private static final String KEY_PASSWORD = "password";

    /** The Constant SESSIONID. */
    private static final String KEY_SESSIONID = "sessid";

    /** The Constant SESSION_TOKEN. */
    private static final String KEY_SESSION_TOKEN = "session_token";

    /** The Constant SESSION_NAME. */
    private static final String KEY_SESSION_NAME = "session_name";

    /** The Constant USER_DATA. */
    private static final String KEY_USER_DATA = "user_data";

    /** The Constant DEVICE_ID. */
    private static final String KEY_DEVICE_ID = "device_id";

    /** The Constant CLIENT_ID. */
    private static final String KEY_CLIENT_ID = "client_id";

    /** The Constant EXPIRATION_DATE. */
    private static final String KEY_CREATION_DATE= "creation_date";

    /** The Constant TTL. */
    private static final String KEY_TTL= "time_to_live";

    /** The Constant KEY_GOOGLE_SESSION_TOKEN. */
    private static final String KEY_GOOGLE_SESSION_TOKEN= "google_session_token";
    /** The Constant KEY_GOOGLE_ACCOUNT_NAME. */
    private static final String KEY_GOOGLE_ACCOUNT_NAME= "google_account_name";


    /**
     * Instantiates a new session data.
     *
     * @param context the context
     */
    public SessionData(Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
    }

    /**
     * Checks if is logged in.
     *
     * @return true, if is logged in
     */
    public boolean isLoggedIn() {
        return mPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    /**
     * Sets the logged in.
     *
     * @param loggedIn the new logged in
     */
    public void setLoggedIn(boolean loggedIn) {
        getEditor().putBoolean(KEY_LOGGED_IN, loggedIn);
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return mPreferences.getString(KEY_USER_ID, null);
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        getEditor().putString(KEY_USER_ID, userId);
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return mPreferences.getString(KEY_USER_EMAIL, null);
    }

    /**
     * Sets the user email.
     *
     * @param userEmail the new user email
     */
    public void setUserEmail(String userEmail) {
        getEditor().putString(KEY_USER_EMAIL, userEmail);
    }

    public String getUserUsername() {
        return mPreferences.getString(KEY_USER_USERNAME, null);
    }
    public void setUserUsername(String userName) {
        getEditor().putString(KEY_USER_USERNAME, userName);
    }
    public String getUserFirstname() {
        return mPreferences.getString(KEY_USER_FIRSTNAME, null);
    }
    public void setUserFirstname(String userFirstName) {
        getEditor().putString(KEY_USER_FIRSTNAME, userFirstName);
    }
    public String getUserLastname() {
        return mPreferences.getString(KEY_USER_LASTNAME, null);
    }
    public void setUserLastname(String userLastName) {
        getEditor().putString(KEY_USER_LASTNAME, userLastName);
    }
    public String getUserRole() {
        return mPreferences.getString(KEY_USER_ROLE, null);
    }
    public void setUserRole(String userRole) {
        getEditor().putString(KEY_USER_ROLE, userRole);
    }
    public String getUserImage() {
        return mPreferences.getString(KEY_USER_IMAGE, null);
    }
    public void setUserImage(String userImage) {
        getEditor().putString(KEY_USER_IMAGE, userImage);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return mPreferences.getString(KEY_PASSWORD, null);
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        getEditor().putString(KEY_PASSWORD, password);
    }

    /**
     * Gets the session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return mPreferences.getString(KEY_SESSIONID, null);
    }

    /**
     * Sets the session id.
     *
     * @param sessionId the new session id
     */
    public void setSessionId(String sessionId) {
        getEditor().putString(KEY_SESSIONID, sessionId);
    }

    /**
     * Gets the session token.
     *
     * @return the session token
     */
    public String getSessionToken() {
        return mPreferences.getString(KEY_SESSION_TOKEN, null);
    }

    /**
     * Sets the session token.
     *
     * @param sessionToken the new session token
     */
    public void setSessionToken(String sessionToken) {
        getEditor().putString(KEY_SESSION_TOKEN, sessionToken);
    }

    /**
     * Gets the session name.
     *
     * @return the session name
     */
    public String getSessionName() {
        return mPreferences.getString(KEY_SESSION_NAME, null);
    }

    /**
     * Sets the session name.
     *
     * @param sessionName the new session name
     */
    public void setSessionName(String sessionName) {
        getEditor().putString(KEY_SESSION_NAME, sessionName);
    }

    /**
     * Gets the user data json string.
     *
     * @return the user data
     */
    public String getUserData() {
        return mPreferences.getString(KEY_USER_DATA, null);
    }

    /**
     * Sets the user data.
     *
     * @param userData the new user data
     */
    public void setUserData(String userData) {
        getEditor().putString(KEY_USER_DATA, userData);
    }

    /**
     * Gets the creation date of the authorization token.
     *
     * @return the creation date of the authorization token
     */
    public Date getAuthenticationTokenCreationDate() {
        return this.stringToDate(mPreferences.getString(KEY_CREATION_DATE, null));
    }

    /**
     * Sets the creation date of the authorization token.
     *
     * @param date the creation date of the authorization token.
     */
    public void setAuthenticationTokenCreationDate(Date date) {
        if(date == null ){
            getEditor().putString(KEY_CREATION_DATE, "");
        }else{
            getEditor().putString(KEY_CREATION_DATE, this.dateToString(date));
        }

    }

    /**
     * Gets the time to live of the session
     *
     * @return the time to live of the session
     */
    public String getTimeToLive() {
        return mPreferences.getString(KEY_TTL, null);
    }

    /**
     * Sets the time to live of the session.
     *
     * @param timeToLive new user data
     */
    public void setTimeToLive(String timeToLive) {
        getEditor().putString(KEY_TTL, timeToLive);
    }


    /**
     * Gets the google session token of the session
     *
     * @return the google session token of the session
     */
    public String getGoogleSessionToken() {
        return mPreferences.getString(KEY_GOOGLE_SESSION_TOKEN, null);
    }

    /**
     * Sets the google session token of the session.
     *
     * @param googleSessionToken new google session token
     */
    public void setGoogleSessionToken(String googleSessionToken) {
        getEditor().putString(KEY_GOOGLE_SESSION_TOKEN, googleSessionToken);
    }

    /**
     * Gets the google account name of the session
     *
     * @return the google account name of the session
     */
    public String getGoogleAccountName() {
        return mPreferences.getString(KEY_GOOGLE_ACCOUNT_NAME, null);
    }

    /**
     * Sets the google account name of the session.
     *
     * @param googleAccountName new google account name
     */
    public void setGoogleAccountName(String googleAccountName) {
        getEditor().putString(KEY_GOOGLE_ACCOUNT_NAME, googleAccountName);
    }



    /**
     * Gets the expiration date of the session
     *
     * @return the expiration date of the session
     */
    public Date getExpirationDate() {
        String timeToLive = this.getTimeToLive();
        Date creationDate = this.getAuthenticationTokenCreationDate();
        if (timeToLive == null || creationDate == null)
        {
            return null;
        }
        long newDate = creationDate.getTime() + (Integer.parseInt(timeToLive) * 1000);

        Date expirationDate = new Date(newDate);
        return expirationDate;

    }

    public Date stringToDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return convertedDate;

    }

    public String dateToString(Date date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String convertedDate = dateFormat.format(date);
        return convertedDate;
    }


    /**
     * Gets the client id.
     *
     * @return the client id
     */
    public String getClientId() {
        return mPreferences.getString(KEY_CLIENT_ID, null);
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id
     */
    public void setClientId(String clientId) {
        getEditor().putString(KEY_CLIENT_ID, clientId);
    }

    /**
     * Gets the device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return mPreferences.getString(KEY_DEVICE_ID, null);
    }

    /**
     * Sets the device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(String deviceId) {
        getEditor().putString(KEY_DEVICE_ID, deviceId);
    }

    public void doLogout(){
        setLoggedIn(false);
        apply();
    }

    public void clearEditor(){
        getEditor().clear();
        apply();
    }

    /**
     * Apply.
     */
    public void apply() {
        if (mEditor != null) {
            mEditor.apply();
            mEditor = null;
        }
    }

    /**
     * Gets the editor.
     *
     * @return the editor
     */
    protected SharedPreferences.Editor getEditor() {
        if (mEditor == null) {
            mEditor = mPreferences.edit();
        }

        return mEditor;
    }

}
