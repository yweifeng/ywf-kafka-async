    package com.ywf;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.kafka.annotation.KafkaListener;
    import org.springframework.stereotype.Component;

    @Component
    public class HelloConsumerService {
        private Logger logger = LoggerFactory.getLogger(HelloConsumerService.class);

        @KafkaListener(topics = {"app-sync-log","app-async-log"})
        public void receive(String message){
            logger.info("------hello：消费者处理消息------"+message);
            System.out.println("消费完成"+System.currentTimeMillis()+"ms");
            logger.debug(message);
        }
    }
