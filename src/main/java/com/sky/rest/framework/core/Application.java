package com.sky.rest.framework.core;

import io.vertx.core.Vertx;

import java.io.IOException;
import java.util.Set;


public class Application {

    public static void run(Vertx vertx, Class<?> cls) {
        // 1.读取配置文件

        // 2.
        // 根目录
        String root = cls.getPackage().getName().replace(".", "/");
        try {
            SourceEngine.iterator(root);
            RestEngine.handlerMapping(vertx);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}
