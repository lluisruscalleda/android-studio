package com.thesocialcoin.utils;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class Codes {

    public final static String EMAIL_VALUE = "email";
    public final static String USERNAME_VALUE = "username";
    public final static String FACEBOOK_ID_VALUE = "facebookId";
    public final static String PROJECT_IDENTIFIER_VALUE = "projectId";
    public final static String FACEBOOK_ACCESS_TOKEN_VALUE = "token";
    public final static String PASSWORD_VALUE = "password";
    public final static String AUTHORIZATION_VALUE = "authorization";


    public static final String API_HOST = "";
    public static final String API_LOCALHOST = "";


    public static final String API_LOGIN = "/login";
    public static final String API_REGISTER_FACEBOOK = "/register_facebook";
    public static final String API_REGISTER = "/register";


    public static final String KEY_AUTH = "AUTHORIZATION";



    public static final String USER_TOKEN_VALUE = "token";
    public static final String USER_ID_VALUE = "user_id";
    public static final String SHARED_PREFERENCES_NAME = "TheSocialCoinPreferences";

    public static String getShared(){
        return SHARED_PREFERENCES_NAME;
    }


    // PARAMETRES DE REGISTRE D'USUARIS
    public static final String reg_user_username = "username";
    public static final String reg_user_firstname = "firstname";
    public static final String reg_user_lastname = "lastname";
    public static final String reg_user_email = "email";
    public static final String reg_user_password = "password";
    public static final String reg_user_role = "role";
    public static final String reg_user_photo = "photo";
    public static final String reg_user_weight = "weight";
    public static final String reg_user_height = "height";
    public static final String reg_user_birthday = "birthday";
    public static final String reg_user_language = "language";
    public static final String reg_user_facebook_token = "access_token";



    // WS ERROR CODES
    public static final String WS_TIMEOUT_ERROR = "generic_server_timeout";
    public static final String WS_SERVER_ERROR = "generic_server_down";
    public static final String WS_AUTHFAILURE_ERROR = "auth_failed";
    public static final String WS_NETWORK_ERROR = "network_error";
    public static final String WS_NOCONNECTION_ERROR = "no_network_connection";
    public static final String WS_PARSE_ERROR = "parsing_failed";

    public static final String WS_USERNAME_ALREADY_EXISTS_ERROR = "ws_username_already_exists";
    public static final String WS_USER_EMAIL_ALREADY_EXISTS_ERROR = "ws_user_email_already_exists";


    public static final String WS_ERROR_CODE_40 = "auth_failed";
    public static final String WS_ERROR_CODE_41 = "account_inactive";
    public static final String WS_ERROR_CODE_0 = "email_already_used";
    public static final String WS_ERROR_CODE_1 = "validation_email_sended";

}
