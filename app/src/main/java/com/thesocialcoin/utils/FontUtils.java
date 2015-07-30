package com.thesocialcoin.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.thesocialcoin.R;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class FontUtils {

    public static Typeface getAppsBoldFont(Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getResources().getString(R.string.bc_app_bold_font));
        return font;
    }

    public static Typeface getAppsRegularFont(Context context) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getResources().getString(R.string.bc_app_regular_font));
        return font;
    }
}
