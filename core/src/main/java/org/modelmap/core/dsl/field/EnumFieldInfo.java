package org.modelmap.core.dsl.field;

import org.modelmap.core.FieldId;

public class EnumFieldInfo<E extends Enum<E>> extends DefaultFieldInfo<E> {

    EnumFieldInfo(FieldId fieldId, Class<?> type, FieldId[] siblings) {
        super(fieldId, type, new Class[] {}, siblings);
    }
}
