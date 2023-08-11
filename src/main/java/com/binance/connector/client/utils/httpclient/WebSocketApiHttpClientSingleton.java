package com.binance.connector.client.utils.httpclient;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public final class WebSocketApiHttpClientSingleton {
    private static final int PING_INTERVAL = 2;
    private static final OkHttpClient httpClient = new OkHttpClient().newBuilder().pingInterval(PING_INTERVAL, TimeUnit.MINUTES).build();

    private WebSocketApiHttpClientSingleton() {
    }

    public static OkHttpClient getHttpClient() {
        return httpClient;
    }
}
