package com.thesocialcoin.networking.volleyextensions;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.thesocialcoin.App;

/**
 * Created by lluisruscalleda on 15/10/14.
 */
public class VolleySingleton {

    private static VolleySingleton instance = null;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;
    LruBitmapCache mLruBitmapCache;
    private Context context;

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(App.getAppContext());

        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            private final LruBitmapCache cache = new LruBitmapCache();

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }


    public static VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }


    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(App.getAppContext());
        }

        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null) {
            getLruBitmapCache();
            imageLoader = new ImageLoader(this.requestQueue, mLruBitmapCache);
        }

        return this.imageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }


   /* private HttpStack getStack(){
        BasicHttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);

        CookieStore cookieStore = new PersistentCookieStore(context);
        httpClient.setCookieStore(cookieStore);
        HttpStack httpStack = new HttpClientStack(httpClient);

        return httpStack;
    }*/
//    private SSLSocketFactory newSslSocketFactory() {
//        try {
//            // Get an instance of the Bouncy Castle KeyStore format
//            /*KeyStore trusted = KeyStore.getInstance("BKS");
//            // Get the raw resource, which contains the keystore with
//            // your trusted certificates (root and any intermediate certs)
//            InputStream in = context.getApplicationContext().getResources().openRawResource(R.raw.certification);
//            try {
//                // Initialize the keystore with the provided trusted certificates
//                // Provide the password of the keystore
//                trusted.load(in, KEYSTORE_PASSWORD);
//            } finally {
//                in.close();
//            }*/
//
////            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
////            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
////            tmf.init(trusted);
//
//            TrustManager tm = new X509TrustManager() {
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                }
//
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            };
//
//            SSLContext context = SSLContext.getInstance("TLS");
//            context.init(null,new TrustManager[] { tm }, null);
//
//            SSLSocketFactory sf = context.getSocketFactory();
//            ((X509HostnameVerifier) hostnameVerifier);
//            return sf;
//        } catch (Exception e) {
//            throw new AssertionError(e);
//        }
//    }

}