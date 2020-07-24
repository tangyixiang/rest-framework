package com.sky.rest.framework.core;

import com.sky.rest.framework.annotation.inject.Inject;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationContext {

    private ApplicationContext() {
    }

    private static Map<String, Object> singleInstance = new HashMap<>(); //对象实例化完成

    // 初始化实例
    private static Map<String, Object> initializationInstanceCache = new HashMap<>(); //注入属性


    private static Map<String, ObjectFactory> instance = new HashMap<>();  //创建对象


    public static void injectField(Object object, Field field) throws IllegalAccessException, InstantiationException {
        String injectInstanceName = field.getType().getSimpleName();
        injectInstanceName = injectInstanceName.substring(0, 1).toLowerCase() + injectInstanceName.substring(1, injectInstanceName.length());

        if (singleInstance.get(injectInstanceName) == null) {
            if (initializationInstanceCache.get(injectInstanceName) == null) {
                if(instance.get(injectInstanceName) == null){
                    ObjectFactory objectFactory = new ObjectFactory(field.getType().newInstance(), true, false);
                    instance.put(injectInstanceName,objectFactory);
                }else {
                    Object noInjectObject = instance.get(injectInstanceName).getObject();
                    Field[] declaredFields = noInjectObject.getClass().getDeclaredFields();
                    List<Field> fields = Arrays.asList(declaredFields).stream().filter(f -> f.getAnnotation(Inject.class) != null).collect(Collectors.toList());
                    //TODO 给未注入属性的对象注入属性
                }


            }else {

            }

        }
    }


}
