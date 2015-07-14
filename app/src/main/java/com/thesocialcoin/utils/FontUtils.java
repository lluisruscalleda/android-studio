package com.thesocialcoin.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class FontUtils {

    public static Typeface getAppsBoldFont(Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueBold.ttf");
        return font;
    }

    public static Typeface getAppsRegularFont(Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeueLight.ttf");
        return font;
    }
}
