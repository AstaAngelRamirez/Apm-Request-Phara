package com.apm.example_request.classes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apm.request.abstracts.GenericResponse;
import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.models.Request;
import com.apm.request.models.RequestBuilder;
import com.apm.request.utils.RequestConfiguration;

import java.util.List;

/**
 * Created by Ing. Oscar G. Medina Cruz on 09/05/18.
 */
public class CustomResponse extends GenericResponse<CustomResponse> {

    private String type;
    private boolean errors;
    private String exception_name;
    private int code;
    private String code_message;
    private String message;
    private List<AugmentedRealityInfo> data;


    public List<AugmentedRealityInfo> getData() {
        return data;
    }

    public void setData(List<AugmentedRealityInfo> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public boolean hasErrors() {
        return errors;
    }

    public String getExceptionName() {
        return exception_name;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMessage() {
        return code_message;
    }

    public String getMessage() {
        return message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    public void setExceptionName(String exceptionName) {
        this.exception_name = exceptionName;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setCodeMessage(String code_message) {
        this.code_message = code_message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public Request buildRequest(RequestBuilder requestBuilder) {
        return super.buildRequest(requestBuilder, CustomResponse.class);
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration, @NonNull String webServiceName, @NonNull WebServiceType webServiceType, @Nullable RequestListener<CustomResponse> requestListener) {
        return super.buildRequest(context, CustomResponse.class, requestConfiguration, webServiceName, webServiceType, requestListener);
    }
    @Override
    public Request buildRequest(@NonNull Context context, @NonNull String webService, @Nullable RequestListener<CustomResponse> requestListener) {
        return super.buildRequest(context, webService, CustomResponse.class, requestListener);
    }

    @Override
    public Request buildRequest(@NonNull Context context, int webService, @Nullable RequestListener<CustomResponse> requestListener) {
        return super.buildRequest(context, webService, CustomResponse.class, requestListener);
    }
}
