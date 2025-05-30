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

package com.bytechef.component.github.action;

import static com.bytechef.component.github.constant.GithubConstants.FILTER;
import static com.bytechef.component.github.constant.GithubConstants.STATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.bytechef.component.definition.Parameters;
import com.bytechef.component.definition.TypeReference;
import com.bytechef.component.test.definition.MockParametersFactory;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * @author Marija Horvat
 */
public class GithubListIssuesActionTest extends AbstractGithubActionTest {

    private final Parameters mockedParameters = MockParametersFactory.create(Map.of(FILTER, "all", STATE, "all"));
    private final ArgumentCaptor<Map<String, List<String>>> queryArgumentCaptor = ArgumentCaptor.forClass(Map.class);

    @Test
    void testPerform() {
        when(mockedExecutor.queryParameters(queryArgumentCaptor.capture()))
            .thenReturn(mockedExecutor);
        when(mockedResponse.getBody(any(TypeReference.class)))
            .thenReturn(responseList);

        Object result = GithubListIssuesAction.perform(mockedParameters, mockedParameters, mockedContext);

        assertEquals(responseList, result);

        assertEquals(Map.of(FILTER, List.of("all"), STATE, List.of("all")), queryArgumentCaptor.getValue());
    }
}
