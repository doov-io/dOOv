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
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.FieldMetadata.isMetadata;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import java.util.Optional;
import java.util.function.Function;

import io.doov.core.FieldModel;
import io.doov.core.dsl.field.BooleanFieldInfo;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.FieldMetadata;

public class BooleanCondition extends DefaultCondition<BooleanFieldInfo, Boolean> {

    public BooleanCondition(BooleanFieldInfo field) {
        super(field);
    }

    public BooleanCondition(FieldMetadata metadata, Function<FieldModel, Optional<Boolean>> value) {
        super(metadata, value);
    }

    public final StepCondition isTrue() {
        return predicate(isMetadata(field, true),
                        model -> Optional.of(TRUE),
                        Boolean::equals);
    }

    public final StepCondition isFalse() {
        return predicate(isMetadata(field, false),
                        model -> Optional.of(FALSE),
                        Boolean::equals);
    }

}