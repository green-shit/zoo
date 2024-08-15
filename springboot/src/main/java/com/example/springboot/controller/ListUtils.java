package com.example.springboot.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static <T> List<T> listToEntityList(List<List<Object>> dataList, Class<T> clazz) {
        List<T> entityList = new ArrayList<>();

        for (List<Object> data : dataList) {
            try {
                T entity = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();

                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    field.set(entity, data.get(i));
                }

                entityList.add(entity);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return entityList;
    }
}
