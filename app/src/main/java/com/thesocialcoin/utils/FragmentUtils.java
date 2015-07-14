package com.thesocialcoin.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 14/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public class FragmentUtils {

    /**
     * Loads a fragment in a container and adds it to the backstack
     *
     * @param fragment    : El fragment que se quiere colocar
     * @param containerID : El id del contenedor donde se va a colocar el fragment
     * @param activity    : La actividad actual
     */
    public static void loadFragmentAndAddToBackStack(Fragment fragment,
                                                     int containerID, FragmentActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(containerID, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }

    /**
     * Loads a fragment in a container
     *
     * @param fragment    : El fragment que se quiere colocar
     * @param containerID : El id del contenedor donde se va a colocar el fragment
     */
    public static void loadFragment(Fragment fragment, int containerID,
                                    FragmentActivity activity) {
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(containerID, fragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        transaction.commit();
    }
}
