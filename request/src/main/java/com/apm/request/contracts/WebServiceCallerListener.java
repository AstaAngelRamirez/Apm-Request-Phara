package com.apm.request.contracts;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/08/17.
 *
 * Communicate web service query/response with UI
 */

public interface WebServiceCallerListener {

    /**
     * Triggered when web service call starts
     */
    void onWSCallStart();

    /**
     * Triggered when web service throws an exception
     * @param e                 Web service exception
     */
    void onWSCallError(Exception e);

    /**
     * Triggered when web service all ends
     *
     * @param objectResponse    Web service response of type {@link com.apm.request.enums.ResponseType}
     * @param currentToken      Current user token
     */
    void onWSCallEnd(Object objectResponse, String currentToken);
}
