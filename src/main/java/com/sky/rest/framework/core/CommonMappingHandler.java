package com.sky.rest.framework.core;

import com.github.houbb.asm.tool.reflection.AsmMethods;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class CommonMappingHandler implements Handler<RoutingContext> {

    private Object object;

    private Method method;

    public CommonMappingHandler(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    @Override
    public void handle(RoutingContext routingContext) {

        JsonObject data = routingContext.getBodyAsJson();

        //TODO 参数封装
        /*List<String> paramNamesByAsm = AsmMethods.getParamNamesByAsm(method);
        for (int i = 0; i < method.getParameters().length; i++) {
            Class<?> type = method.getParameters()[i].getType();
            String paramName = paramNamesByAsm.get(i);
        }*/
        //TODO 执行方法

        routingContext.response().putHeader("Content-type","application/json").end("");
    }
}
