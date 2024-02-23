package com.example.PetProjectSpring.core.funcs;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GetHashFromClass {
    public static Map<String, Object> getMap(Class someClassInstance) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();

//        for (Field field : someClassInstance.getDeclaredFields()) {
//            field.setAccessible(true);
//
//            System.out.println(field.get("sessionId"));
//
//            map.put(field.getName(), field.get(someClassInstance)); // Extract the field's value
//        }

        try {
            Map<String, Object> myObjectAsDict = new HashMap<>();
            Field[] allFields = someClassInstance.getDeclaredFields();
            for (Field field : allFields) {
                Class<?> targetType = field.getType();
                Object objectValue = targetType.newInstance();
                Object value = field.get(objectValue);
                myObjectAsDict.put(field.getName(), value);
            }

            return myObjectAsDict;
        } catch (InstantiationException err) {
            return null;
        }
    }
}
