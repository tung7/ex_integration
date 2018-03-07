package com.tung7.ex.repository.json.util;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.lang.annotation.Annotation;

/**
 * TODO Fill The Description!
 *
 * @author Tung
 * @version 1.0
 * @date 2018/3/7.
 * @update
 */

public class DimensionFieldSerializer extends JacksonAnnotationIntrospector implements Versioned {
    private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_SER = (Class[])(new Class[]{JsonSerialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonRawValue.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class});
    private static final Class<? extends Annotation>[] ANNOTATIONS_TO_INFER_DESER = (Class[])(new Class[]{JsonDeserialize.class, JsonView.class, JsonFormat.class, JsonTypeInfo.class, JsonUnwrapped.class, JsonBackReference.class, JsonManagedReference.class});

    @Override
    public Version version() {
        return VersionUtil.versionFor(getClass());
    }


    @Override
    public boolean isAnnotationBundle(Annotation ann) {
        Class<?> cls = ann.annotationType();
        if (Dimension.class == cls) {
            return true;
        }
        return super.isAnnotationBundle(ann);
    }



    @Override
    public PropertyName findNameForSerialization(Annotated a) {
        Dimension dimension = _findAnnotation(a, Dimension.class);
        if (dimension != null) {
            return new PropertyName(a.getName() + "_" + dimension.valueType());
        }
        return super.findNameForSerialization(a);
    }

    @Override
    public PropertyName findNameForDeserialization(Annotated a) {
        Dimension dimension = _findAnnotation(a, Dimension.class);
        if (dimension != null) {
            return new PropertyName(a.getName() + "_" + dimension.valueType());
        }
        return super.findNameForDeserialization(a);
    }
}