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

package com.bytechef.component.webhook.trigger;

import static com.bytechef.component.definition.ComponentDsl.placeholder;
import static com.bytechef.component.definition.ComponentDsl.trigger;
import static com.bytechef.component.webhook.constant.WebhookConstants.BODY;
import static com.bytechef.component.webhook.constant.WebhookConstants.HEADERS;
import static com.bytechef.component.webhook.constant.WebhookConstants.METHOD;
import static com.bytechef.component.webhook.constant.WebhookConstants.PARAMETERS;

import com.bytechef.component.definition.ComponentDsl.ModifiableTriggerDefinition;
import com.bytechef.component.definition.TriggerDefinition.TriggerType;
import com.bytechef.component.webhook.util.WebhookUtils;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class WebhookAutoRespondWithHTTP200Trigger {

    public static final ModifiableTriggerDefinition TRIGGER_DEFINITION = trigger("autoRespondWithHTTP200")
        .title("Auto Respond with HTTP 200 Status")
        .description(
            "The webhook trigger always replies immediately with an HTTP 200 status code in response to any incoming " +
                "webhook request. This guarantees execution of the webhook trigger, but does not involve any " +
                "validation of the received request.")
        .type(TriggerType.STATIC_WEBHOOK)
        .output(placeholder(Map.of(METHOD, "POST", HEADERS, Map.of(), PARAMETERS, Map.of(), BODY, Map.of())))
        .webhookRequest(WebhookUtils::getWebhookResult);
}
