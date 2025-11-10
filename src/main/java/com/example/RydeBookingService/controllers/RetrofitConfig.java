package com.example.RydeBookingService.controllers;


import com.example.RydeBookingService.apis.LocationServiceApi;
import com.example.RydeBookingService.apis.RydeSocketApi;
import com.netflix.discovery.EurekaClient;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.codehaus.stax2.ri.SingletonIterator.create;

@Configuration
public class RetrofitConfig {

    @Autowired
    private EurekaClient eurekaClient;

    private String getServiceUrl(String serviceName){
        return eurekaClient.getNextServerFromEureka(serviceName , false).getHomePageUrl();
    }

    @Bean
    public LocationServiceApi locationServiceApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("RYDEPROJECT-LOCATIONSERVICE"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
        .create(LocationServiceApi.class);
    }



    @Bean
    public RydeSocketApi rydeSocketApi(){
        return new Retrofit.Builder()
                .baseUrl(getServiceUrl("RYDEWEBSOCKETSERVER"))
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().build())
                .build()
                .create(RydeSocketApi.class);
    }
}
