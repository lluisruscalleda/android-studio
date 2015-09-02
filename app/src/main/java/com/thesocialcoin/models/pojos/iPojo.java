package com.thesocialcoin.models.pojos;

import com.google.gson.Gson;

import org.parceler.Parcel;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 15/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
@Parcel
public class iPojo<T> {

    public iPojo() {}

    public String serialize(Object object) {
        // Serialize this class into a JSON string using GSON
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public Object create(String serializedData, Class<T> classType) {
        // Use GSON to instantiate this class using the JSON representation of the state
        Gson gson = new Gson();
        return gson.fromJson(serializedData, classType);
    }
}
