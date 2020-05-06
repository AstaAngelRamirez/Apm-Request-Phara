package com.apm.request.contracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.models.Request;
import com.apm.request.models.RequestBuilder;
import com.apm.request.utils.RequestConfiguration;
import com.apm.request.models.Response;

/**
 * Created by Ing. Oscar G. Medina Cruz on 09/05/18.
 */
public interface IRequestBuilder {

    Request buildRequest(@NonNull RequestBuilder requestBuilder);

    Request buildRequest(@NonNull Context context, @NonNull RequestConfiguration requestConfiguration,
                         @NonNull String webServiceName, @NonNull WebServiceType webServiceType,
                         @Nullable RequestListener<Response> requestListener);

    Request buildRequest(@NonNull Context context, @NonNull String webService,
                         @Nullable RequestListener<Response> requestListener);

    Request buildRequest(@NonNull Context context, @StringRes int webService,
                         @Nullable RequestListener<Response> requestListener);
}
