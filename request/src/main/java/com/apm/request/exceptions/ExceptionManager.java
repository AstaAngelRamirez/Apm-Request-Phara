package com.apm.request.exceptions;

import com.apm.request.enums.ExceptionType;
import com.apm.request.models.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/05/18.
 */
public class ExceptionManager {

    private String mURLToRequest;
    private ExceptionType mExceptionType;
    private Object mResponse;
    private Exception mRelatedException;
    private List<String> mResponseErrors;
    private String mFormattedResponseErrors;

    public ExceptionManager(Exception relatedException, String urlToRequest, Object response) {
        this.mRelatedException = relatedException;
        this.mURLToRequest = urlToRequest;
        this.mResponse = response;

        parseException();
    }

    private void parseException() {
        if (getResponse() != null) {
            if (getResponse() instanceof Response) {
                Response response = (Response) getResponse();
                ResponseCodeParser responseCodeParser = new ResponseCodeParser(response);
                mExceptionType = responseCodeParser.getExceptionType();
                mRelatedException = responseCodeParser.getException();

                String jsonResponse = new Gson().toJson(response.getData());

                if (response.getData() instanceof List
                        || response.getData() instanceof ArrayList) {
                    mResponseErrors = Arrays.asList(new Gson().fromJson(jsonResponse, String[].class));
                } else if (response.getData() instanceof String) {
                    mResponseErrors = Collections.singletonList(new Gson().fromJson(jsonResponse, String.class));
                }

                StringBuilder error = new StringBuilder();
                for (String e : getResponseErrors()) {
                    error.append(e).append(System.getProperty("line.separator"));
                }
                mFormattedResponseErrors = error.toString();
            } else {
                mExceptionType = ExceptionType.RESPONSE_EXCEPTION;
                mRelatedException = new Exception("Cannot convert request response to Response class");
            }

        } else {
            if (mRelatedException instanceof UnknownHostException ||
                    mRelatedException instanceof ConnectException ||
                    mRelatedException instanceof SocketTimeoutException ||
                    mRelatedException instanceof SocketException) {
                mExceptionType = ExceptionType.NO_INTERNET_CONNECTION;
            } else if (mRelatedException instanceof JSONException) {
                mExceptionType = ExceptionType.NO_JSON_CONTENT;
            } else if (mRelatedException instanceof ClassCastException) {
                mExceptionType = ExceptionType.INVALID_RESPONSE_CLASS;
            } else if (mRelatedException instanceof IllegalArgumentException){
                mExceptionType = ExceptionType.NOT_FOUND;
            } else if (mRelatedException instanceof SecurityException){
                mExceptionType = ExceptionType.SECURITY;
            } else if (mRelatedException != null) {
                mExceptionType = ExceptionType.UNKNOWN_EXCEPTION;
            } else {
                mExceptionType = ExceptionType.UNKNOWN_EXCEPTION;
                mRelatedException = new Exception("Unhandled exception.");
            }
        }
    }

    public String getURLToRequest() {
        return this.mURLToRequest;
    }

    public ExceptionType getExceptionType() {
        return this.mExceptionType;
    }

    public Object getResponse() {
        return this.mResponse;
    }

    public Exception getRelatedException() {
        return this.mRelatedException;
    }

    public List<String> getResponseErrors() {
        return this.mResponseErrors;
    }

    public String getFormattedResponseErrors() {
        return this.mFormattedResponseErrors;
    }
}
