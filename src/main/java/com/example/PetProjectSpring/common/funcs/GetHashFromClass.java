package com.example.PetProjectSpring.common.funcs;

import java.lang.reflect.Field;
import java.util.HashMap;

public class GetHashFromClass {
    public static HashMap getHash(Class someClassInstance) throws IllegalAccessException {
        HashMap map = new HashMap<>();

        for (Field field : someClassInstance.getDeclaredFields()) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(someClassInstance)); // Extract the field's value
        }

        return map;
    }
}
