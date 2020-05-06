package com.apm.request.contracts;

import android.content.Context;
import android.support.annotation.NonNull;

import com.apm.request.abstracts.RequestListener;
import com.apm.request.models.Request;

import org.json.JSONException;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/11/2017.
 */

public interface IRequestMethods<T> {

    Request buildRequest(@NonNull Context context,
                         @NonNull String webService,
                         Map<String, String> headers,
                         Map<String, String> stringParams,
                         Map<String, File> fileParams,
                         Map<String, List<File>> fileArrayParameters,
                         Map<String, List<String>> stringArrayParameters,
                         String jsonParams,
                         String responseObjectName,
                         String requestToken,
                         String requestLanguage,
                         boolean autoRegisterToken,
                         int timeout,
                         boolean retryOnTimeout,
                         RequestListener<T> requestListener);

    void get();

    void save();

    void update();

    void delete();

    T getElement(String elementName) throws JSONException;

    String toJSONString();
}