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

package com.bytechef.component.http.client.action;

import static com.bytechef.component.definition.ComponentDsl.action;

import com.bytechef.component.definition.ComponentDsl.ModifiableActionDefinition;
import com.bytechef.component.definition.Context.Http.RequestMethod;
import com.bytechef.component.http.client.constant.HttpClientComponentConstants;
import com.bytechef.component.http.client.util.HttpClientActionUtils;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class HttpClientGetAction {

    public static final ModifiableActionDefinition ACTION_DEFINITION = action("get")
        .title("GET")
        .description("The request method to use.")
        .properties(
            HttpClientActionUtils.toArray(
                //
                // Common properties
                //

                HttpClientComponentConstants.COMMON_PROPERTIES,
                //
                // Options
                //

                HttpClientActionUtils.options(false)))
        .output()
        .perform(HttpClientActionUtils.getPerform(RequestMethod.GET));
}
