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

package com.bytechef.platform.configuration.service;

import com.bytechef.atlas.configuration.domain.Workflow;
import com.bytechef.platform.configuration.domain.WorkflowNodeTestOutput;
import com.bytechef.platform.definition.WorkflowNodeType;
import com.bytechef.platform.domain.OutputResponse;
import java.time.Instant;
import java.util.Optional;
import javax.annotation.Nullable;

/**
 * @author Ivica Cardic
 */
public interface WorkflowNodeTestOutputService {

    void deleteWorkflowNodeTestOutput(String workflowId, String workflowNodeName);

    boolean checkWorkflowNodeTestOutputExists(
        String workflowId, String workflowNodeName, @Nullable Instant createdDate);

    Optional<WorkflowNodeTestOutput> fetchWorkflowTestNodeOutput(
        String workflowId, String workflowNodeName);

    void removeUnusedNodeTestOutputs(Workflow workflow);

    WorkflowNodeTestOutput save(
        String workflowId, String workflowNodeName, WorkflowNodeType workflowNodeType, OutputResponse outputResponse);

    void updateWorkflowId(String oldWorkflowId, String newWorkflowId);
}
