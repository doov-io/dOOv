/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package io.doov.core.dsl.meta.function;

import static io.doov.core.dsl.meta.DefaultOperator.template_field;
import static io.doov.core.dsl.meta.MetadataType.TEMPLATE_PARAM;
import static io.doov.core.dsl.meta.predicate.ValuePredicateMetadata.valueMetadata;

import io.doov.core.FieldId;
import io.doov.core.FieldInfo;
import io.doov.core.dsl.meta.BinaryMetadata;
import io.doov.core.dsl.meta.Metadata;
import io.doov.core.dsl.meta.MetadataType;
import io.doov.core.dsl.meta.Operator;
import io.doov.core.dsl.meta.predicate.ValuePredicateMetadata;

public class TemplateParamMetadata extends BinaryMetadata {

    TemplateParamMetadata(Metadata left, Operator operator, Metadata right) {
        super(left, operator, right);
    }

    public static TemplateParamMetadata templateParamMetadata(String unInitReadable, FieldInfo fieldInfo) {
        if (fieldInfo == null)
            return new TemplateParamMetadata(valueMetadata(unInitReadable), template_field, valueMetadata(null));
        return new TemplateParamMetadata(valueMetadata(unInitReadable), template_field, fieldInfo.getMetadata());
    }

    @Override
    public MetadataType type() {
        return TEMPLATE_PARAM;
    }
}
