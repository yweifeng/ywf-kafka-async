package com.ywf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;


@Service
public class HelloProducerServiceImpl implements HelloProducerService {

    private Logger logger = LoggerFactory.getLogger(HelloProducerServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendSyncHello(String helloQueue, String message) throws InterruptedException, ExecutionException {
        logger.debug("发送信息");
        try {
            kafkaTemplate.send(helloQueue, message).get();
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("消费成功" + System.currentTimeMillis());
    }

    @Override
    public void sendAsyncHello(String helloQueue, String message) {
        logger.debug("发送信息");
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(helloQueue, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logger.debug("消费成功" + System.currentTimeMillis());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.debug("消费失败");
                ex.getStackTrace();
            }
        });
    }
}
