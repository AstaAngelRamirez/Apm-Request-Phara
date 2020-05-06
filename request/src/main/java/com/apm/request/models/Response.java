package com.apm.request.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apm.request.abstracts.RequestListener;
import com.apm.request.contracts.IRequestBuilder;
import com.apm.request.contracts.IResponse;
import com.apm.request.enums.WebServiceType;
import com.apm.request.utils.RequestConfiguration;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/11/2017.
 */

public class Response implements IResponse, IRequestBuilder {

    private String type;
    private boolean errors;
    private String exception_name;
    private int code;
    private String code_message;
    private String message;
    private Object data;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean hasErrors() {
        return errors;
    }

    @Override
    public String getExceptionName() {
        return exception_name;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getCodeMessage() {
        return code_message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public <T> T getData(Class<T> classType) {
        String json = new Gson().toJson(data);

        if (classType == String.class){
            return classType.cast(json);
        } else if (classType == JSONArray.class ){
            try {
                return classType.cast(new JSONArray(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (classType == JSONObject.class ){
            try {
                return classType.cast(new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return new Gson().fromJson(json, classType);
        }

        return null;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    @Override
    public void setExceptionName(String exceptionName) {
        this.exception_name = exceptionName;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public void setCodeMessage(String code_message) {
        this.code_message = code_message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public Request buildRequest(@NonNull RequestBuilder requestBuilder) {
        return requestBuilder.build(Response.class);
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration, @NonNull String webServiceName, @NonNull WebServiceType webServiceType, @Nullable RequestListener<Response> requestListener) {
        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context, requestConfiguration)
                .requestTo(webServiceName)
                .ofType(webServiceType)
                .listener(requestListener);

        return requestBuilder.build(Response.class);
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull String webService, @Nullable RequestListener<Response> requestListener) {
        if (webService.trim().equals("")) {
            throw new IllegalArgumentException("Web service url must have a valid url");
        }

        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context)
                .requestTo(webService);

        if (requestListener != null) {
            requestBuilder.listener(requestListener);
        }

        return requestBuilder.build(Response.class);
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull int webService, @Nullable RequestListener<Response> requestListener) {
        if (webService == -1) {
            throw new IllegalArgumentException("Web service url must have a valid url");
        }

        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context)
                .requestTo(webService);

        if (requestListener != null) {
            requestBuilder.listener(requestListener);
        }

        return requestBuilder.build(Response.class);
    }
}
