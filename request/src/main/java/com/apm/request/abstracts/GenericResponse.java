package com.apm.request.abstracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apm.request.contracts.IGenericRequestBuilder;
import com.apm.request.enums.WebServiceType;
import com.apm.request.models.Request;
import com.apm.request.models.RequestBuilder;
import com.apm.request.utils.RequestConfiguration;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/04/18.
 */
public abstract class GenericResponse<T> implements IGenericRequestBuilder<T> {


    @Override
    public Request buildRequest(RequestBuilder requestBuilder, Class<T> classType) {
        return requestBuilder.build(classType);
    }

    @Override
    public Request buildRequest(@NonNull Context context, Class<T> classType, @NonNull RequestConfiguration requestConfiguration, @NonNull String webServiceName, @NonNull WebServiceType webServiceType, @Nullable RequestListener<T> requestListener) {
        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context, requestConfiguration)
                .requestTo(webServiceName)
                .ofType(webServiceType)
                .listener(requestListener);

        return requestBuilder.build(classType);
    }

    @Override
    public Request buildRequest(@NonNull Context context, @NonNull String webService, @NonNull Class<T> classType, @Nullable RequestListener<T> requestListener) {
        if (webService.trim().equals("")) {
            throw new IllegalArgumentException("Web service url must have a valid url");
        }

        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context)
                .requestTo(webService);

        if (requestListener != null) {
            requestBuilder.listener(requestListener);
        }

        return requestBuilder.build(classType);
    }

    @Override
    public Request buildRequest(@NonNull Context context, int webService, @NonNull Class<T> classType, @Nullable RequestListener<T> requestListener) {
        if (webService == -1) {
            throw new IllegalArgumentException("Web service url must have a valid url");
        }

        RequestBuilder requestBuilder = new RequestBuilder()
                .with(context)
                .requestTo(webService);

        if (requestListener != null) {
            requestBuilder.listener(requestListener);
        }

        return requestBuilder.build(classType);
    }
}
