package com.secdevoops.components;

import java.util.concurrent.CountDownLatch;
import lombok.extern.slf4j.Slf4j;
import com.secdevoops.data.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitterConsumer{

    @Value(value = "${app.max_tweets}")
    private int maxTweets;

    @Value(value = "${app.max_time_in_milliseconds}")
    private int maxTimeInMilliseconds;

    @Autowired
    private TwitterQueue twitterQueue;

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    private CountDownLatch countDownLatch = new CountDownLatch(maxTweets);


    public void init(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
        start();
    }

    @KafkaListener(id="listener_id", autoStartup = "false", topics = "${app.kafka.topic.name}")
    public void listener(Tweet tweet, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) throws InterruptedException {
        log.info("Tweet = {}", tweet);
        synchronized (this) {
            if(twitterQueue.getTweetList().get().size()<maxTweets) {
                twitterQueue.getTweetList().get().add(tweet);
            }
        }
        this.countDownLatch.countDown();
    }

    public void start() {
        kafkaListenerEndpointRegistry.getListenerContainer("listener_id").start();
    }

    public void stop() {
        kafkaListenerEndpointRegistry.getListenerContainer("listener_id").stop();
    }
}
