package com.piro.run.web.common;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

import javax.faces.context.FacesContext;
import java.util.Map;

public class ViewJsfScope implements Scope {

    @Override
    public synchronized Object get(String name, ObjectFactory<?> objectFactory) {
        Object instance = getViewMap().get(name);
        if (instance == null) {
            instance = objectFactory.getObject();
            getViewMap().put(name, instance);
        }
        return instance;
    }

    @Override
    public Object remove(String name) {
        Object instance = getViewMap().remove(name);

        ViewScopeCallbackRegistrar.clearDestructionCallbacksForSession(ViewScopeCallbackRegistrar.getCurrentSessionId());

        return instance;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable) {
        ViewScopeCallbackRegistrar.addDestructionCallBack(ViewScopeCallbackRegistrar.getCurrentSessionId(), runnable);
    }

    @Override
    public Object resolveContextualObject(String name) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.resolveReference(name);
    }

    @Override
    public String getConversationId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
        return facesRequestAttributes.getSessionId() + "-" + facesContext.getViewRoot().getViewId();
    }

    private Map<String, Object> getViewMap() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewMap();
    }
}
