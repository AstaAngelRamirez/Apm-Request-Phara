package com.apm.request.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apm.request.abstracts.RequestListener;
import com.apm.request.contracts.IRequestMethods;
import com.apm.request.contracts.WebServiceCallerListener;
import com.apm.request.enums.ResponseType;
import com.apm.request.enums.RequestType;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.http.controllers.WebServiceCallerBuilder;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/11/2017.
 */

public class Request<T> implements IRequestMethods<T>, WebServiceCallerListener, Serializable {

    // CONSTANTS
    private final String TAG = Request.class.getSimpleName();

    // LISTENER
    private RequestListener<T> mRequestListener;

    // VARS
    private WebServiceCallerBuilder webServiceCallerBuilder;
    private JSONObject mWebServiceResponse;
    private String mResponseObjectName;
    private Class<T> mClassType;
    private String mURLToRequest;

    public Request(Class<T> classType) {
        this.mClassType = classType;
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull String webService,
                                Map<String, String> headers, Map<String, String> stringParams,
                                Map<String, File> fileParams, Map<String, List<File>> fileArrayParameters,
                                Map<String, List<String>> stringArrayParameters, String jsonParams,
                                String responseObjectName, String requestToken, String requestLanguage,
                                boolean autoRegisterToken, int timeout, boolean retryOnTimeout,
                                RequestListener<T> requestListener) {


        if (webService.trim().equals("")) {
            throw new IllegalArgumentException("Web service url must have a valid url");
        }

        mURLToRequest = webService;
        webServiceCallerBuilder =
                new WebServiceCallerBuilder()
                        .with(context)
                        .webserviceURL(webService)
                        .requestResponseType(ResponseType.TEXT)
                        .webServiceCallerListener(this);

        if (headers != null) {
            webServiceCallerBuilder.webServiceHeaders(headers);
        }

        if (stringParams != null) {
            webServiceCallerBuilder.webServiceStringParams(stringParams);
        }

        if (fileParams != null) {
            webServiceCallerBuilder.webServiceFileParams(fileParams);
        }

        if (fileArrayParameters != null) {
            webServiceCallerBuilder.webServiceFileArrayParams(fileArrayParameters);
        }

        if (stringArrayParameters != null) {
            webServiceCallerBuilder.webServiceStringArrayParams(stringArrayParameters);
        }

        if (jsonParams != null && !jsonParams.trim().equals("")) {
            webServiceCallerBuilder.webServiceJsonParams(jsonParams);
        }

        if (responseObjectName != null && !responseObjectName.trim().equals("")) {
            this.mResponseObjectName = responseObjectName;
        }

        if (requestToken != null && !requestToken.trim().equals("")) {
            webServiceCallerBuilder.currentToken(requestToken);
        }

        if (requestLanguage != null && !requestLanguage.trim().equals("")) {
            webServiceCallerBuilder.language(requestLanguage);
        }

        if (autoRegisterToken) webServiceCallerBuilder.autoRegisterToken();
        webServiceCallerBuilder.connectionTimeout(timeout);
        if (retryOnTimeout) webServiceCallerBuilder.retryOnTimeOut();
        if (requestListener != null) this.mRequestListener = requestListener;

        return this;
    }

    @Override
    public void get() {
        if (webServiceCallerBuilder != null) {
            webServiceCallerBuilder
                    .requestType(RequestType.GET)
                    .build().doWebServiceQuery();
        } else {
            Log.e(TAG, "WebServiceCallerBuilder is null. Please call buildWebServiceQuery method before call get() method.");
        }
    }

    @Override
    public void save() {
        if (webServiceCallerBuilder != null) {
            webServiceCallerBuilder
                    .requestType(RequestType.POST)
                    .build().doWebServiceQuery();
        } else {
            Log.e(TAG, "WebServiceCallerBuilder is null. Please call buildWebServiceQuery method before call save() method.");
        }
    }

    @Override
    public void update() {
        if (webServiceCallerBuilder != null) {
            webServiceCallerBuilder
                    .requestType(RequestType.PUT)
                    .build().doWebServiceQuery();
        } else {
            Log.e(TAG, "WebServiceCallerBuilder is null. Please call buildWebServiceQuery method before call update() method.");
        }
    }

    @Override
    public void delete() {
        if (webServiceCallerBuilder != null) {
            webServiceCallerBuilder
                    .requestType(RequestType.DELETE)
                    .build().doWebServiceQuery();
        } else {
            Log.e(TAG, "WebServiceCallerBuilder is null. Please call buildWebServiceQuery method before call delete() method.");
        }
    }

    @Override
    public T getElement(String elementName) {
        try {
            if (mClassType.isPrimitive()) {
                return mClassType
                        .cast(elementName != null && !elementName.trim().equals("")
                                ? mWebServiceResponse.get(elementName)
                                : mWebServiceResponse);
            } else if (mClassType == JSONObject.class) {
                return mClassType
                        .cast(elementName != null && !elementName.trim().equals("")
                                ? new JSONObject(mWebServiceResponse.get(elementName).toString())
                                : new JSONObject(mWebServiceResponse.toString()));
            } else if (mClassType == JSONArray.class) {
                return mClassType
                        .cast(elementName != null && !elementName.trim().equals("")
                                ? new JSONArray(mWebServiceResponse.get(elementName).toString())
                                : new JSONArray(mWebServiceResponse.toString()));
            } else {
                return new Gson()
                        .fromJson(elementName != null && !elementName.trim().equals("")
                                ? mWebServiceResponse.get(elementName).toString()
                                : mWebServiceResponse.toString(), mClassType);
            }
        } catch (Exception e) {
            e.printStackTrace();

            if (mRequestListener != null) {
                mRequestListener.onRequestError(e);
                mRequestListener.onRequestError(new ExceptionManager(e, mURLToRequest, null));
            }
        }
        return null;
    }

    @Override
    public String toJSONString() {
        if (mWebServiceResponse != null) {
            return mWebServiceResponse.toString();
        }
        Log.e(TAG, "You must call get(), update(), save() or delete() method");
        return null;
    }

    @Override
    public void onWSCallStart() {
        if (mRequestListener != null) {
            mRequestListener.onRequestStart();
        }
    }

    @Override
    public void onWSCallError(Exception e) {
        if (mRequestListener != null) {
            mRequestListener.onRequestError(e);
            mRequestListener.onRequestError(new ExceptionManager(e, mURLToRequest, null));
        }
    }

    @Override
    public void onWSCallEnd(Object objectResponse, String currentToken) {
        if (mRequestListener != null) {
            try {
                mWebServiceResponse = new JSONObject(String.valueOf(objectResponse));
            } catch (Exception e) {
                mRequestListener.onRequestError(e);
                mRequestListener.onRequestError(new ExceptionManager(e, mURLToRequest, objectResponse));
            }

            try {
                Response response = new Gson().fromJson(mWebServiceResponse.toString(), Response.class);
                if (response.hasErrors()) {
                    mRequestListener.onRequestError(response);
                    mRequestListener.onRequestError(new ExceptionManager(null, mURLToRequest, response));
                } else {
                    mRequestListener.onRequestEnd(getElement(mResponseObjectName), currentToken);
                }
            } catch (Exception e) {
                mRequestListener.onRequestError(e);
                mRequestListener.onRequestError(new ExceptionManager(e, mURLToRequest, objectResponse));
            }
        }
    }
}
