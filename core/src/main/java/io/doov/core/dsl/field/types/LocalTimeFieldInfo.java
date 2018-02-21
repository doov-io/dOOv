/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.doov.core.dsl.field.types;

import java.time.LocalTime;

import io.doov.core.FieldInfo;
import io.doov.core.dsl.field.DelegatingFieldInfoImpl;
import io.doov.core.dsl.impl.LocalTimeCondition;
import io.doov.core.dsl.impl.TemporalCondition;

public class LocalTimeFieldInfo extends DelegatingFieldInfoImpl<LocalTime> implements TemporalFieldInfo<LocalTime> {

    public LocalTimeFieldInfo(FieldInfo fieldInfo) {
        super(fieldInfo);
    }

    @Override
    public TemporalCondition<LocalTime> getTemporalCondition() {
        return new LocalTimeCondition(this);
    }

}