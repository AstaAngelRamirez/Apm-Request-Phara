package com.apm.request.http.controllers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.apm.request.contracts.WebServiceCallerListener;
import com.apm.request.enums.ResponseType;
import com.apm.request.enums.RequestType;
import com.apm.request.utils.FileUtils;
import com.apm.request.utils.TokenUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Ing. Oscar G. Medina Cruz on 06/06/17.
 * <p>
 * <p>
 * Utility for easy handle of web services with {@see https://github.com/square/okhttp} library.
 * All information and request processed in background.
 * </p>
 */

public class WebServiceCaller extends AsyncTask<Object, Object, Object> {

    // CONSTANTS
    private final String TAG = WebServiceCaller.class.getSimpleName();

    //region VARS

    private String mWebServiceURL;
    private Map<String, String> mWebServiceStringParams;
    private Map<String, List<String>> mWebServiceStringArrayParams;
    private Map<String, File> mWebServiceFileParams;
    private Map<String, List<File>> mWebServiceFileArrayParams;
    private String mWebServiceJsonParams;
    private Map<String, String> mWebServiceHeaders;
    private RequestType mRequestType;
    private ResponseType mResponseType;
    //private String mResponseObjectName;
    private int mConnectionTimeout;
    private boolean mRetryOnTimeOut;
    private String mToken = "";
    private String mLanguage = "";
    private boolean mLogEnabled = false;
    private Context mContext;
    private boolean mAutoRegisterToken = false;

    private JSONObject jsonObjectResponse = null;
    private JSONArray jsonArrayResponse = null;
    private String jsonTextResponse = "";

    private static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    //endregion

    //region LISTENERS
    private WebServiceCallerListener webServiceCallerListener;
    //endregion

    /**
     * @param context                     Application context
     * @param webServiceURL               Web service URL
     * @param webServiceHeaders           Web service Headers
     * @param webServiceParams            String map of parameters
     * @param webServiceStringArrayParams String array map of parameters
     * @param webServiceFileParams        File map of parameters
     * @param webServiceFileArrayParams   File array map of parameters
     * @param webServiceJsonParams        JSON String of parameters
     * @param requestType                 Request type {@link RequestType}
     * @param responseType                Response type {@link ResponseType}
     *                                    //@param responseObjectName        Name of the wanted object
     * @param connectionTimeout           Web service connection timeout
     * @param retryOnTimeOut              Set if the class can retry connection in case of error
     * @param token                       Generated token after login process
     * @param logEnabled                  Enable class log
     * @param language                    Language code {@see http://kirste.userpage.fu-berlin.de/diverse/doc/ISO_3166.html}
     * @param autoRegisterToken           Auto register token
     * @param webServiceCallerListener    Listener that receive response of the web service
     */
    public WebServiceCaller(String webServiceURL, Map<String, String> webServiceParams, Map<String, List<String>> webServiceStringArrayParams,
                            Map<String, File> webServiceFileParams, Map<String, List<File>> webServiceFileArrayParams,
                            String webServiceJsonParams, Map<String, String> webServiceHeaders, RequestType requestType,
                            ResponseType responseType, /*String responseObjectName, */int connectionTimeout,
                            boolean retryOnTimeOut, String token, boolean logEnabled, String language, Context context,
                            boolean autoRegisterToken, WebServiceCallerListener webServiceCallerListener) {
        this.mWebServiceURL = webServiceURL;
        this.mWebServiceStringParams = webServiceParams;
        this.mWebServiceStringArrayParams = webServiceStringArrayParams;
        this.mWebServiceFileParams = webServiceFileParams;
        this.mWebServiceFileArrayParams = webServiceFileArrayParams;
        this.mWebServiceJsonParams = webServiceJsonParams;
        this.mWebServiceHeaders = webServiceHeaders;
        this.mRequestType = requestType;
        this.mResponseType = responseType;
        this.mConnectionTimeout = connectionTimeout;
        this.mRetryOnTimeOut = retryOnTimeOut;
        this.mToken = token;
        this.mLogEnabled = logEnabled;
        this.mLanguage = language;
        this.mContext = context;
        this.mAutoRegisterToken = autoRegisterToken;
        this.webServiceCallerListener = webServiceCallerListener;
    }

    /**
     * Do web service query in background
     */
    public void doWebServiceQuery() {
        execute();
    }

    /**
     * Show log message
     *
     * @param logMessage Message that will be showed
     * @param logOption  Integer log option (1: information, 2: error, 3: warning)
     */
    private void showLog(String logMessage, int logOption) {
        if (mLogEnabled) {
            switch (logOption) {
                case 1:
                    Log.i(TAG, logMessage);
                    break;
                case 2:
                    Log.e(TAG, logMessage);
                    break;
                case 3:
                    Log.w(TAG, logMessage);
                    break;
            }
        }
    }

    private RequestBody buildRequestBodyFromParams() {
        MultipartBody.Builder multiPartBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        FormBody.Builder builder = new FormBody.Builder();

        if (mWebServiceFileParams != null || mWebServiceFileArrayParams != null) {
            if (mWebServiceStringParams != null) {
                for (String paramKey : mWebServiceStringParams.keySet()) {
                    multiPartBuilder.addFormDataPart(paramKey, mWebServiceStringParams.get(paramKey));
                }
            }

            if (mWebServiceFileParams != null) {
                for (String paramKey : mWebServiceFileParams.keySet()) {
                    multiPartBuilder.addFormDataPart(paramKey, mWebServiceFileParams.get(paramKey).getName(),
                            RequestBody.create(
                                    MediaType.parse(FileUtils.GetMimeTypeFromFile(mWebServiceFileParams.get(paramKey))),
                                    mWebServiceFileParams.get(paramKey)));
                }
            }

            if (mWebServiceFileArrayParams != null) {
                for (String paramKey : mWebServiceFileArrayParams.keySet()) {
                    for (File file : mWebServiceFileArrayParams.get(paramKey)) {
                        multiPartBuilder.addFormDataPart(paramKey, file.getName(),
                                RequestBody.create(
                                        MediaType.parse(FileUtils.GetMimeTypeFromFile(file)),
                                        file));
                    }
                }
            }

            if (mWebServiceStringArrayParams != null) {
                for (String paramKey : mWebServiceStringArrayParams.keySet()) {
                    for (String value : mWebServiceStringArrayParams.get(paramKey)) {
                        multiPartBuilder.addFormDataPart(paramKey, value);
                    }
                }
            }

            return multiPartBuilder.build();
        } else {
            if (mWebServiceStringParams != null || mWebServiceStringArrayParams != null) {

                if (mWebServiceStringParams != null) {
                    for (String paramKey : mWebServiceStringParams.keySet()) {
                        builder.add(paramKey, mWebServiceStringParams.get(paramKey));
                    }
                }

                if (mWebServiceStringArrayParams != null) {
                    for (String paramKey : mWebServiceStringArrayParams.keySet()) {
                        for (String value : mWebServiceStringArrayParams.get(paramKey)) {
                            builder.add(paramKey, value);
                        }
                    }

                    return builder.build();
                }

                if (mRequestType == RequestType.DELETE) {
                    JSONObject obj = new JSONObject(mWebServiceStringParams);
                    return RequestBody.create(JSON, obj.toString());
                } else {
                    return builder.build();
                }

            } else if (mWebServiceJsonParams != null) {
                return RequestBody.create(JSON, mWebServiceJsonParams);
            } else {
                return builder.build();
            }
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (webServiceCallerListener != null) {
            webServiceCallerListener.onWSCallStart();
        }
    }

    @Override
    protected Object doInBackground(Object... params) {
        RequestBody body = null;
        Request request = null;
        Request.Builder requestBuilder = null;
        try {
            if (mRequestType == RequestType.POST || mRequestType == RequestType.PUT || mRequestType == RequestType.DELETE) {

                body = buildRequestBodyFromParams();

            } else if (mRequestType == RequestType.GET) {
                if (mWebServiceStringParams != null) {
                    String getParams = "?";
                    for (String paramKey : mWebServiceStringParams.keySet()) {
                        getParams += paramKey + "=" + mWebServiceStringParams.get(paramKey) + "&";
                    }
                    getParams = getParams.substring(0, getParams.length() - 1);
                    mWebServiceURL += getParams;
                }
            }

            requestBuilder = new Request.Builder();

            requestBuilder.url(mWebServiceURL);

            if (!mToken.equals(""))
                requestBuilder.addHeader("Authorization", "Bearer " + mToken);

            if (!mLanguage.equals(""))
                requestBuilder.addHeader("X-Localization", mLanguage);

            if (mWebServiceHeaders != null) {
                for (String header : mWebServiceHeaders.keySet()) {
                    requestBuilder.addHeader(header, mWebServiceHeaders.get(header));
                }
            }

            if (body != null) {

                if (mRequestType == RequestType.POST) {
                    requestBuilder.post(body);
                }

                if (mRequestType == RequestType.PUT) {
                    requestBuilder.put(body);
                }

                if (mRequestType == RequestType.DELETE) {
                    requestBuilder.delete(body);
                }
            }

            request = requestBuilder.build();

            if (request != null) {
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(mConnectionTimeout, TimeUnit.SECONDS)
                        .readTimeout(mConnectionTimeout, TimeUnit.SECONDS)
                        .writeTimeout(mConnectionTimeout, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(mRetryOnTimeOut)
                        .build();


                String wsResponse = "";
                Response response = client.newCall(request).execute();

                showLog("Response: " + String.valueOf(response), 1);

                if (!response.isSuccessful()) {
                    showLog("Response: " + String.valueOf(response), 2);
                    if (response.body() != null) {
                        wsResponse = response.body().string();
                        response.body().close();
                    }
                    response.close();
                } else {
                    showLog("Response: " + String.valueOf(response), 1);
                    wsResponse = response.body().string();
                    response.body().close();
                    response.close();
                }

                if (!wsResponse.equals("")) {
                    switch (mResponseType) {
                        case JSONOBJECT:
                            jsonObjectResponse = new JSONObject(wsResponse);
                            break;
                        case JSONARRAY:
                            jsonArrayResponse = new JSONArray(wsResponse);
                            break;
                        case TEXT:
                            jsonTextResponse = wsResponse;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            return e;
        }

        if (request != null && request.headers() != null)
            return request.headers().get("Authorization");
        else
            return "";
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);

        if (object instanceof Exception) {
            webServiceCallerListener.onWSCallError((Exception) object);
        } else {
            String token = "";

            if (object != null) {
                token = String.valueOf(object).replace("Bearer", "").trim();

                if (mAutoRegisterToken) {
                    TokenUtils.RegisterUserToken(mContext, token);
                }
            }

            if (webServiceCallerListener != null) {
                switch (mResponseType) {
                    case JSONOBJECT:
                        webServiceCallerListener.onWSCallEnd(jsonObjectResponse, token);
                        break;
                    case JSONARRAY:
                        webServiceCallerListener.onWSCallEnd(jsonArrayResponse, token);
                        break;
                    case TEXT:
                        webServiceCallerListener.onWSCallEnd(jsonTextResponse, token);
                        break;
                }
            }
        }
    }
}
