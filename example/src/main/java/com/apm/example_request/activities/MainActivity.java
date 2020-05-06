package com.apm.example_request.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.apm.example_request.R;
import com.apm.example_request.classes.CustomResponse;
import com.apm.request.abstracts.RequestListener;
import com.apm.request.enums.WebServiceType;
import com.apm.request.exceptions.ExceptionManager;
import com.apm.request.models.RequestBuilder;
import com.apm.request.utils.RequestConfiguration;
import com.apm.request.models.Response;
import com.google.gson.Gson;
import com.yuyh.jsonviewer.library.JsonRecyclerView;

/**
 * @author Ing. Oscar G. Medina Cruz on 08/05/18.
 */
public class MainActivity extends AppCompatActivity {

    private RequestConfiguration mRequestConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequestConfiguration = RequestConfiguration.newInstance(getApplicationContext(), R.raw.request);

        ((JsonRecyclerView) findViewById(R.id.rv_json)).setTextSize(16);
        findViewById(R.id.fab_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (findViewById(R.id.progress_bar).getVisibility() == View.INVISIBLE) {
                    createSimpleRequest();
                    //createCustomRequest();
                }
            }
        });
    }

    /**
     * Create a simple request, with minimum requirements.
     */
    private void createSimpleRequest() {
        new Response()
               .buildRequest(getApplicationContext(), mRequestConfiguration, "ar", WebServiceType.GET, mRequestListener).get();
        /*new Response()
                .buildRequest(getApplicationContext(), R.string.complete_ws_ar, mRequestListener).get();*/
        /*new CustomResponse()
                .buildRequest(getApplicationContext(), mRequestConfiguration, "ar", WebServiceType.GET, mCustomRequestListener).get();/*
        /*new CustomResponse()
                .buildRequest(getApplicationContext(), R.string.complete_ws_ar, mCustomRequestListener).get();*/
    }

    /**
     * Create a request using {@link RequestBuilder} class to construct custom request with custom configuration
     */
    private void createCustomRequest() {
        RequestBuilder requestBuilder =
                new RequestBuilder()
                        .with(getApplicationContext(), mRequestConfiguration)
                        .requestTo("ar")
                        .ofType(WebServiceType.GET)
                        //.requestTo(R.string.complete_ws_ar)
                        .listener(mRequestListener);

        new Response().buildRequest(requestBuilder).get();
    }

    /**
     * {@link RequestListener} of type {@link Response} for get a generic response of request
     */
    private RequestListener<Response> mRequestListener =
            new RequestListener<Response>() {
                @Override
                public void onRequestStart() {
                    super.onRequestStart();

                    findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    findViewById(R.id.linear_layout_test).setVisibility(View.INVISIBLE);
                }

                @Override
                public void onRequestError(ExceptionManager em) {
                    super.onRequestError(em);
                    findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                    em.getRelatedException().printStackTrace();
                    Snackbar.make(findViewById(R.id.fab_go), "Error!. See log for details", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onRequestEnd(Response response, String currentToken) {
                    super.onRequestEnd(response, currentToken);

                    ((JsonRecyclerView) findViewById(R.id.rv_json))
                            .bindJson(new Gson().toJson(response));

                    // response data can be parsed to any kind of class, if data type is the same as
                    // desired class for example:
                    //((JsonRecyclerView) findViewById(R.id.rv_json))
                    //        .bindJson(response.getData(String.class));
                    //
                    // OR
                    //
                    //AugmentedRealityInfo[] augmentedRealityInfos = response.getData(AugmentedRealityInfo[].class);

                    findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                }
            };

    /**
     * {@link RequestListener} of type {@link CustomResponse} for get a custom response of request
     */
    private RequestListener<CustomResponse> mCustomRequestListener =
            new RequestListener<CustomResponse>() {
                @Override
                public void onRequestStart() {
                    super.onRequestStart();

                    findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                    findViewById(R.id.linear_layout_test).setVisibility(View.INVISIBLE);
                }

                @Override
                public void onRequestError(ExceptionManager em) {
                    super.onRequestError(em);
                    findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                    em.getRelatedException().printStackTrace();
                    Snackbar.make(findViewById(R.id.fab_go), "Error!. See log for details", Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onRequestEnd(CustomResponse customResponse, String currentToken) {
                    super.onRequestEnd(customResponse, currentToken);

                    ((JsonRecyclerView) findViewById(R.id.rv_json))
                            .bindJson(new Gson().toJson(customResponse));

                    // response data can be parsed to any kind of class, if data type is the same as
                    // desired class for example:
                    //((JsonRecyclerView) findViewById(R.id.rv_json))
                    //        .bindJson(response.getData(String.class));
                    //
                    // OR
                    //
                    //List<AugmentedRealityInfo> augmentedRealityInfos = customResponse.getData();

                    findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                }
            };
}
