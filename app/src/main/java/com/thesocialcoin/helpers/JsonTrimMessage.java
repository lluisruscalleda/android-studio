package com.thesocialcoin.helpers;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * andorra-turisme-android
 * <p/>
 * Created by Lluis Ruscalleda Abad on 26/05/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class JsonTrimMessage {
    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}
