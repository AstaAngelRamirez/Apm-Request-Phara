package com.apm.request.abstracts;

import com.apm.request.contracts.IRequestListener;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

/**
 * Created by Ing. Oscar G. Medina Cruz on 30/04/18.
 */
public abstract class RequestListener<T> implements IRequestListener<T> {
    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestError(Exception e) {

    }

    @Override
    public void onRequestError(ExceptionManager em) {

    }

    @Override
    public void onRequestError(Response requestResponse) {

    }

    @Override
    public void onRequestEnd(T response, String currentToken) {
    }
}
