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

package com.bytechef.component.ai.llm.hugging.face;

import static com.bytechef.component.definition.ComponentDsl.component;

import com.bytechef.component.ComponentHandler;
import com.bytechef.component.ai.llm.hugging.face.action.HuggingFaceChatAction;
import com.bytechef.component.ai.llm.hugging.face.cluster.HuggingFaceChatModel;
import com.bytechef.component.ai.llm.hugging.face.connection.HuggingFaceConnection;
import com.bytechef.component.definition.ComponentCategory;
import com.bytechef.component.definition.ComponentDefinition;
import com.google.auto.service.AutoService;

/**
 * @author Monika Domiter
 * @author Marko Kriskovic
 */
@AutoService(ComponentHandler.class)
public class HuggingFaceComponentHandler implements ComponentHandler {

    private static final ComponentDefinition COMPONENT_DEFINITION = component("huggingFace")
        .title("Hugging Face")
        .description(
            "Hugging Face is on a journey to advance and democratize artificial intelligence through open source " +
                "and open science.")
        .icon("path:assets/hugging-face.svg")
        .categories(ComponentCategory.ARTIFICIAL_INTELLIGENCE)
        .connection(HuggingFaceConnection.CONNECTION_DEFINITION)
        .actions(HuggingFaceChatAction.ACTION_DEFINITION)
        .clusterElements(HuggingFaceChatModel.CLUSTER_ELEMENT_DEFINITION);

    @Override
    public ComponentDefinition getDefinition() {
        return COMPONENT_DEFINITION;
    }
}
