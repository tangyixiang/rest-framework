package com.sky.rest.framework.core;

import com.sky.rest.framework.annotation.request.*;
import io.netty.util.Mapping;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RestEngine {

    public static Router handlerMapping(Vertx vertx) {
        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        for (Class<?> cls : SourceEngine.classSetView) {
            RequestMapping requestMappingAnno = cls.getAnnotation(RequestMapping.class);
            String prefix = null;
            if (requestMappingAnno != null) {
                prefix = requestMappingAnno.value();
            }

            Method[] declaredMethods = cls.getDeclaredMethods();
            for (Method method : declaredMethods) {
                Map<HttpMethod, String> httpMethodStringMap = httpMethod(method);
                if (httpMethodStringMap != null) {
                    httpMethodStringMap.forEach((k, v) -> router.route(k, v).handler(new CommonMappingHandler(null,method)));
                }

            }

        }
        return router;
    }


    private static Map<HttpMethod, String> httpMethod(Method method) {
        HttpMethod httpMethod = null;
        String value = null;
        if (method.getAnnotation(GetMapping.class) != null) {
            httpMethod = HttpMethod.GET;
            value = method.getAnnotation(GetMapping.class).value();
        }
        if (method.getAnnotation(PostMapping.class) != null) {
            httpMethod = HttpMethod.POST;
            value = method.getAnnotation(GetMapping.class).value();
        }
        if (method.getAnnotation(PutMapping.class) != null) {
            httpMethod = HttpMethod.PUT;
            value = method.getAnnotation(GetMapping.class).value();
        }
        if (method.getAnnotation(DeleteMapping.class) != null) {
            httpMethod = HttpMethod.DELETE;
            value = method.getAnnotation(GetMapping.class).value();
        }
        if (httpMethod == null) {
            return null;
        }
        Map<HttpMethod, String> map = new HashMap<>();
        map.put(httpMethod, value);
        return map;
    }


}
