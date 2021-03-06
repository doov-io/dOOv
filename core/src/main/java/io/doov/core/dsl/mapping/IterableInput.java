/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.mapping;

import java.util.function.Supplier;

import io.doov.core.FieldModel;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.IterableMetadata;

public class IterableInput<E,T extends Iterable<E>> extends AbstractDSLBuilder implements MappingInput<T> {

    private final Supplier<T> valueSupplier;
    private final IterableMetadata<E,T> metadata;

    public IterableInput(Supplier<T> valueSupplier) {
        this.valueSupplier = valueSupplier;
        this.metadata = IterableMetadata.mappingIterableMetadata(valueSupplier);
    }

    @Override
    public boolean validate(FieldModel inModel) {
        return true;
    }

    @Override
    public IterableMetadata metadata() {
        return metadata;
    }

    @Override
    public T read(FieldModel inModel, Context context) {
        return valueSupplier.get();
    }
}
