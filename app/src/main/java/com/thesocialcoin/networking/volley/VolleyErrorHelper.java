package com.thesocialcoin.networking.volley;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import ad.andorra.turismeactiu.R;

/**
 * Created by lluisruscalleda on 17/12/14.
 */

public class VolleyErrorHelper {
    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error
     * @param context
     * @return
     */
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (isServerProblem(error)) {
            return handleServerError(error, context);
        } else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.no_network_connection);
        }
        return context.getResources().getString(R.string.generic_server_down);
    }


    /**
     *
     * @param error
     * @param context
     * @return Return generic message for errors
     */
    public static String getErrorType(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return context.getResources().getString(R.string.generic_server_timeout);
        } else if (error instanceof ServerError) {
            return context.getResources().getString(R.string.generic_server_down);
        } else if (error instanceof AuthFailureError) {
            return context.getResources().getString(R.string.auth_failed);
        } else if (error instanceof NetworkError) {
            return context.getResources().getString(R.string.no_network_connection);
        } else if (error instanceof NoConnectionError) {
            return context.getResources().getString(R.string.no_network_connection);
        } else if (error instanceof ParseError) {
            return context.getResources().getString(R.string.parsing_failed);
        }
        return context.getResources().getString(R.string.generic_server_down);
    }

    /**
     * Determines whether the error is related to network
     *
     * @param error
     * @return
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server
     *
     * @param error
     * @return
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err
     * @param context
     * @return
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 404:
                case 422:
                    return context.getResources().getString(R.string.generic_server_down);
                case 401:
                    return context.getResources().getString(R.string.auth_failed);
                    /*try {
                        // server might return error like this { "error": "Some error occured" }
                        // Use "Gson" to parse the result
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(new String(response.data)).getAsJsonObject();

                        if (jsonObject != null && jsonObject.get("error") !=null) {
                            JsonElement jsonError = jsonObject.get("error");

                            return jsonError.toString();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage();*/

                default:
                    return context.getResources().getString(R.string.generic_server_down);
            }
        }
        return context.getResources().getString(R.string.generic_server_down);
    }
}

