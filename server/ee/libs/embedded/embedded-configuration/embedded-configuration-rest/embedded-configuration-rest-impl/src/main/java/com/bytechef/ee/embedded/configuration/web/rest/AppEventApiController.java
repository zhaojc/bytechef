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

package com.bytechef.ee.embedded.configuration.web.rest;

import com.bytechef.atlas.coordinator.annotation.ConditionalOnCoordinator;
import com.bytechef.commons.util.CollectionUtils;
import com.bytechef.ee.embedded.configuration.domain.AppEvent;
import com.bytechef.ee.embedded.configuration.service.AppEventService;
import com.bytechef.ee.embedded.configuration.web.rest.model.AppEventModel;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Ivica Cardic
 */
@RestController
@RequestMapping("${openapi.openAPIDefinition.base-path.embedded:}/internal")
@ConditionalOnCoordinator
public class AppEventApiController implements AppEventApi {

    private final AppEventService appEventService;
    private final ConversionService conversionService;

    @SuppressFBWarnings("EI")
    public AppEventApiController(AppEventService appEventService, ConversionService conversionService) {
        this.appEventService = appEventService;
        this.conversionService = conversionService;
    }

    @Override
    @SuppressFBWarnings("NP")
    public ResponseEntity<Long> createAppEvent(AppEventModel appEventModel) {
        AppEvent appEvent = appEventService.create(conversionService.convert(appEventModel, AppEvent.class));

        return ResponseEntity.ok(appEvent.getId());
    }

    @Override
    public ResponseEntity<Void> deleteAppEvent(Long id) {
        appEventService.delete(id);

        return ResponseEntity.ok()
            .build();
    }

    @Override
    public ResponseEntity<AppEventModel> getAppEvent(Long id) {
        return ResponseEntity.ok(conversionService.convert(appEventService.getAppEvent(id), AppEventModel.class));
    }

    @Override
    public ResponseEntity<List<AppEventModel>> getAppEvents() {
        return ResponseEntity.ok(
            CollectionUtils.map(
                appEventService.getAppEvents(), appEvent -> conversionService.convert(appEvent, AppEventModel.class)));
    }

    @Override
    @SuppressFBWarnings("NP")
    public ResponseEntity<Void> updateAppEvent(Long id, AppEventModel appEventModel) {
        appEventService.update(conversionService.convert(appEventModel, AppEvent.class));

        return ResponseEntity.noContent()
            .build();
    }
}
