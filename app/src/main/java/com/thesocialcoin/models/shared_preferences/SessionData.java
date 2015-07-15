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
    private static final String LOGGED_IN = "logged_in";

    /** The Constant USER_ID. */
    private static final String USER_ID = "user_id";
    /** The Constant EMAIL. */
    private static final String USER_EMAIL = "email";
    /** The Constant USER_USERNAME. */
    private static final String USER_USERNAME = "user_username";
    /** The Constant USER_FIRSTNAME. */
    private static final String USER_FIRSTNAME = "user_firstname";
    /** The Constant USER_LASTNAME. */
    private static final String USER_LASTNAME = "user_lastname";
    /** The Constant USER_ROLE. */
    private static final String USER_ROLE = "user_role";
    /** The Constant USER_IMAGE. */
    private static final String USER_IMAGE = "user_image";

    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";

    /** The Constant SESSIONID. */
    private static final String SESSIONID = "sessid";

    /** The Constant SESSION_TOKEN. */
    private static final String SESSION_TOKEN = "session_token";

    /** The Constant SESSION_NAME. */
    private static final String SESSION_NAME = "session_name";

    /** The Constant USER_DATA. */
    private static final String USER_DATA = "user_data";

    /** The Constant DEVICE_ID. */
    private static final String DEVICE_ID = "device_id";

    /** The Constant CLIENT_ID. */
    private static final String CLIENT_ID = "client_id";

    /** The Constant EXPIRATION_DATE. */
    private static final String CREATION_DATE= "creation_date";

    /** The Constant TTL. */
    private static final String TTL= "time_to_live";


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
        return mPreferences.getBoolean(LOGGED_IN, false);
    }

    /**
     * Sets the logged in.
     *
     * @param loggedIn the new logged in
     */
    public void setLoggedIn(boolean loggedIn) {
        getEditor().putBoolean(LOGGED_IN, loggedIn);
    }

    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return mPreferences.getString(USER_ID, null);
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(String userId) {
        getEditor().putString(USER_ID, userId);
    }

    /**
     * Gets the user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return mPreferences.getString(USER_EMAIL, null);
    }

    /**
     * Sets the user email.
     *
     * @param userEmail the new user email
     */
    public void setUserEmail(String userEmail) {
        getEditor().putString(USER_EMAIL, userEmail);
    }

    public String getUserUsername() {
        return mPreferences.getString(USER_USERNAME, null);
    }
    public void setUserUsername(String userName) {
        getEditor().putString(USER_USERNAME, userName);
    }
    public String getUserFirstname() {
        return mPreferences.getString(USER_FIRSTNAME, null);
    }
    public void setUserFirstname(String userFirstName) {
        getEditor().putString(USER_FIRSTNAME, userFirstName);
    }
    public String getUserLastname() {
        return mPreferences.getString(USER_LASTNAME, null);
    }
    public void setUserLastname(String userLastName) {
        getEditor().putString(USER_LASTNAME, userLastName);
    }
    public String getUserRole() {
        return mPreferences.getString(USER_ROLE, null);
    }
    public void setUserRole(String userRole) {
        getEditor().putString(USER_ROLE, userRole);
    }
    public String getUserImage() {
        return mPreferences.getString(USER_IMAGE, null);
    }
    public void setUserImage(String userImage) {
        getEditor().putString(USER_IMAGE, userImage);
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return mPreferences.getString(PASSWORD, null);
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        getEditor().putString(PASSWORD, password);
    }

    /**
     * Gets the session id.
     *
     * @return the session id
     */
    public String getSessionId() {
        return mPreferences.getString(SESSIONID, null);
    }

    /**
     * Sets the session id.
     *
     * @param sessionId the new session id
     */
    public void setSessionId(String sessionId) {
        getEditor().putString(SESSIONID, sessionId);
    }

    /**
     * Gets the session token.
     *
     * @return the session token
     */
    public String getSessionToken() {
        return mPreferences.getString(SESSION_TOKEN, null);
    }

    /**
     * Sets the session token.
     *
     * @param sessionToken the new session token
     */
    public void setSessionToken(String sessionToken) {
        getEditor().putString(SESSION_TOKEN, sessionToken);
    }

    /**
     * Gets the session name.
     *
     * @return the session name
     */
    public String getSessionName() {
        return mPreferences.getString(SESSION_NAME, null);
    }

    /**
     * Sets the session name.
     *
     * @param sessionName the new session name
     */
    public void setSessionName(String sessionName) {
        getEditor().putString(SESSION_NAME, sessionName);
    }

    /**
     * Gets the user data json string.
     *
     * @return the user data
     */
    public String getUserData() {
        return mPreferences.getString(USER_DATA, null);
    }

    /**
     * Sets the user data.
     *
     * @param userData the new user data
     */
    public void setUserData(String userData) {
        getEditor().putString(USER_DATA, userData);
    }

    /**
     * Gets the creation date of the authorization token.
     *
     * @return the creation date of the authorization token
     */
    public Date getAuthenticationTokenCreationDate() {
        return this.stringToDate(mPreferences.getString(CREATION_DATE, null));
    }

    /**
     * Sets the creation date of the authorization token.
     *
     * @param date the creation date of the authorization token.
     */
    public void setAuthenticationTokenCreationDate(Date date) {
        if(date == null ){
            getEditor().putString(CREATION_DATE, "");
        }else{
            getEditor().putString(CREATION_DATE, this.dateToString(date));
        }

    }

    /**
     * Gets the time to live of the session
     *
     * @return the time to live of the session
     */
    public String getTimeToLive() {
        return mPreferences.getString(TTL, null);
    }

    /**
     * Sets the time to live of the session.
     *
     * @param timeToLive new user data
     */
    public void setTimeToLive(String timeToLive) {
        getEditor().putString(TTL, timeToLive);
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
        return mPreferences.getString(CLIENT_ID, null);
    }

    /**
     * Sets the client id.
     *
     * @param clientId the client id
     */
    public void setClientId(String clientId) {
        getEditor().putString(CLIENT_ID, clientId);
    }

    /**
     * Gets the device id.
     *
     * @return the device id
     */
    public String getDeviceId() {
        return mPreferences.getString(DEVICE_ID, null);
    }

    /**
     * Sets the device id.
     *
     * @param deviceId the device id
     */
    public void setDeviceId(String deviceId) {
        getEditor().putString(DEVICE_ID, deviceId);
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
