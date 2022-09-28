package com.secdevoops.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import com.secdevoops.data.Tweet;

@Slf4j
public class TweetDataSerializer implements Serializer<Tweet> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String s, Tweet tweet) {
        if (tweet == null){
            return null;
        }
        try {
            return mapper.writeValueAsBytes(tweet);
        } catch (JsonProcessingException e) {
            log.error("Unable to serialize object", e);
            throw new SerializationException("Error while serializing TweetData object");
        }
    }

    @Override
    public void close() {
    }
}
