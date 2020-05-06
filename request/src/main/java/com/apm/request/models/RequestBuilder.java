package com.apm.request.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.utils.RequestConfiguration;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/04/18.
 */
public class RequestBuilder {

    private Context mContext;
    private String mWebService;
    private Map<String, String> mRequestHeaders;
    private Map<String, String> mRequestParameters;
    private Map<String, File> mRequestFileParameters;
    private Map<String, List<File>> mRequestFileArrayParameters;
    private Map<String, List<String>> mRequestStringArrayParameters;
    private String mRequestJsonParameters;
    private String mRequestResponseObjectName;
    private String mRequestToken = "";
    private String mRequestLanguage = "";
    private boolean mAutoRegisterToken = false;
    private int mConnectionTimeout = 60;
    private boolean mRetryOnTimout = false;

    private RequestConfiguration mRequestConfiguration;
    private Object mModelRequestListener;

    /**
     * Initialize builder class with application context
     *
     * @param context Application context
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder with(@NonNull Context context) {
        this.mContext = context;
        return this;
    }

    /**
     * Initialize builder class with application context and {@link RequestConfiguration} instance
     *
     * @param context                   Application context
     * @param requestConfiguration      {@link RequestConfiguration} instance
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder with(@NonNull Context context, RequestConfiguration requestConfiguration) {
        this.mContext = context;
        this.mRequestConfiguration = requestConfiguration;

        requestLanguage(requestConfiguration.requestLanguage)
                .timeout(requestConfiguration.timeout)
                .requestLanguage(requestConfiguration.requestLanguage);

        if (requestConfiguration.autoRegisterToken) autoRegisterToken();
        if (requestConfiguration.retryOnTimeout) retryOnTimeout();

        return this;
    }

    /**
     * Initialize request to selected url
     *
     * @param webService Web service url to request
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder requestTo(String webService) {
        this.mWebService = webService;
        return this;
    }

    /**
     * Initialize request to selected url
     *
     * @param webService Web service url to request as String resource
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder requestTo(@StringRes int webService) {
        this.mWebService = mContext.getResources().getString(webService);
        return this;
    }

    /**
     * Initialize headers
     *
     * @param headers Web service headers
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder headers(Map<String, String> headers) {
        this.mRequestHeaders = headers;
        return this;
    }

    /**
     * Add header
     *
     * @param headerName  Header name
     * @param headerValue Header value
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder addHeader(String headerName, String headerValue) {
        if (this.mRequestHeaders == null) this.mRequestHeaders = new HashMap<>();
        this.mRequestHeaders.put(headerName, headerValue);
        return this;
    }

    /**
     * Initialize parameters
     *
     * @param params Web service string parameters
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder parameters(Map<String, String> params) {
        this.mRequestParameters = params;
        return this;
    }

    /**
     * Add parameter
     *
     * @param paramName  Parameter name
     * @param paramValue Parameter value
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder addParam(String paramName, String paramValue) {
        if (this.mRequestParameters == null) this.mRequestParameters = new HashMap<>();
        this.mRequestParameters.put(paramName, paramValue);
        return this;
    }

    /**
     * Initialize file parameters
     *
     * @param fileParameters Web service file parameters
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder fileParams(Map<String, File> fileParameters) {
        this.mRequestFileParameters = fileParameters;
        return this;
    }

    /**
     * Add file
     *
     * @param fileName  File name
     * @param fileValue File value
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder addFile(String fileName, File fileValue) {
        if (this.mRequestFileParameters == null) this.mRequestFileParameters = new HashMap<>();
        this.mRequestFileParameters.put(fileName, fileValue);
        return this;
    }

    /**
     * Initialize file array parameters
     *
     * @param fileArrayParameters Web service file array parameters
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder fileArrayParams(Map<String, List<File>> fileArrayParameters) {
        this.mRequestFileArrayParameters = fileArrayParameters;
        return this;
    }

    /**
     * Add file array
     *
     * @param fileName  File name
     * @param fileArray File array
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder addFileArray(String fileName, File... fileArray) {
        if (this.mRequestFileArrayParameters == null)
            this.mRequestFileArrayParameters = new HashMap<>();
        this.mRequestFileArrayParameters.put(fileName, Arrays.asList(fileArray));
        return this;
    }

    /**
     * Initialize string array parameters
     *
     * @param stringArrayParameters Web service string array parameters
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder stringArrayParams(Map<String, List<String>> stringArrayParameters) {
        this.mRequestStringArrayParameters = stringArrayParameters;
        return this;
    }

    /**
     * Add param array
     *
     * @param paramName  Parameter name
     * @param paramArray Parameter array
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder addParamArray(String paramName, String... paramArray) {
        if (this.mRequestStringArrayParameters == null)
            this.mRequestStringArrayParameters = new HashMap<>();
        this.mRequestStringArrayParameters.put(paramName, Arrays.asList(paramArray));
        return this;
    }

    /**
     * Initialize JSON string parameters
     *
     * @param jsonParameters Web service JSON string parameters
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder jsonParams(String jsonParameters) {
        this.mRequestJsonParameters = jsonParameters;
        return this;
    }

    /**
     * Initialize parent object name to be casted
     *
     * @param responseObjectName Web service response object name
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder parentObjectName(String responseObjectName) {
        this.mRequestResponseObjectName = responseObjectName;
        return this;
    }

    /**
     * Initialize current token
     *
     * @param requestToken Web service current token
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder requestToken(String requestToken) {
        this.mRequestToken = requestToken;
        return this;
    }

    /**
     * Initialize current token
     *
     * @param requestLanguagen Web service request language
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder requestLanguage(String requestLanguagen) {
        this.mRequestLanguage = requestLanguagen;
        return this;
    }

    /**
     * Enable this if you want to auto register token returned by request
     *
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder autoRegisterToken() {
        this.mAutoRegisterToken = true;
        return this;
    }

    /**
     * Establishes a connection timeout for the request
     *
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder timeout(int timeout) {
        this.mConnectionTimeout = timeout;
        return this;
    }

    /**
     * Enable this if you want to enable a retry when request time out was reached
     *
     * @return {@link RequestBuilder} instance
     */
    public RequestBuilder retryOnTimeout() {
        this.mRetryOnTimout = true;
        return this;
    }

