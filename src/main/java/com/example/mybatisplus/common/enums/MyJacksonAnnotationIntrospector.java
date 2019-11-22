package com.example.mybatisplus.common.enums;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

public class MyJacksonAnnotationIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public Object findSerializer(Annotated a) {
        IEnum annotation = a.getAnnotation(IEnum.class);
        if(annotation!=null){
            return EnumSerializer.class;
        }
        return super.findSerializer(a);
    }
}
