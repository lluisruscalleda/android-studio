package com.thesocialcoin.utils;

import java.util.Locale;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class Utils {


    public static String getAppLanguage(){
        return Locale.getDefault().getLanguage().substring(0,2);
    }

}
