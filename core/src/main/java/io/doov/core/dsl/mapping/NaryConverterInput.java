/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.mapping;

import static io.doov.core.dsl.meta.MappingInputMetadata.inputMetadata;
import static io.doov.core.dsl.meta.MappingMetadata.fieldsInput;
import static io.doov.core.dsl.meta.MappingMetadata.metadataInput;

import java.util.List;

import io.doov.core.FieldModel;
import io.doov.core.dsl.DslField;
import io.doov.core.dsl.grammar.mapping.FunctionIn;
import io.doov.core.dsl.grammar.Value;
import io.doov.core.dsl.lang.*;
import io.doov.core.dsl.meta.MappingInputMetadata;
import io.doov.core.dsl.meta.Metadata;

public class NaryConverterInput<T> extends AbstractDSLBuilder<T> implements MappingInput<T> {

    private final List<DslField<?>> fields;
    private final NaryTypeConverter<T> converter;
    private final MappingInputMetadata metadata;

    public NaryConverterInput(List<DslField<?>> fields, NaryTypeConverter<T> converter) {
        this.fields = fields;
        this.metadata = inputMetadata(metadataInput(fieldsInput(fields)), converter.metadata());
        this.converter = converter;
    }

    @Override
    public boolean validate(FieldModel inModel) {
        return fields.stream().allMatch(f -> inModel.getFieldIds().contains(f.id()));
    }

    @Override
    public Metadata metadata() {
        return metadata;
    }

    @Override
    public T read(FieldModel inModel, Context context) {
        return converter.convert(inModel, context, fields.toArray(new DslField[0]));
    }

    @Override
    public Value<T> ast() {
        return new FunctionIn<>();
    }

}
