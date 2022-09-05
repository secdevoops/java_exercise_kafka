package org.interview.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.interview.data.Tweet;

@Slf4j
public class TweetDataDeserializer implements Deserializer<Tweet> {

    ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Tweet deserialize(String s, byte[] bytes) {
        try {
            if (bytes == null){
                return null;
            }
            return mapper.readValue(new String(bytes, "UTF-8"), Tweet.class);
        } catch (Exception e) {
            log.error("Unable to deserialize object", e);
            throw new SerializationException("Error when deserializing byte[] to Tweet");
        }
    }

    @Override
    public void close() {
    }
}
