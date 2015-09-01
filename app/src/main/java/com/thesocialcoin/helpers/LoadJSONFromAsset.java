package com.thesocialcoin.helpers;

import com.thesocialcoin.App;

import java.io.IOException;
import java.io.InputStream;

/**
 * thesocialcoin
 * <p/>
 * Created by identitat on 31/08/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class LoadJSONFromAsset {
    public static String load(String jsonFilename) {
        String json = null;
        try {

            InputStream is = App.getAppContext().getResources().openRawResource(
                    App.getAppContext().getResources().getIdentifier("raw/"+jsonFilename,
                            "raw", App.getAppContext().getPackageName()));

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer);


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
