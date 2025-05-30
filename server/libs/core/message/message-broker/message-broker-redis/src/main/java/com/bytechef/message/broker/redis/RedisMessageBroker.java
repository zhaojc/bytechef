/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.message.broker.redis;

import com.bytechef.message.Retryable;
import com.bytechef.message.broker.MessageBroker;
import com.bytechef.message.broker.redis.serializer.RedisMessageSerializer;
import com.bytechef.message.route.MessageRoute;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

/**
 * @author Ivica Cardic
 */
public class RedisMessageBroker implements MessageBroker {

    private static final Logger logger = LoggerFactory.getLogger(RedisMessageBroker.class);

    private final RedisMessageSerializer redisMessageSerializer;
    private final StringRedisTemplate stringRedisTemplate;

    @SuppressFBWarnings("EI2")
    public RedisMessageBroker(
        RedisMessageSerializer redisMessageSerializer, StringRedisTemplate stringRedisTemplate) {

        this.redisMessageSerializer = redisMessageSerializer;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void send(MessageRoute messageRoute, Object message) {
        Assert.notNull(messageRoute, "'messageRoute' must not be null");

        if (message instanceof Retryable retryable) {
            delay(retryable.getRetryDelayMillis());
        }

        if (messageRoute.isControlExchange()) {
            sendMessageToTopic(messageRoute.getName(), message);
        } else {
            sendMessageToQueue(messageRoute.getName(), message);
        }
    }

    private void sendMessageToQueue(String queueName, Object message) {
        MapRecord<String, String, String> messageObjectRecord = StreamRecords.newRecord()
            .ofMap(Map.of("message", redisMessageSerializer.serialize(message)))
            .withStreamKey(queueName);

        stringRedisTemplate.opsForStream()
            .add(messageObjectRecord);

//        redisSMQ.sendMessage()
//            .qname(queueName)
//            .message(redisMessageSerializer.serialize(message))
//            .exec();
    }

    private void sendMessageToTopic(String queueName, Object message) {
        stringRedisTemplate.convertAndSend(queueName, redisMessageSerializer.serialize(message));
    }

    private void delay(long value) {
        try {
            TimeUnit.MILLISECONDS.sleep(value);
        } catch (InterruptedException e) {
            if (logger.isTraceEnabled()) {
                logger.trace(e.getMessage(), e);
            }
        }
    }
}
