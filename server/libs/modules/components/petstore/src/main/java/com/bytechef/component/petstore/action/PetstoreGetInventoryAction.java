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

package com.bytechef.component.petstore.action;

import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.integer;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.Context.Http.ResponseType;

import com.bytechef.component.definition.ComponentDsl;
import java.util.Map;

/**
 * Provides a list of the component actions.
 *
 * @generated
 */
public class PetstoreGetInventoryAction {
    public static final ComponentDsl.ModifiableActionDefinition ACTION_DEFINITION = action("getInventory")
        .title("Returns pet inventories by status")
        .description("Returns a map of status codes to quantities")
        .metadata(
            Map.of(
                "method", "GET",
                "path", "/store/inventory"

            ))
        .properties()
        .output(outputSchema(object().additionalProperties(integer())
            .metadata(
                Map.of(
                    "responseType", ResponseType.JSON))));

    private PetstoreGetInventoryAction() {
    }
}
