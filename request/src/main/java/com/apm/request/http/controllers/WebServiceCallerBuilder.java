package com.apm.request.http.controllers;

import android.content.Context;

import com.apm.request.contracts.WebServiceCallerListener;
import com.apm.request.enums.ResponseType;
import com.apm.request.enums.RequestType;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/08/17.
 *
 * RequestBuilder class for {@link WebServiceCaller}
 *
 */

public class WebServiceCallerBuilder {

    // VARS
    private Context context;
    private String webServiceURL = "";
    private Map<String, String> webServiceStringParams;
    private Map<String, List<String>> webServiceStringArrayParams;
    private Map<String, File> webServiceFileParams;
    private Map<String, List<File>> webServiceFileArrayParams;
    private String webServiceJsonParams;
    private Map<String, String> webServiceHeaders;
    private RequestType requestType = RequestType.POST;
    private ResponseType responseType = ResponseType.JSONOBJECT;
    //private String responseObjectName = "data";
    private int connectionTimeout = 60;
    private boolean retryOnTimeOut = false;
    private String token = "";
    private boolean logEnabled = false;
    private String language = "";
    private boolean autoRegisterToken = false;

    private WebServiceCallerListener webServiceCallerListener;

    /**
     * Set the current application context
     *
     * @param context   application context
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder with(Context context) {
        this.context = context;
        return this;
    }

    /**
     * @param webServiceURL     Web service URL
     * @return                  New builder instance
     */
    public WebServiceCallerBuilder webserviceURL(String webServiceURL) {
        this.webServiceURL = webServiceURL;
        return this;
    }

    /**
     * @param params    String map of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceStringParams(Map<String, String> params) {
        this.webServiceStringParams = params;
        return this;
    }

    /**
     * @param params    String array map of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceStringArrayParams(Map<String, List<String>> params) {
        this.webServiceStringArrayParams = params;
        return this;
    }

    /**
     * @param params    File map of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceFileParams(Map<String, File> params) {
        this.webServiceFileParams = params;
        return this;
    }

    /**
     * @param params    File array map of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceFileArrayParams(Map<String, List<File>> params) {
        this.webServiceFileArrayParams = params;
        return this;
    }

    /**
     * @param params    JSON String of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceJsonParams(String params) {
        this.webServiceJsonParams = params;
        return this;
    }

    /**
     * @param params    String map of parameters
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceHeaders(Map<String, String> params) {
        this.webServiceHeaders = params;
        return this;
    }

    /**
     * @param requestType   Request type {@link RequestType}
     * @return              {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder requestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    /**
     * @param responseType   Response type {@link ResponseType}
     * @return                      {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder requestResponseType(ResponseType responseType) {
        this.responseType = responseType;
        return this;
    }

    /**
     * @param responseObjectName    Name of the wanted object
     * @return                      {@link WebServiceCallerBuilder} instance
     */
    /*public WebServiceCallerBuilder responseObjectName(String responseObjectName) {
        this.responseObjectName = responseObjectName;
        return this;
    }*/

    /**
     * @param connectionTimeout Web service connection timeout
     * @return                  {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder connectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    /**
     * Enable this to activate a retry when request timeout was reached
     * @return                  {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder retryOnTimeOut() {
        this.retryOnTimeOut = true;
        return this;
    }

    /**
     * @param token     Generated token after login process
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder currentToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * @param enable    Enable class log
     * @return          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder enableLog(boolean enable) {
        this.logEnabled = enable;
        return this;
    }

    /**
     * @param languageCode  Language code {@see http://kirste.userpage.fu-berlin.de/diverse/doc/ISO_3166.html}
     * @return              {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder language(String languageCode) {
        this.language = languageCode;
        return this;
    }

    /**
     * @param webServiceCallerListener  Listener that receive response of the web service
     * @return                          {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder webServiceCallerListener(WebServiceCallerListener webServiceCallerListener) {
        this.webServiceCallerListener = webServiceCallerListener;
        return this;
    }

    /**
     * Enable this for autor register token
     * @return  {@link WebServiceCallerBuilder} instance
     */
    public WebServiceCallerBuilder autoRegisterToken(){
        this.autoRegisterToken = true;
        return this;
    }

    /**
     * @return Create new instance of {@link WebServiceCaller}
     */
    public WebServiceCaller build() {
        return new WebServiceCaller(webServiceURL, webServiceStringParams, webServiceStringArrayParams,
                webServiceFileParams, webServiceFileArrayParams, webServiceJsonParams,
                webServiceHeaders, requestType, responseType, connectionTimeout,
                retryOnTimeOut, token, logEnabled, language, context, autoRegisterToken,
                webServiceCallerListener);
    }
}
