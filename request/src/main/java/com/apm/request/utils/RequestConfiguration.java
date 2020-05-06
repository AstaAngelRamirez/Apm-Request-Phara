package com.apm.request.utils;

import android.content.Context;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;

import com.apm.request.enums.WebServiceType;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Ing. Oscar G. Medina Cruz on 15/05/18.
 */
public class RequestConfiguration {

    private String hostURL = null;
    private Map<String, WebServiceDefinition> webServices = null;

    public String requestLanguage = "";
    public boolean autoRegisterToken = false;
    public int timeout = 60;
    public boolean retryOnTimeout = false;

    /**
     *
     * @param context
     * @param configFile
     * @return
     */
    public static RequestConfiguration newInstance(Context context, @RawRes int configFile) {
        return new Gson().fromJson(FileUtils.ReadRawTextFile(context, configFile), RequestConfiguration.class);
    }

    /**
     *
     * @param json
     * @return
     */
    public static RequestConfiguration newInstance(String json) {
        return new Gson().fromJson(json, RequestConfiguration.class);
    }

    /**
     *
     * @param jsonObject
     * @return
     */
    public static RequestConfiguration newInstance(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), RequestConfiguration.class);
    }

    /**
     *
     * @return
     */
    public String getHostURL() {
        return this.hostURL;
    }

    /**
     *
     * @param webServiceName
     * @return
     */
    public WebServiceDefinition getWebServiceDefinition(String webServiceName) {
        if (webServices == null) {
            throw new NullPointerException("Your configuration doesn't contains webServices object");
        }

        if (!webServices.containsKey(webServiceName)) {
            throw new NoSuchElementException("Your webServices object doesn't contains " + webServiceName + " web service");
        }

        return webServices.get(webServiceName);
    }

    /**
     *
     * @param webServiceName
     * @param webServiceType
     * @return
     */
    public String getRequestURL(String webServiceName, WebServiceType webServiceType) {
        if (webServices == null) {
            throw new NullPointerException("Your configuration doesn't contains webServices object");
        }

        if (!webServices.containsKey(webServiceName)) {
            throw new NoSuchElementException("Your webServices object doesn't contains " + webServiceName + " web service");
        }

        if (hostURL.equals("")) {
            throw new NullPointerException("Your configuration doesn't contains hostURL object");
        }

        String webService = "";

        switch (webServiceType) {
            case GET:
                webService = getWebServiceDefinition(webServiceName).get();
                break;
            case CREATE:
                webService = getWebServiceDefinition(webServiceName).create();
                break;
            case UPDATE:
                webService = getWebServiceDefinition(webServiceName).update();
                break;
            case DELETE:
                webService = getWebServiceDefinition(webServiceName).delete();
                break;
        }

        if (webService.equals("")) {
            throw new NullPointerException("Your configuration doesn't contains " + webServiceType.toString().toLowerCase() + " object");
        }


        return hostURL + webService;
    }

    /**
     *
     */
    public class WebServiceDefinition {
        private String get = null;
        private String create = null;
        private String update = null;
        private String delete = null;

        public String get() {
            return get;
        }

        public void setGet(String get) {
            this.get = get;
        }

        public String create() {
            return create;
        }

        public void setCreate(String create) {
            this.create = create;
        }

        public String update() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String delete() {
            return delete;
        }

        public void setDelete(String delete) {
            this.delete = delete;
        }
    }

}
