/*
 * Copyright 2025 ByteChef
 *
 * Licensed under the ByteChef Enterprise license (the "Enterprise License");
 * you may not use this file except in compliance with the Enterprise License.
 */

package com.bytechef.ee.embedded.configuration.domain;

import com.bytechef.commons.data.jdbc.wrapper.MapWrapper;
import com.bytechef.commons.util.MapUtils;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @version ee
 *
 * @author Ivica Cardic
 */
@Table("integration_instance_configuration_workflow")
public class IntegrationInstanceConfigurationWorkflow implements Comparable<IntegrationInstanceConfigurationWorkflow> {

    @CreatedBy
    @Column("created_by")
    private String createdBy;

    @Column("created_date")
    @CreatedDate
    private Instant createdDate;

    @Column
    private MapWrapper inputs = new MapWrapper();

    @Column
    private boolean enabled;

    @Id
    private Long id;

    @Column("last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    @Column("last_modified_date")
    @LastModifiedDate
    private Instant lastModifiedDate;

    @Column("integration_instance_configuration_id")
    private AggregateReference<IntegrationInstanceConfiguration, Long> integrationInstanceConfigurationId;

    @MappedCollection(idColumn = "integration_instance_configuration_workflow_id")
    private Set<IntegrationInstanceConfigurationWorkflowConnection> connections = Collections.emptySet();

    @Version
    private int version;

    @Column("workflow_id")
    private String workflowId;

    public IntegrationInstanceConfigurationWorkflow() {
    }

    public IntegrationInstanceConfigurationWorkflow(
        List<IntegrationInstanceConfigurationWorkflowConnection> connections, Map<String, Object> inputs,
        String workflowId) {

        this.connections = new HashSet<>(connections);
        this.inputs = new MapWrapper(inputs);
        this.workflowId = workflowId;
    }

    @Override
    public int compareTo(IntegrationInstanceConfigurationWorkflow integrationInstanceConfigurationWorkflow) {
        return workflowId.compareTo(integrationInstanceConfigurationWorkflow.workflowId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntegrationInstanceConfigurationWorkflow that = (IntegrationInstanceConfigurationWorkflow) o;

        return Objects.equals(id, that.id) && Objects.equals(workflowId, that.workflowId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<IntegrationInstanceConfigurationWorkflowConnection> getConnections() {
        return List.copyOf(connections);
    }

    public int getConnectionsCount() {
        return connections.size();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Map<String, ?> getInputs() {
        return Collections.unmodifiableMap(inputs.getMap());
    }

    public Long getId() {
        return id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Long getIntegrationInstanceConfigurationId() {
        return integrationInstanceConfigurationId.getId();
    }

    public int getVersion() {
        return version;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setConnections(
        List<IntegrationInstanceConfigurationWorkflowConnection> integrationInstanceWorkflowConnections) {
        if (integrationInstanceWorkflowConnections != null) {
            this.connections = new HashSet<>(integrationInstanceWorkflowConnections);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setInputs(Map<String, ?> inputs) {
        if (!MapUtils.isEmpty(inputs)) {
            this.inputs = new MapWrapper(inputs);
        }
    }

    public void setIntegrationInstanceConfigurationId(Long integrationInstanceConfigurationId) {
        if (integrationInstanceConfigurationId != null) {
            this.integrationInstanceConfigurationId = AggregateReference.to(integrationInstanceConfigurationId);
        }
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public String toString() {
        return "IntegrationInstanceConfigurationWorkflow{" +
            "createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", inputs=" + inputs +
            ", enabled=" + enabled +
            ", id=" + id +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", integrationInstanceConfigurationId=" + integrationInstanceConfigurationId +
            ", connections=" + connections +
            ", version=" + version +
            ", workflowId='" + workflowId + '\'' +
            '}';
    }
}
