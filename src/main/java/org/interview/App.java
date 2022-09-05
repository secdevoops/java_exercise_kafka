package org.interview;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.interview.oauth.twitter.TwitterAuthenticationException;
import org.interview.services.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    @Value(value = "${app.kafka.topic.name}")
    private String topic;

    @Autowired
    private TwitterService twitterService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws TwitterAuthenticationException, IOException, InterruptedException {
        twitterService.findTweets();
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(topic)
                .partitions(1)
                .replicas(1)
                .build();
    }
}