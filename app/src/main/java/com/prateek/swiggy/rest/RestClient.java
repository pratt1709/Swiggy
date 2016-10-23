package com.prateek.swiggy.rest;

import com.prateek.swiggy.rest.Request.PizzaResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


/**
 * Created by prateek.kesarwani on 21/10/16.
 */

public interface RestClient {

    String end_point = "https://api.myjson.com/";

    String GET_CUSTOMIZE_OPTIONS = "/bins/3b0u2/";

    @GET(GET_CUSTOMIZE_OPTIONS)
    Call<PizzaResponse> getCustomizeOptions();

    class Implementation {

        private static Retrofit retrofit;
        private static RestClient restClient;

        public static RestClient getClient() {

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(end_point)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }

            if (restClient == null) {
                restClient = retrofit.create(RestClient.class);
            }

            return restClient;
        }
    }
}