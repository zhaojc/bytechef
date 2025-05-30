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

package com.bytechef.component.asana;

import static com.bytechef.component.definition.ComponentDsl.component;
import static com.bytechef.component.definition.ComponentDsl.tool;

import com.bytechef.component.OpenApiComponentHandler;
import com.bytechef.component.asana.action.AsanaCreateProjectAction;
import com.bytechef.component.asana.action.AsanaCreateTaskAction;
import com.bytechef.component.asana.connection.AsanaConnection;
import com.bytechef.component.definition.ComponentDefinition;

/**
 * Provides the base implementation for the REST based component.
 *
 * @generated
 */
public abstract class AbstractAsanaComponentHandler implements OpenApiComponentHandler {
    private final ComponentDefinition componentDefinition = modifyComponent(
        component("asana")
            .title("Asana")
            .description(
                "Asana is a web and mobile application designed to help teams organize, track, and manage their work tasks and projects efficiently."))
                    .actions(modifyActions(AsanaCreateProjectAction.ACTION_DEFINITION,
                        AsanaCreateTaskAction.ACTION_DEFINITION))
                    .connection(modifyConnection(AsanaConnection.CONNECTION_DEFINITION))
                    .clusterElements(modifyClusterElements(tool(AsanaCreateProjectAction.ACTION_DEFINITION),
                        tool(AsanaCreateTaskAction.ACTION_DEFINITION)))
                    .triggers(getTriggers());

    @Override
    public ComponentDefinition getDefinition() {
        return componentDefinition;
    }
}
