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

package com.bytechef.component.todoist.action;

import static com.bytechef.component.OpenApiComponentHandler.PropertyType;
import static com.bytechef.component.definition.ComponentDsl.action;
import static com.bytechef.component.definition.ComponentDsl.bool;
import static com.bytechef.component.definition.ComponentDsl.object;
import static com.bytechef.component.definition.ComponentDsl.outputSchema;
import static com.bytechef.component.definition.ComponentDsl.string;
import static com.bytechef.component.definition.Context.Http.BodyContentType;
import static com.bytechef.component.definition.Context.Http.ResponseType;

import com.bytechef.component.definition.ComponentDsl;
import java.util.Map;

/**
 * Provides a list of the component actions.
 *
 * @generated
 */
public class TodoistCreateProjectAction {
    public static final ComponentDsl.ModifiableActionDefinition ACTION_DEFINITION = action("createProject")
        .title("Create Project")
        .description("Creates a new project.")
        .metadata(
            Map.of(
                "method", "POST",
                "path", "/projects", "bodyContentType", BodyContentType.JSON, "mimeType", "application/json"

            ))
        .properties(string("name").metadata(
            Map.of(
                "type", PropertyType.BODY))
            .label("Name")
            .description("Name of the project.")
            .required(true),
            string("color").metadata(
                Map.of(
                    "type", PropertyType.BODY))
                .label("Color")
                .required(false),
            bool("is_favorite").metadata(
                Map.of(
                    "type", PropertyType.BODY))
                .label("Is Project a Favorite?")
                .description("Whether the project is a favorite.")
                .required(false))
        .output(outputSchema(object().properties(string("id").description("ID of the project.")
            .required(false),
            string("name").description("Name of the project.")
                .required(false),
            string("color").description("Color of the project icon.")
                .required(false),
            string("is_favorite").description("Whether the project is a favorite.")
                .required(false),
            string("url").description("URL of the project.")
                .required(false))
            .metadata(
                Map.of(
                    "responseType", ResponseType.JSON))));

    private TodoistCreateProjectAction() {
    }
}
