package com.apm.request.contracts;

import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.Response;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/11/2017.
 */

public interface IRequestListener<T> {

    void onRequestStart();
    void onRequestError(Exception e);
    void onRequestError(ExceptionManager em);
    void onRequestError(Response requestResponse);
    void onRequestEnd(T response, String currentToken);
}
