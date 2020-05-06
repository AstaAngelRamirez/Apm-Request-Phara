package com.apm.request.exceptions;

import com.apm.request.enums.ExceptionType;
import com.apm.request.models.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Ing. Oscar G. Medina Cruz on 22/05/18.
 */
public class ResponseCodeParser {

    private ExceptionType mExceptionType;
    private Exception mException;

    public ResponseCodeParser(Response response){
        parseResponse(response);
    }

    private void parseResponse(Response response){
        switch (response.getCode()){
            case 200:
                mExceptionType = ExceptionType.COMPLETED_WITH_ERRORS;
                mException = new Exception("Completed request with some errors.");
                break;
            case 304:
                mExceptionType = ExceptionType.NOT_MODIFIED;
                mException = new Exception("There was no new data to return.");
                break;
            case 400:
                mExceptionType = ExceptionType.BAD_REQUEST;
                mException = new Exception("The request was invalid or cannot be otherwise served. An accompanying error message will explain further. Requests without authentication are considered invalid and will yield this response.");
                break;
            case 401:
                mExceptionType = ExceptionType.UNAUTHORIZED;
                mException = new Exception("Missing or incorrect authentication credentials or JWT Token field.");
                break;
            case 403:
                mExceptionType = ExceptionType.FORBIDDEN;
                mException = new Exception("The request is understood, but it has been refused or access is not allowed. An accompanying error message will explain why. This code is used when requests are being denied due to permissions.");
                break;
            case 404:
                mExceptionType = ExceptionType.NOT_FOUND;
                mException = new Exception("The URI requested is invalid or the resource requested, such as a user, does not exists. Also returned when the requested format is not supported by the requested method.");
                break;
            case 406:
                mExceptionType = ExceptionType.NOT_ACCEPTABLE;
                mException = new Exception("Returned when an invalid format is specified in the request.");
                break;
            case 410:
                mExceptionType = ExceptionType.GONE;
                mException = new Exception("This resource is gone. Used to indicate that an API endpoint has been turned off.");
                break;
            case 422:
                mExceptionType = ExceptionType.UNPROCESSABLE_ENTITY;
                mException = new Exception("Returned when an image uploaded and it's unable to be processed.");
                break;
            case 500:
                mExceptionType = ExceptionType.INTERNAL_SERVER_ERROR;
                mException = new Exception("Something is broken. Please post to the issue forum with additional details of your request, in case others are having similar issues.");
                break;
            case 502:
                mExceptionType = ExceptionType.BAD_GATEWAY;
                mException = new Exception("This API is down or being upgraded.");
                break;
            case 503:
                mExceptionType = ExceptionType.SERVICE_UNAVAILABLE;
                mException = new Exception("This API servers are up, but overloaded with requests. Try again later.");
                break;
            case 504:
                mExceptionType = ExceptionType.GATEWAY_TIMEOUT;
                mException = new Exception("This API servers are up, but the request couldn’t be serviced due to some failure within our stack. Try again later.");
                break;
        }
    }

    public ExceptionType getExceptionType() {
        return mExceptionType;
    }

    public Exception getException() {
        return mException;
    }
}
