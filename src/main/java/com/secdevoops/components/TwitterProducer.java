package com.secdevoops.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import com.secdevoops.data.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TwitterProducer implements Runnable{

    @Value(value = "${app.kafka.topic.name}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, Tweet> kafkaTemplate;

    private AtomicReference<BufferedReader> bufferedReader = new AtomicReference<>();

    private CountDownLatch countDownLatch;

    ObjectMapper mapper = new ObjectMapper();

    public void init(AtomicReference<BufferedReader> bufferedReader, CountDownLatch countDownLatch) {
        this.bufferedReader = bufferedReader;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        while(countDownLatch.getCount() != 0) {
            try {
                Tweet tweet = mapper.readValue(new String(bufferedReader.get().readLine().getBytes(StandardCharsets.UTF_8)), Tweet.class);
                log.info("Tweet = {}", tweet);
                kafkaTemplate.send(this.topic, tweet);
                log.debug("Produced tweet");
                countDownLatch.countDown();
            } catch (IOException e) {
                log.error("Unable to retrieve tweet");
            }
        }
    }

}
