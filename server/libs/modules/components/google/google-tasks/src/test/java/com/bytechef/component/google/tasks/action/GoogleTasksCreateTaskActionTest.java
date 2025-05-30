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

package com.bytechef.component.google.tasks.action;

import static com.bytechef.component.google.tasks.constant.GoogleTasksConstants.STATUS;
import static com.bytechef.component.google.tasks.constant.GoogleTasksConstants.TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bytechef.component.definition.Context.Http;
import com.bytechef.component.definition.Parameters;
import com.bytechef.component.test.definition.MockParametersFactory;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author Marija Horvat
 */
class GoogleTasksCreateTaskActionTest extends AbstractGoogleTasksActionTest {

    private final Parameters mockedParameters = MockParametersFactory.create(
        Map.of(TITLE, "test", STATUS, "needsAction"));

    @Test
    void testPerform() {
        Object result = GoogleTasksCreateTaskAction.perform(mockedParameters, mockedParameters, mockedActionContext);

        assertEquals(responseMap, result);

        Http.Body capturedBody = bodyArgumentCaptor.getValue();
        assertEquals(Map.of(TITLE, "test", STATUS, "needsAction"), capturedBody.getContent());
    }
}
