package com.example.mybatisplus.common.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
@Slf4j
public class EnumSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object var1, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(var1);
        JsonStreamContext outputContext = jsonGenerator.getOutputContext();
        String currentName = outputContext.getCurrentName();
        if(var1!=null){
            try {
                //获取目标类指定属性
                Field declaredField = outputContext.getCurrentValue().getClass().getDeclaredField(currentName);
                IEnum annotation = declaredField.getAnnotation(IEnum.class);
                if(annotation!=null){
                    Class<?> value = annotation.value();
                    if(value.isEnum()){
                        String name = annotation.name();
                        String jsonName = currentName + "Value";
                        if(StringUtils.isNotBlank(name)){
                            jsonName = name;
                        }
                        //获取枚举value值
                        String enumValue = null;
                      Object[] objects =  value.getEnumConstants();
                        for (Object object : objects) {
                           BaseEnum baseEnum = (BaseEnum)object;
                           if(var1.equals(baseEnum.getCode())){
                               enumValue=baseEnum.getValue();
                               break;
                           }
                        }
                        jsonGenerator.writeFieldName(jsonName);
                        if(enumValue!=null){
                            jsonGenerator.writeString(enumValue);
                        }
                    }
                }

            } catch (NoSuchFieldException e) {
                log.error("EnumSerializer -> {}",e);
            }
        }

    }
}
