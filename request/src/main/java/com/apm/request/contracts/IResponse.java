package com.apm.request.contracts;

/**
 * Created by Ing. Oscar G. Medina Cruz on 28/04/18.
 */
public interface IResponse {

    String getType();
    boolean hasErrors();
    String getExceptionName();
    int getCode();
    String getCodeMessage();
    String getMessage();
    <T> T getData(Class<T> classType);
    Object getData();

    void setType(String type);
    void setErrors(boolean errors);
    void setExceptionName(String exceptionName);
    void setCode(int code);
    void setCodeMessage(String code_message);
    void setMessage(String message);
    void setData(Object data);
}