    /**
     * Initialize base model listener to retrieve results
     *
     * @param requestListener RequestListener to retrieve request results
     * @return                {@link RequestBuilder} instance
     */
    public <T> RequestBuilder listener(RequestListener<T> requestListener) {
        this.mModelRequestListener = requestListener;
        return this;
    }

    /**
     * Set the type of request that we want to consume
     * @param webServiceType    {@link WebServiceType} type
     * @return                  {@link RequestBuilder} instance
     */
    public RequestBuilder ofType(WebServiceType webServiceType){
        if (mRequestConfiguration == null){
            throw new NullPointerException("You must initialize RequestConfiguration. Are you calling with(Context, RequestConfiguration) method?");
        }

        requestTo(mRequestConfiguration.getRequestURL(mWebService, webServiceType));

        return this;
    }

    /**
     * Concat a String to final web service route
     * @param concatString  String to be concatenated
     * @return              {@link RequestBuilder} instance
     */
    public RequestBuilder concatToRequest(String concatString){
        if (mRequestConfiguration == null){
            throw new NullPointerException("You must initialize RequestConfiguration. Are you calling with(Context, RequestConfiguration) method?");
        }

        requestTo(mWebService + concatString);

        return this;
    }

    /**
     * Concat a String to final web service route
     * @param concatStrings  String array of parametters to be concatenated
     * @return              {@link RequestBuilder} instance
     */
    public RequestBuilder concatToRequest(String... concatStrings){
        if (mRequestConfiguration == null){
            throw new NullPointerException("You must initialize RequestConfiguration. Are you calling with(Context, RequestConfiguration) method?");
        }

        StringBuilder finalConcat = new StringBuilder();

        for (String concat : concatStrings){
            finalConcat.append(concat).append("/");
        }
        finalConcat.append(";");

        requestTo(mWebService + finalConcat.toString().replace("/;", ""));

        return this;
    }

    /**
     * Build the {@link Request} class of generic type
     * @param castClass         Destination class of request
     * @param <T>               Generic class
     * @return                  Initialized {@link Request} instance
     */
    @SuppressWarnings("unchecked")
    public <T> Request build(Class<T> castClass) {
        return new Request<>(castClass)
                .buildRequest(
                        mContext,
                        mWebService,
                        mRequestHeaders,
                        mRequestParameters,
                        mRequestFileParameters,
                        mRequestFileArrayParameters,
                        mRequestStringArrayParameters,
                        mRequestJsonParameters,
                        mRequestResponseObjectName,
                        mRequestToken,
                        mRequestLanguage,
                        mAutoRegisterToken,
                        mConnectionTimeout,
                        mRetryOnTimout,
                        (RequestListener<T>) mModelRequestListener);
    }
}
