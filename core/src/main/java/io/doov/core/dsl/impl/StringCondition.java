/*
 * Copyright 2017 Courtanet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package io.doov.core.dsl.impl;

import static io.doov.core.dsl.meta.predicate.LeafPredicateMetadata.*;

import java.util.Optional;
import java.util.function.BiFunction;

import io.doov.core.dsl.DslField;
import io.doov.core.dsl.DslModel;
import io.doov.core.dsl.lang.Context;
import io.doov.core.dsl.lang.StepCondition;
import io.doov.core.dsl.meta.predicate.LeafPredicateMetadata;
import io.doov.core.dsl.meta.predicate.PredicateMetadata;

/**
 * Base class for string conditions.
 * <p>
 * It contains a {@link DslField} to get the value from the model, a {@link LeafPredicateMetadata} to describe this node, and a
 * {@link BiFunction} to take the value from the model and return an optional value.
 */
public class StringCondition extends DefaultCondition<String> {

    public StringCondition(DslField<String> field) {
        super(field);
    }

    public StringCondition(PredicateMetadata metadata, BiFunction<DslModel, Context, Optional<String>> value) {
        super(metadata, value);
    }

    /**
     * Returns a condition checking if the node value contains the given value.
     *
     * @param value the value
     * @return the step condition
     */
    public final StepCondition contains(String value) {
        return predicate(containsMetadata(metadata, value),
                        (model, context) -> Optional.ofNullable(value),
                        String::contains);
    }

    /**
     * Returns a condition checking if the node value matches the given value.
     *
     * @param value the value
     * @return the step condition
     */
    public final StepCondition matches(String value) {
        return predicate(matchesMetadata(metadata, value),
                        (model, context) -> Optional.ofNullable(value),
                        String::matches);
    }

    /**
     * Returns a condition checking if the node value starts with the given value.
     *
     * @param value the value
     * @return the step condition
     */
    public final StepCondition startsWith(String value) {
        return predicate(startsWithMetadata(metadata, value),
                        (model, context) -> Optional.ofNullable(value),
                        String::startsWith);
    }

    /**
     * Returns a condition checking if the node value ends with the given value.
     *
     * @param value the value
     * @return the step condition
     */
    public final StepCondition endsWith(String value) {
        return predicate(endsWithMetadata(metadata, value),
                        (model, context) -> Optional.ofNullable(value),
                        String::endsWith);
    }

    /**
     * Returns an integer condition that returns the node value length.
     *
     * @return the integer condition
     */
    public IntegerCondition length() {
        return new IntegerCondition(lengthIsMetadata(metadata),
                        (model, context) -> value(model, context).map(String::length));
    }

    /**
     * Returns an integer condition that returns the node value as an integer.
     *
     * @return the integer condition
     */
    public IntegerCondition parseInt() {
        return new IntegerCondition(metadata,
                        (model, context) -> value(model, context).map(Integer::parseInt));
    }

}
