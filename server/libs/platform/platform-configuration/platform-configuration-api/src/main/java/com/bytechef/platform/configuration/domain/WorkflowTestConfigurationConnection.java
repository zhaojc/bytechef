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

package com.bytechef.platform.configuration.domain;

import com.bytechef.platform.connection.domain.Connection;
import java.util.Objects;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Ivica Cardic
 */
@Table("workflow_test_configuration_connection")
public class WorkflowTestConfigurationConnection {

    @Column("connection_id")
    private AggregateReference<Connection, Long> connectionId;

    @Column("workflow_connection_key")
    private String workflowConnectionKey;

    @Column("workflow_node_name")
    private String workflowNodeName;

    public WorkflowTestConfigurationConnection() {
    }

    @Default
    public WorkflowTestConfigurationConnection(Long connectionId, String workflowConnectionKey,
        String workflowNodeName) {
        this.connectionId = connectionId == null ? null : AggregateReference.to(connectionId);
        this.workflowConnectionKey = workflowConnectionKey;
        this.workflowNodeName = workflowNodeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkflowTestConfigurationConnection that = (WorkflowTestConfigurationConnection) o;

        return Objects.equals(connectionId, that.connectionId)
            && Objects.equals(workflowConnectionKey, that.workflowConnectionKey) &&
            Objects.equals(workflowNodeName, that.workflowNodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionId, workflowConnectionKey, workflowNodeName);
    }

    public Long getConnectionId() {
        return connectionId.getId();
    }

    public String getWorkflowConnectionKey() {
        return workflowConnectionKey;
    }

    public String getWorkflowNodeName() {
        return workflowNodeName;
    }

    @Override
    public String toString() {
        return "WorkflowTestConfigurationConnection{" +
            "connectionId=" + connectionId +
            ", workflowConnectionKey='" + workflowConnectionKey + '\'' +
            ", workflowNodeName='" + workflowNodeName + '\'' +
            '}';
    }

    /**
     * Used by MapStruct.
     */
    public @interface Default {
    }
}
