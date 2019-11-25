package com.ywf;

import java.util.concurrent.ExecutionException;

public interface HelloProducerService {
    public void sendSyncHello(String helloQueue, String message) throws InterruptedException, ExecutionException;

    public void sendAsyncHello(String helloQueue, String message);
}
