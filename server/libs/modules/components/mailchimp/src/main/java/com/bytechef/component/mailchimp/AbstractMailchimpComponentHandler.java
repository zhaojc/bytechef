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

package com.bytechef.component.mailchimp;

import static com.bytechef.component.definition.ComponentDsl.component;
import static com.bytechef.component.definition.ComponentDsl.tool;

import com.bytechef.component.OpenApiComponentHandler;
import com.bytechef.component.definition.ComponentDefinition;
import com.bytechef.component.mailchimp.action.MailchimpAddMemberToListAction;
import com.bytechef.component.mailchimp.connection.MailchimpConnection;

/**
 * Provides the base implementation for the REST based component.
 *
 * @generated
 */
public abstract class AbstractMailchimpComponentHandler implements OpenApiComponentHandler {
    private final ComponentDefinition componentDefinition = modifyComponent(
        component("mailchimp")
            .title("Mailchimp")
            .description("Mailchimp is a marketing automation and email marketing platform."))
                .actions(modifyActions(MailchimpAddMemberToListAction.ACTION_DEFINITION))
                .connection(modifyConnection(MailchimpConnection.CONNECTION_DEFINITION))
                .clusterElements(modifyClusterElements(tool(MailchimpAddMemberToListAction.ACTION_DEFINITION)))
                .triggers(getTriggers());

    @Override
    public ComponentDefinition getDefinition() {
        return componentDefinition;
    }
}
