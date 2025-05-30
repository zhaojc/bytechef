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

package com.bytechef.platform.component.definition.ai.agent.rag;

import com.bytechef.component.definition.ClusterElementDefinition.ClusterElementType;
import com.bytechef.component.definition.Parameters;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;

/**
 * @author Ivica Cardic
 */
@FunctionalInterface
public interface DocumentJoinerFunction {

    /**
     *
     */
    ClusterElementType DOCUMENT_JOINER = new ClusterElementType("DOCUMENT_JOINER", "documentJoiner", "Document Joiner");

    /**
     * @param inputParameters
     * @param connectionParameters
     * @return
     * @throws Exception
     */
    ConcatenationDocumentJoiner apply(
        Parameters inputParameters, Parameters connectionParameters) throws Exception;
}
