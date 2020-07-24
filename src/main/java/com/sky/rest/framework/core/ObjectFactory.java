package com.sky.rest.framework.core;

public class ObjectFactory {

    private Object object;

    private boolean isInitialization;

    private boolean finshed;

    public ObjectFactory(Object object, boolean isInitialization, boolean finshed) {
        this.object = object;
        this.isInitialization = isInitialization;
        this.finshed = finshed;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isInitialization() {
        return isInitialization;
    }

    public void setInitialization(boolean initialization) {
        isInitialization = initialization;
    }

    public boolean isFinshed() {
        return finshed;
    }

    public void setFinshed(boolean finshed) {
        this.finshed = finshed;
    }
}
