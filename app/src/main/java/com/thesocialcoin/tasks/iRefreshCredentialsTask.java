package com.thesocialcoin.tasks;

import com.thesocialcoin.controllers.AccountManager;

/**
 * thesocialcoin
 * <p/>
 * Created by Lluis Ruscalleda Abad on 29/07/15.
 * Copyright (c) 2015 Identitat SL. All rights reserved.
 */
public interface iRefreshCredentialsTask<T> {
    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
    public void onFetchSocialTokenComplete(T result, AccountManager.LoginType identityType);
}
