/*
 * Copyright 2016-2020 the original author or authors.
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
 *
 * Modifications copyright (C) 2025 ByteChef
 */

package com.bytechef.message.broker.sync;

import com.bytechef.commons.util.ConvertUtils;
import com.bytechef.commons.util.JsonUtils;
import com.bytechef.message.broker.MessageBroker;
import com.bytechef.message.route.MessageRoute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;

/**
 * a simple, non-thread-safe implementation of the {@link MessageBroker} interface. Useful for testing.
 *
 * @author Arik Cohen
 * @since Jul 10, 2016
 */
public class SyncMessageBroker implements MessageBroker {

    private final Map<MessageRoute, List<Receiver>> receiverMap = new HashMap<>();

    @Override
    public void send(MessageRoute messageRoute, Object message) {
        Assert.notNull(messageRoute, "'messageRoute' must not be null");

        List<Receiver> receivers = receiverMap.get(messageRoute);

        Assert.isTrue(receivers != null && !receivers.isEmpty(), "no listeners subscribed for: " + messageRoute);

        for (Receiver receiver : Validate.notNull(receivers, "receivers")) {
            receiver.receive(
                ConvertUtils.convertValue(JsonUtils.read(JsonUtils.write(message)), message.getClass()));
        }
    }

    public void receive(MessageRoute messageRoute, Receiver receiver) {
        List<Receiver> receivers = receiverMap.computeIfAbsent(messageRoute, k -> new ArrayList<>());

        receivers.add(receiver);
    }

    public interface Receiver {
        void receive(Object message);
    }
}
