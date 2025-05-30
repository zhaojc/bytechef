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

package com.bytechef.automation.configuration.domain;

import java.time.Instant;
import java.util.Objects;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Ivica Cardic
 */
@Table("project_version")
public class ProjectVersion {

    public enum Status {

        DRAFT, PUBLISHED
    }

    @Column
    private String description;

    @Column("published_date")
    private Instant publishedDate;

    @Column
    private int status = Status.DRAFT.ordinal();

    @Column
    private int version;

    private ProjectVersion() {
    }

    public ProjectVersion(int version) {
        this.version = version;
    }

    public ProjectVersion(int version, int status, Instant publishedDate, String description) {
        this.description = description;
        this.publishedDate = publishedDate;
        this.status = status;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ProjectVersion that)) {
            return false;
        }

        return status == that.status && version == that.version && Objects.equals(description, that.description)
            && Objects.equals(publishedDate, that.publishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, publishedDate, status, version);
    }

    public String getDescription() {
        return description;
    }

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public Status getStatus() {
        return Status.values()[status];
    }

    public int getVersion() {
        return version;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishedDate(Instant publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setStatus(Status status) {
        this.status = status.ordinal();
    }

    @Override
    public String toString() {
        return "ProjectVersion{" +
            "description='" + description + '\'' +
            ", publishedDate=" + publishedDate +
            ", status=" + status +
            ", version=" + version +
            '}';
    }
}
