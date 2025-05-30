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

package com.bytechef.platform.workflow.task.dispatcher.test.config;

import com.bytechef.atlas.configuration.repository.WorkflowRepository;
import com.bytechef.atlas.configuration.repository.resource.ClassPathResourceWorkflowRepository;
import com.bytechef.atlas.configuration.service.WorkflowService;
import com.bytechef.atlas.configuration.service.WorkflowServiceImpl;
import com.bytechef.atlas.execution.repository.memory.InMemoryContextRepository;
import com.bytechef.atlas.execution.repository.memory.InMemoryCounterRepository;
import com.bytechef.atlas.execution.repository.memory.InMemoryJobRepository;
import com.bytechef.atlas.execution.repository.memory.InMemoryTaskExecutionRepository;
import com.bytechef.atlas.execution.service.ContextService;
import com.bytechef.atlas.execution.service.ContextServiceImpl;
import com.bytechef.atlas.execution.service.CounterService;
import com.bytechef.atlas.execution.service.CounterServiceImpl;
import com.bytechef.atlas.execution.service.JobService;
import com.bytechef.atlas.execution.service.JobServiceImpl;
import com.bytechef.atlas.execution.service.TaskExecutionService;
import com.bytechef.atlas.execution.service.TaskExecutionServiceImpl;
import com.bytechef.atlas.file.storage.TaskFileStorage;
import com.bytechef.atlas.file.storage.TaskFileStorageImpl;
import com.bytechef.config.ApplicationProperties;
import com.bytechef.file.storage.base64.service.Base64FileStorageService;
import com.bytechef.jackson.config.JacksonConfiguration;
import com.bytechef.platform.workflow.task.dispatcher.test.workflow.TaskDispatcherJobTestExecutor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * @author Ivica Cardic
 */
@ComponentScan("com.bytechef.platform.workflow.task.dispatcher")
@EnableAutoConfiguration(
    exclude = {
        DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class
    })
@EnableCaching
@EnableConfigurationProperties(ApplicationProperties.class)
@Configuration
@Import(JacksonConfiguration.class)
public class TaskDispatcherIntTestConfiguration {

    @Bean
    ClassPathResourceWorkflowRepository classPathResourceWorkflowRepository(
        ResourcePatternResolver resourcePatternResolver) {

        return new ClassPathResourceWorkflowRepository("workflows/**/*.{json|yml|yaml}", resourcePatternResolver);
    }

    @Bean
    ContextService contextService(CacheManager cacheManager) {
        return new ContextServiceImpl(new InMemoryContextRepository(cacheManager));
    }

    @Bean
    CounterService counterService(CacheManager cacheManager) {
        return new CounterServiceImpl(new InMemoryCounterRepository(cacheManager));
    }

    @Bean
    JobService jobService(CacheManager cacheManager, ObjectMapper objectMapper) {
        return new JobServiceImpl(
            new InMemoryJobRepository(cacheManager, taskExecutionRepository(cacheManager), objectMapper));
    }

    @Bean
    TaskDispatcherJobTestExecutor taskDispatcherWorkflowTestSupport(
        ContextService contextService, CounterService counterService, JobService jobService,
        TaskExecutionService taskExecutionService, TaskFileStorage taskFileStorage, WorkflowService workflowService) {

        return new TaskDispatcherJobTestExecutor(
            contextService, counterService, jobService, taskExecutionService, taskFileStorage, workflowService);
    }

    @Bean
    TaskExecutionService taskExecutionService(CacheManager cacheManager) {
        return new TaskExecutionServiceImpl(taskExecutionRepository(cacheManager));
    }

    @Bean
    InMemoryTaskExecutionRepository taskExecutionRepository(CacheManager cacheManager) {
        return new InMemoryTaskExecutionRepository(cacheManager);
    }

    @Bean
    TaskFileStorage workflowFileStorageFacade() {
        return new TaskFileStorageImpl(new Base64FileStorageService());
    }

    @Bean
    WorkflowService workflowService(CacheManager cacheManager, List<WorkflowRepository> workflowRepositories) {
        return new WorkflowServiceImpl(cacheManager, Collections.emptyList(), workflowRepositories);
    }
}
