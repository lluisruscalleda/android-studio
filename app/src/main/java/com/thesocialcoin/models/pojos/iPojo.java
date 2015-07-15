package com.thesocialcoin.models.pojos;

import com.google.gson.Gson;

/**
 * Created by identitat on 26/11/14.
 */
public class iPojo {

    public String serialize() {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static protected iPojo create(String serializedData) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, iPojo.class);
    }
}
